package org.sukrupa;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class SqlRunner {

    private Connection connection;
    private boolean dryRun;

    SqlRunner(Connection connection, boolean dryRun) {
        this.connection = connection;
        this.dryRun = dryRun;
    }


    public void execute(InputStream fileInputStream) throws java.sql.SQLException, IOException {
        Scanner scanner = new Scanner(fileInputStream, "UTF-8");

        try {
            while (scanner.hasNextLine()) {
                String sqlStatement = scanner.nextLine();
                if (dryRun) {
                    System.out.println(sqlStatement);
                } else {
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                    preparedStatement.execute();
                }
            }
        } finally {
            scanner.close();
        }
    }
}