package org.sukrupa;

import org.jets3t.service.security.AWSCredentials;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class CredentialsFileCreator {

    public static void main(String[] args) throws IllegalBlockSizeException, IOException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException {

        File credFile = new File("awscredentials.enc");

        String awsAccessKey = "YOUR_AWS_ACCESS_KEY";
        String awsSecretKey = "YOUR_AWS_SECRET_KEY";

        AWSCredentials awsCredentials = new AWSCredentials(awsAccessKey, awsSecretKey);
        awsCredentials.save("YOUR_PASSWORD", credFile);
    }
}
