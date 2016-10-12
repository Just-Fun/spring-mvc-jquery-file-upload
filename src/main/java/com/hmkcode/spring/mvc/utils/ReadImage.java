package com.hmkcode.spring.mvc.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Serzh on 10/8/16.
 */
public class ReadImage {

    public static void main(String[] args) {

        // !!!!!!!!!!!!!!!!
        /*BufferedReader reader = null;
        try {
            reader = new BufferedReader(new UnicodeReader(new FileInputStream(fileName), "UTF-8"));
            // ...
        } finally {
            if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
        }*/

        Connection con = null;
        PreparedStatement pst = null;
        FileOutputStream fos = null;

        String url = "jdbc:postgresql://localhost/upload";
        String user = "postgres";
        String password = "postgres";

        try {
            con = DriverManager.getConnection(url, user, password);

            /*PreparedStatement ps = con.prepareStatement("SELECT img FROM images WHERE imgname = ?");
            ps.setString(1, "myimage.gif");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                byte[] imgBytes = rs.getBytes(1);
                // use the data in some way here
            }
            rs.close();
            ps.close();*/

            /*String sql = "SELECT name, description, image FROM pictures ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
              String name = resultSet.getString(1);
              String description = resultSet.getString(2);
              File image = new File("D:\\java.gif");
              FileOutputStream fos = new FileOutputStream(image);

              byte[] buffer = new byte[1];
              InputStream is = resultSet.getBinaryStream(3);
              while (is.read(buffer) > 0) {
                fos.write(buffer);
              }
              fos.close();
            }
            conn.close();*/
//            String query = "SELECT file, LENGTH(file) FROM files where id='9'";
            byte[] byteArray = new byte[]{87, 79, 87, 46, 46, 46};
            String value = new String(byteArray, "UTF-8");
            System.out.println(value);


            File file = new File("src/main/temp/1.txt");

            fos = new FileOutputStream(file);

            String query = "SELECT file FROM files where id='10'";
            pst = con.prepareStatement(query);
            ResultSet resultSet = pst.executeQuery();
            List<String> list = new LinkedList<>();

//            List<Byte>
            while (resultSet.next()) {
                byte[] buffer = new byte[1];
                InputStream is = resultSet.getBinaryStream(1);

                String stringFromInputStream = InputStreamToStringExample.getStringFromInputStream(is);
                System.out.println(stringFromInputStream);
                System.out.println("!!!!");

//                String s = readStringFromInputStreamWithBuffer(is);
//                System.out.println(s);
//                String str = IOUtils.toString(is, StandardCharsets.UTF_8);
//                System.out.println(str);
                String string = "";

                byte[] bufferToString = new byte[1024];
                int i = 0;
                while (is.read(buffer) > 0) {
                    if (buffer[0] == 10) {
//                        string = System.Text.Encoding.UTF8.GetString(byteArray);
//                        System.Text.Encoding.UTF8.GetString(buf).TrimEnd('\0');
//                        string = new String(bufferToString, StandardCharsets.UTF_8);
                        string = new String(bufferToString, StandardCharsets.UTF_8).trim();
                        System.out.println(string);
                        if (string.length() > 0) {
                            list.add(string);
                        }
                        bufferToString = new byte[128];
                        i = 0;
                        System.out.println("yep");
//                        string = new String(buffer, StandardCharsets.UTF_8);
//                        System.out.println(string);
                    }
                    if (buffer[0] > 0) {
                        bufferToString[i++] = buffer[0];
//                        System.out.println(buffer[0]);
                    }
//                    if (buffer[0] > 0) {
//                        string = new String(buffer, StandardCharsets.UTF_8);
//                        System.out.println(string);
//                    }
                    fos.write(buffer);
                }
                fos.close();
            }

            list.forEach(s -> System.out.println(s));


//            String query = "SELECT photo, LENGTH(photo) FROM contacts WHERE photo_name = ?";
           /* String query = "SELECT file, LENGTH(file) FROM files where id='9'";
            pst = con.prepareStatement(query);
            String name = "f1_1.doc";
            pst.setString(1, name);
            ResultSet result = pst.executeQuery();
            result.next();

            fos = new FileOutputStream(name);

            int len = result.getInt(2);
            byte[] buf = result.getBytes("file");
            fos.write(buf, 0, len);
*/
            /*ByteArrayOutputStream os = new ByteArrayOutputStream();
            aClass.outputStreamMethod(os);
            String aString = new String(os.toByteArray(),"UTF-8");*/

            /*String str = new String(bytes, StandardCharsets.UTF_8);
And if you're feeling lazy, you can use the Apache Commons IO library to convert the InputStream to a String directly:

String str = IOUtils.toString(inputStream, StandardCharsets.UTF_8);*/

        } catch (IOException | SQLException ex) {
            Logger lgr = Logger.getLogger(ReadImage.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
                if (fos != null) {
                    fos.close();
                }

            } catch (IOException | SQLException ex) {
                Logger lgr = Logger.getLogger(ReadImage.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);

            }
        }
    }

    private static String readStringFromInputStreamWithBuffer(InputStream is) throws IOException {
        StringBuilder text = new StringBuilder();
        int incrementStep = 64;
        byte[] content = new byte[incrementStep];
        int currentByte = 0;
        byte[] buffer = new byte[incrementStep / 32];
        while (is.read(buffer) != -1) {
            if (currentByte >= content.length) {
                byte[] copy = new byte[incrementStep + content.length];
                System.arraycopy(content, 0, copy, 0, content.length);
                content = copy;
            }
            for (int i = currentByte; i < buffer.length + currentByte; i++) {
                content[i] = buffer[i - currentByte];
            }
            currentByte += buffer.length;
            buffer = new byte[2];
        }
        return new String(content);
    }
}