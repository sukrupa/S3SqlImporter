package org.sukrupa;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;

import java.io.File;
import java.io.InputStream;

public class S3Loader {
    private AWSCredentials awsCredentials;
    private String bucketName;
    private String dataFileName;

    public S3Loader(String credentialsPath, String password, String bucketName, String dataFileName) {
        this.bucketName = bucketName;
        this.dataFileName = dataFileName;
        try {
            this.awsCredentials = AWSCredentials.load(password, new File(credentialsPath));
        } catch (ServiceException e) {
            throw new RuntimeException("Falied to load S3 credentials");
        }
    }

    public InputStream getDataStream() {
        try {
            S3Service s3Service = new RestS3Service(awsCredentials);

            S3Object dataObject = s3Service.getObject(bucketName, dataFileName);
            return dataObject.getDataInputStream();

        } catch (S3ServiceException e) {
            throw new RuntimeException("Failed to download data from S3");
        } catch (ServiceException e) {
            throw new RuntimeException("Failed to download data from S3");
        }
    }

}
