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

        // read file input until length of buffer input
        for (int arr = 0; arr < buffIn.length; arr++) {
            // store each byte of input in buffer input
            fin.read(buffIn[arr]);
        }
        
        byte[][] buffOut = new byte[m + 1][16];
        buffOut[m] = new byte[b];
        
        // set aes encryption with key2
        AES aes = new AES();
        aes.setKey(key2);
        // if noncedp is null we set noncedp by encryption of nonce
        if(nonceDP==null) nonceDP = aes.encrypt(i);
        //
        buildTable(nonceDP, m + 1);
        
        System.out.println("---Start a thread, perform a long task");
        // make new thread for execute per block encryption
        Thread[] worker = new Thread[NUMBER_OF_THREAD];
        // we will start encryption from first until before last two block
        // there is a stealing for last two block if the mod is not zero
        for (int a = 0; a <= m - 2; a++) {
            worker[a % NUMBER_OF_THREAD] = new Thread(new LongTask(LongTask.MODE_ENCRYPT,
                    buffOut[a], buffIn[a], key1, key2, a, i));
            worker[a % NUMBER_OF_THREAD].start();
            if (a % NUMBER_OF_THREAD == NUMBER_OF_THREAD - 1) {
                for (int aa = 0; aa < NUMBER_OF_THREAD; aa++) {
                    if (worker[aa] != null) {
                        worker[aa].join(0);
                    }
                }
            }
        }
        for (int a = 0; a < NUMBER_OF_THREAD; a++) {
            if (worker[a] != null) {
                worker[a].join(0);
            }
        }
        System.out.println("---finish all thread, block - 2 successfull encrypted");
        if (b == 0) {
            System.out.println("---file size is divisible by block size");
            System.out.println("---continue encryption for last two block");
            perBlockEncrypt(buffOut[m - 1], buffIn[m - 1], key1, key2, m - 1, i);
            buffOut[m] = new byte[0];
        } else {
            System.out.println("---file size is not divisible by block size");
            System.out.println("---Perform a ciphertext stealing");
            byte[] cc = new byte[BLOCK_SIZE];
            
            perBlockEncrypt(cc, buffIn[m - 1], key1, key2, m - 1, i);
            System.arraycopy(cc, 0, buffOut[m], 0, b);
            byte[] cp = new byte[16 - b];
            int ctr = 16 - b;
            int xx = cc.length - 1;
            int yy = cp.length - 1;
            while (ctr-- != 0) {
                cp[yy--] = cc[xx--];
            }
            byte[] pp = new byte[16];
            for (int a = 0; a < b; a++) {
                pp[a] = buffIn[m][a];
            }
            for (int a = b; a < pp.length; a++) {
                pp[a] = cp[a - b];
            }
            perBlockEncrypt(buffOut[m - 1], pp, key1, key2, m, i);
        }
        for (int a = 0; a < buffOut.length; a++) {
            fout.write(buffOut[a]);
        }

    }
    public void startDecryption(String cipher, String key, String plain) throws Exception {
        
        RandomAccessFile raf1 = new RandomAccessFile(plain, "r");
        RandomAccessFile raf2 = new RandomAccessFile(cipher, "rw");        
        BufferedReader br = new BufferedReader(new FileReader(key));
        String baca = br.readLine();
        br.close();
        String key1 = baca.substring(0, KEY_LENGTH / 2);
        String key2 = baca.substring(KEY_LENGTH / 2, baca.length());
        System.out.println("key1\t= " + key1);
        System.out.println("key2\t= " + key2);
        System.out.println("tweak\t= " + Util.toHEX1(nonce));
        

        //pemanggilan method XTSAESDecrypt untuk melakukan dekripsi data
        Decrypt(raf1, raf2, Util.hex2byte(key1), Util.hex2byte(key2), nonce);
        raf1.close();
        raf2.close();
        System.out.println("Decryption Done!");
    }
    
    public void Decrypt(RandomAccessFile in, RandomAccessFile out,
            byte[] key1, byte[] key2, byte[] i) throws Exception {
        long fileSize = in.length();
        int m = (int) (fileSize / BLOCK_SIZE);
        int b = (int) (fileSize % BLOCK_SIZE);
        byte[][] bufferIn = new byte[m + 1][16];
        bufferIn[m] = new byte[b];
        for (int a = 0; a < bufferIn.length; a++) {
            in.read(bufferIn[a]);
        }
        byte[][] bufferOut = new byte[m + 1][16];
        bufferOut[m] = new byte[b];
        AES aes = new AES();
        aes.setKey(key2);
        if(nonceDP==null) nonceDP = aes.encrypt(i);
        buildTable(nonceDP, m + 1);        

        Thread[] worker = new Thread[NUMBER_OF_THREAD];
        for (int a = 0; a <= m - 2; a++) {
            worker[a % NUMBER_OF_THREAD] = new Thread(new LongTask(LongTask.MODE_DECRYPT,
                    bufferOut[a], bufferIn[a], key1, key2, a, i));
            worker[a % NUMBER_OF_THREAD].start();
            if (a % NUMBER_OF_THREAD == NUMBER_OF_THREAD - 1) {
                for (int aa = 0; aa < NUMBER_OF_THREAD; aa++) {
                    if (worker[aa] != null) {
                        worker[aa].join(0);
                    }
                }
            }
        }
        for (int a = 0; a < NUMBER_OF_THREAD; a++) {
            if (worker[a] != null) {
                worker[a].join(0);
            }
        }

        System.out.println("---finish all thread");
        if (b == 0) {
            System.out.println("---file size is divisible by block size");
            perBlockDecrypt(bufferOut[m - 1], bufferIn[m - 1], key1, key2, m - 1, i);
            bufferOut[m] = new byte[0];
        } else {
            System.out.println("---file size is not divisible by block size");
            byte[] cc = new byte[BLOCK_SIZE];
            perBlockDecrypt(cc, bufferIn[m - 1], key1, key2, m, i);
            System.arraycopy(cc, 0, bufferOut[m], 0, b);
            byte[] cp = new byte[16 - b];
            int ctr = 16 - b;
            int xx = cc.length - 1;
            int yy = cp.length - 1;
            while (ctr-- != 0) {
                cp[yy--] = cc[xx--];
            }
            byte[] pp = new byte[16];
            for (int a = 0; a < b; a++) {
                pp[a] = bufferIn[m][a];
            }
            for (int a = b; a < pp.length; a++) {
                pp[a] = cp[a - b];
            }
            perBlockDecrypt(bufferOut[m - 1], pp, key1, key2, m - 1, i);
        }
        for (int a = 0; a < bufferOut.length; a++) {
            out.write(bufferOut[a]);
        }
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
    public void perBlockEncrypt(byte[] ret, byte[] plain, byte[] key1,
            byte[] key2, int j, byte[] i) {
        AES aes = new AES();
//        aes.setKey(key2);
//        if(nonceDP==null) nonceDP = aes.encrypt(i);
        byte[] t = multiplyByPowJ(nonceDP, j);
        byte[] pp = new byte[BLOCK_SIZE];
        for (int a = 0; a < pp.length; a++) {
            pp[a] = (byte) (plain[a] ^ t[a]);
        }
        aes = new AES();
        aes.setKey(key1);
        byte[] cc = aes.encrypt(pp);
        for (int a = 0; a < ret.length; a++) {
            ret[a] = (byte) (cc[a] ^ t[a]);
        }
    }
     public void perBlockDecrypt(byte[] ret, byte[] cipher, byte[] key1,
            byte[] key2, int j, byte[] i) {
        AES aes = new AES();
//        aes.setKey(key2);
//        if(nonceDP==null) nonceDP = aes.encrypt(i);
        byte[] t = multiplyByPowJ(nonceDP, j);
        byte[] cc = new byte[BLOCK_SIZE];
        for (int a = 0; a < cc.length; a++) {
            cc[a] = (byte) (cipher[a] ^ t[a]);
        }
        aes = new AES();
        aes.setKey(key1);
        byte[] pp = aes.decrypt(cc);
        for (int a = 0; a < ret.length; a++) {
            ret[a] = (byte) (pp[a] ^ t[a]);
        }
    }
    public byte[] multiplyByPowJ(byte[] a, int j) {
        return multiplyDP[j];
    }
    class LongTask implements Runnable {

        /**
         * variabel-variabel yang digunakan
         */
        public static final int MODE_ENCRYPT = 0;
        public static final int MODE_DECRYPT = 1;
        private int mode;
        private byte[] dest;
        private byte[] source;
        private byte[] key1;
        private byte[] key2;
        private int j;
        private byte[] i;

        // Constructor
        public LongTask(int mode, byte[] dest, byte[] source, byte[] key1,
                byte[] key2, int j, byte[] i) {
            this.mode = mode;
            this.dest = dest;
            this.source = source;
            this.key1 = key1;
            this.key2 = key2;
            this.j = j;
            this.i = i;
        }

        @Override
        public void run() {
            switch (this.mode) {
                case MODE_ENCRYPT:
                    perBlockEncrypt(dest, source, key1, key2, j, i);
                    break;
                case MODE_DECRYPT:
                    perBlockDecrypt(dest, source, key1, key2, j, i);
                    break;
            }
        }
    }
}