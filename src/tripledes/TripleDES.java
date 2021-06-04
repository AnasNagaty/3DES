package tripledes;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.*;


public class TripleDES {
    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //Encrypt: C = EK3(DK2(EK1(P)))
        //Decrypt: P = DK3(EK2(DK1(C)))
        
        Scanner sc = new Scanner(System.in);
        
        //Generate key for DES
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey secretKey = keyGenerator.generateKey();
        SecretKey secretKey2 = keyGenerator.generateKey();
        SecretKey secretKey3 = keyGenerator.generateKey();
        
        //Text Enc & Dec
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        Cipher cipher2 = Cipher.getInstance("DES/ECB/NoPadding");
        Cipher cipher3 = Cipher.getInstance("DES/ECB/NoPadding");

        
        //enter msg
        System.out.print("Enter a string: ");
        String x= sc.nextLine();
        
        //enc
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte[] message = x.getBytes();//text
        byte[] messageEnc = cipher.doFinal(message);//encryption with key1
        cipher2.init(Cipher.DECRYPT_MODE,secretKey2);
        byte[] deck2 = cipher2.doFinal(messageEnc);//decryption with key2
        cipher3.init(Cipher.ENCRYPT_MODE,secretKey3);
        byte[] messageEnc1 = cipher3.doFinal(deck2);//encryption with key3
        System.out.println("Cipher Text: " + new String(messageEnc1));
        
        //dec
        cipher3.init(Cipher.DECRYPT_MODE,secretKey3);
        byte[] dec = cipher3.doFinal(messageEnc1);//decryption with key3
        cipher2.init(Cipher.ENCRYPT_MODE,secretKey2);
        byte[] messageEnc2 = cipher2.doFinal(dec);//encryption with key2
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        byte[] deck3 = cipher.doFinal(messageEnc2);//decryption with key1
        System.out.println("Plain Text: " + new String(deck3));
    }
}
