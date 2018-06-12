package logic.Decrypter;

import logic.Crypt;

import javax.crypto.Cipher;
import java.io.*;
import java.security.Key;

public class Decryption
{
    public static void main(String[] args)
    {
        try
        {
            if (args[0].equals("-decrypt"))
            {
                DataInputStream in = new DataInputStream(new FileInputStream(args[1]));
                int length = in.readInt();
                byte[] wrappedKey = new byte[length];
                in.read(wrappedKey, 0, length);

                // unwrap with RSA private key
                ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
                Key privateKey = (Key) keyIn.readObject();
                keyIn.close();

                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.UNWRAP_MODE, privateKey);
                Key key = cipher.unwrap(wrappedKey, "DES", Cipher.SECRET_KEY);

                OutputStream out = new FileOutputStream(args[2]);
                cipher = Cipher.getInstance("DES");
                cipher.init(Cipher.DECRYPT_MODE, key);

                Crypt crypt = new Crypt();
                crypt.crypt(in, out, cipher);
                in.close();
                out.close();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
