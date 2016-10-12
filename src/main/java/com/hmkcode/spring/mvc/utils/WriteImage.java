package com.hmkcode.spring.mvc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Serzh on 10/8/16.
 */
public class WriteImage {

    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement pst = null;
        FileInputStream fin = null;

        String url = "jdbc:postgresql://localhost/upload-photo";
        String user = "postgres";
        String password = "postgres";

        try {
            File img = new File("src/main/java/ua/com/serzh/1.rtf");
            fin = new FileInputStream(img);

            con = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO contacts (first_name, last_name, photo) values (?, ?, ?)";

            pst = con.prepareStatement(sql);
            pst.setString(1, "S6");
            pst.setString(2, "Zag");
            pst.setBinaryStream(3, fin, (int) img.length());
            pst.executeUpdate();

        } catch (FileNotFoundException | SQLException ex) {
            Logger lgr = Logger.getLogger(WriteImage.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
                if (fin != null) {
                    fin.close();
                }

            } catch (IOException | SQLException ex) {
                Logger lgr = Logger.getLogger(WriteImage.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);

            }
        }
    }
}