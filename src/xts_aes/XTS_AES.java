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
     * variabel-variabel yang digunakan
     */
    private String PLAIN_FILE;
    private String KEY_FILE;
    private String CIPHER_FILE;
    private static int BLOCK_SIZE = 16;                     //128-bits (16-bytes)
    private static int KEY_LENGTH_HEX = 64;                 //256-bits (32-bytes)
    private static byte[] nonce = Util.hex2byte("12345678901234567890123456789012");
    // Constructor dari kelas XTSAES
    
    public XTS_AES(String plain, String key, String cipher) {
        this.PLAIN_FILE = plain;
        this.KEY_FILE = key;
        this.CIPHER_FILE = cipher;
    }
    
    public void startEncryption(String plain, String key, String cipher) throws Exception {
        
        RandomAccessFile raf1 = new RandomAccessFile(plain, "r");
        RandomAccessFile raf2 = new RandomAccessFile(cipher, "rw");
        
        BufferedReader br = new BufferedReader(new FileReader(key));
        String read = br.readLine();
        br.close();
        
        String key1 = read.substring(0, KEY_LENGTH_HEX / 2);
        String key2 = read.substring(KEY_LENGTH_HEX / 2, read.length());
        System.out.println("key1\t= " + key1);
        System.out.println("key2\t= " + key2);
        System.out.println("tweak\t= " + Util.toHEX1(nonce));

        // pemanggilan method XTSAESEncrypt untuk melakukan enkripsi
        Encrypt(raf1, raf2, Util.hex2byte(key1), Util.hex2byte(key2), nonce);
        raf1.close();
        raf2.close();
        System.out.println("Encryption Done!");
    }
    
    public void Encrypt(RandomAccessFile fin, RandomAccessFile fout,
            byte[] key1, byte[] key2, byte[] i)throws Exception {
        long fileSize = fin.length();
        System.out.println("finlength\t= " + fileSize);
        int m = (int) (fileSize / BLOCK_SIZE);
        int b = (int) (fileSize % BLOCK_SIZE);
        
        // define a buffer for input and output
        byte[][] bufferIn = new byte[m + 1][16];
        bufferIn[m] = new byte[b];
        byte[][] bufferOut = new byte[m + 1][16];
        bufferOut[m] = new byte[b];

        for (int a = 0; a < bufferIn.length; a++) {
            fin.read(bufferIn[a]);
        }
    }
}