package logic;

import javax.crypto.*;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;

public class Encryption
{
    public static void main(String[] args)
    {
        try
        {
            if (args[0].equals("-encrypt"))
            {
                KeyGenerator keygen = KeyGenerator.getInstance("DES");
                SecureRandom random = new SecureRandom();
                keygen.init(random);
                SecretKey key = keygen.generateKey();

                //wrap with RSA public key
                ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
                Key publicKey = (Key) keyIn.readObject();
                keyIn.close();

                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.WRAP_MODE, publicKey);
                byte[] wrappedKey = cipher.wrap(key);
                DataOutputStream out = new DataOutputStream(new FileOutputStream(args[2]));
                out.writeInt(wrappedKey.length);
                out.write(wrappedKey);
                InputStream in = new FileInputStream(args[1]);
                cipher = Cipher.getInstance("DES");
                cipher.init(Cipher.ENCRYPT_MODE, key);

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
