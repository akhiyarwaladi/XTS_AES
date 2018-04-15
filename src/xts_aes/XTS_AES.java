/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xts_aes;
import java.io.*;
/**
 *
 * @author AW
 */

class XTS_AES {

    /**
     * define variable
     */
    private String PLAIN_FILE;
    private String KEY_FILE;
    private String CIPHER_FILE;
    private static int BLOCK_SIZE = 16;//16-bytes (128-bits)
    private static int KEY_LENGTH = 64;//32-bytes (258-bits
    private static byte[] nonce = Util.hex2byte("12345678901234567890123456789012");
    private static int NUMBER_OF_THREAD = 100;
    private byte[] nonceDP = null;
    private byte[][] multiplyDP = null;
    
    // Constructor from XTSAES class
    public XTS_AES(String plain, String key, String cipher) {
        this.PLAIN_FILE = plain;
        this.KEY_FILE = key;
        this.CIPHER_FILE = cipher;
    }
    
    public void startEncryption(String plain, String key, String cipher) throws Exception {
        
        RandomAccessFile fPlain = new RandomAccessFile(plain, "r");
        RandomAccessFile fCipher = new RandomAccessFile(cipher, "rw");
        
        BufferedReader br = new BufferedReader(new FileReader(key));
        String read = br.readLine();
        br.close();
        
        String key1 = read.substring(0, KEY_LENGTH / 2);
        String key2 = read.substring(KEY_LENGTH / 2, read.length());
        System.out.println("key1\t= " + key1);
        System.out.println("key2\t= " + key2);
        System.out.println("tweak\t= " + Util.toHEX1(nonce));

        // pemanggilan method XTSAESEncrypt untuk melakukan enkripsi
        Encrypt(fPlain, fCipher, Util.hex2byte(key1), Util.hex2byte(key2), nonce);
        fPlain.close();
        fCipher.close();
        System.out.println("Encryption Done!");
    }
    
    public void Encrypt(RandomAccessFile fin, RandomAccessFile fout,
            byte[] key1, byte[] key2, byte[] i)throws Exception {
        
        //define size of array for each block by file input length
        long fileSize = fin.length();
        System.out.println("finlength\t= " + fileSize);
        int m = (int) (fileSize / BLOCK_SIZE);
        int b = (int) (fileSize % BLOCK_SIZE);
        System.out.println("m(panjang array perblok)\t= " + m);
        System.out.println("b(sisa / kosong)\t= " + b);
        
        // define a buff for input and output
        // typically a disk sector, consists of a sequence of plaintext block p0, p1, ..., pm
        // so we need to add value m with one
        byte[][] buffIn = new byte[m + 1][16];
        buffIn[m] = new byte[b];
        byte[][] buffOut = new byte[m + 1][16];
        buffOut[m] = new byte[b];

        // read file input until length of buffer input
        for (int arr = 0; arr < buffIn.length; arr++) {
            // store each byte of input in buffer input
            fin.read(buffIn[arr]);
        }
        // set aes encryption with key2
        AES aes = new AES();
        aes.setKey(key2);
        // if noncedp is null we set noncedp by encryption of nonce
        if(nonceDP==null) nonceDP = aes.encrypt(i);
        //
        buildTable(nonceDP, m + 1);
        
//        Thread[] worker = new Thread[NUMBER_OF_THREAD];
//        for (int a = 0; a <= m - 2; a++) {
//            worker[a % NUMBER_OF_THREAD] = new Thread(new WorkerThread(WorkerThread.MODE_ENCRYPT,
//                    bufferOut[a], bufferIn[a], key1, key2, a, i));
//            worker[a % NUMBER_OF_THREAD].start();
//            if (a % NUMBER_OF_THREAD == NUMBER_OF_THREAD - 1) {
//                for (int aa = 0; aa < NUMBER_OF_THREAD; aa++) {
//                    if (worker[aa] != null) {
//                        worker[aa].join(0);
//                    }
//                }
//            }
//        }
        

    }
    private void buildTable(byte[] a, int numBlock) {
        multiplyDP = new byte[numBlock][BLOCK_SIZE];
        multiplyDP[0] = a;
        for (int i = 1; i < numBlock; i++) {
            multiplyDP[i][0] = (byte) ((2 * (multiplyDP[i-1][0] % 128)) ^ (135 * (multiplyDP[i-1][15] / 128)));
            for (int k = 1; k < 16; k++) {
                multiplyDP[i][k] = (byte) ((2 * (multiplyDP[i-1][k] % 128)) ^ (multiplyDP[i-1][k - 1] / 128));
            }
        }
    }
}