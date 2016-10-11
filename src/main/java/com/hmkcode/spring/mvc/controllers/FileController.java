package com.hmkcode.spring.mvc.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

import com.hmkcode.spring.mvc.model.PostgreSQLManager;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hmkcode.spring.mvc.data.FileMeta;

@Controller
@RequestMapping("/controller")
public class FileController {

    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;

    /***************************************************
     * URL: /rest/controller/upload
     * upload(): receives files
     * @param request : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     ****************************************************/
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {

        //1. build an iterator
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;

        //2. get each file
        while (itr.hasNext()) {

            //2.1 get next MultipartFile
            mpf = request.getFile(itr.next());
            System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());

            //2.2 if files > 10 remove the first from the list
            if (files.size() >= 10)
                files.pop();

            //2.3 create new fileMeta
            fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
            fileMeta.setFileType(mpf.getContentType());

            String fileName = mpf.getOriginalFilename();
            PostgreSQLManager manager;
            Connection connection = null;
            try {
                manager = new PostgreSQLManager();
                connection = manager.getConnection();
                // constructs SQL statement
                String sql = "INSERT INTO contacts (first_name, last_name, photo, photo_name) values (?, ?, ?, ?)";
//                !!!
                connection.setAutoCommit(false);
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, "TestNew2");
                statement.setString(2, "TestnewLastName2");

                InputStream inputStream = mpf.getInputStream();

                long size = mpf.getSize();
                if (inputStream != null) {
                    // fetches input stream of the upload file for the blob column
                    statement.setBinaryStream(3, inputStream, (int) size);
//                statement.setBlob(3, inputStream);
//                statement.setClob(3, );
                }

                statement.setString(4, fileName);
                // sends the statement to the database server
                int row = statement.executeUpdate();
                connection.commit();  //  добавил в паре с connection.setAutoCommit(false);,
                // когда ругалось на setBlob, сейчас возможно не актуально
                if (row > 0) {
                    System.out.println("row > 0");
                }
            } catch (SQLException ex) {
                System.out.println("row > 0" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                if (connection != null) {
                    // closes the database connection
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            files.add(fileMeta);
        }
        return files;
    }

    /***************************************************
     * URL: /rest/controller/get/{value}
     * get(): get file as an attachment
     * @param response : passed by the server
     * @param value : value from the URL
     * @return void
     ****************************************************/
    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void get(HttpServletResponse response, @PathVariable String value) {
        FileMeta getFile = files.get(Integer.parseInt(value));
        try {
            response.setContentType(getFile.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getFileName() + "\"");
            FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
