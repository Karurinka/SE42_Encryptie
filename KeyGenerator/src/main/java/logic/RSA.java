package logic;

import java.io.*;
import java.security.*;

public class RSA
{
    public static final int KEYSIZE = 1024;

    public static void main(String[] args)
    {
        try
        {
            if (args[0].equals("-genkey"))
            {
                KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
                SecureRandom random = new SecureRandom();
                pairgen.initialize(KEYSIZE, random);
                KeyPair keyPair = pairgen.generateKeyPair();

                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[1]));
                out.writeObject(keyPair.getPublic());
                out.close();
                out = new ObjectOutputStream(new FileOutputStream(args[2]));
                out.writeObject(keyPair.getPrivate());
                out.close();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
