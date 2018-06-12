package logic;

import java.io.*;
import java.security.*;
import java.util.Properties;

public class RSA
{
    public static final int KEYSIZE = 1024;

    public static void main(String[] args)
    {
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream("keys.properties"))
        {
            properties.load(in);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = new SecureRandom();
            pairgen.initialize(KEYSIZE, random);
            KeyPair keyPair = pairgen.generateKeyPair();

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(properties.getProperty("public")));
            out.writeObject(keyPair.getPublic());
            out.close();
            out = new ObjectOutputStream(new FileOutputStream(properties.getProperty("private")));
            out.writeObject(keyPair.getPrivate());
            out.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
