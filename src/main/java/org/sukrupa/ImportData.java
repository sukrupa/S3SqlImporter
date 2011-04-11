package org.sukrupa;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ImportData {

    public static void main(String[] args) throws java.sql.SQLException, IOException {

        OptionParser parser = new OptionParser("d");
        OptionSet options = parser.parse(args);

        String propertiesFilePath = args[0];
        Properties properties = load(propertiesFilePath);

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load hsqldb driver");
        }

        Connection connection = DriverManager.getConnection(
                (String) properties.get("jdbc.url"),
                (String) properties.get("jdbc.user"),
                (String) properties.get("jdbc.password"));

        SqlRunner sqlRunner = new SqlRunner(connection, options.has("d"));

        S3Loader s3Loader = new S3Loader(
                (String) properties.get("credentials.file"),
                (String) properties.get("credentials.password"),
                (String) properties.get("s3.bucketName"),
                (String) properties.get("s3.dataFileName"));

        InputStream dataStream = s3Loader.getDataStream();

        if (dataStream != null) {
            sqlRunner.execute(dataStream);
        } else {
            throw new RuntimeException("Invalid data stream - no data found");
        }
    }

    public static Properties load(String propertiesFilePath) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(new File(propertiesFilePath));

        props.load(fis);
        fis.close();

        return props;
    }
}