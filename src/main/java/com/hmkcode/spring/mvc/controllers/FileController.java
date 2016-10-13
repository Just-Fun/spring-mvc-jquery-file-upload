package com.hmkcode.spring.mvc.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hmkcode.spring.mvc.model.PostgreSQLManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hmkcode.spring.mvc.data.FileMeta;

@Controller
@RequestMapping("/controller")
public class FileController {
    PostgreSQLManager manager; // TODO bean

    LinkedList<FileMeta> files = new LinkedList<>();
    FileMeta fileMeta = null;
    long time = System.currentTimeMillis();

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
    LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {

//        Temp!!! + put in session
//        time = System.currentTimeMillis();
//        session.setAttribute("time", time);
        //1. build an iterator
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;

        //2. get each file
        while (itr.hasNext()) {

            //2.1 get next MultipartFile
            mpf = request.getFile(itr.next());
            String originalFilename = mpf.getOriginalFilename();
            System.out.println(originalFilename + " uploaded!");

            //2.2 if files > 10 remove the first from the list
            if (files.size() >= 10) {
                files.pop();
            }

            //2.3 create new fileMeta
            fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
            fileMeta.setFileType(mpf.getContentType());
//            fileMeta.setTimeCreated(time);

            String fileName = mpf.getOriginalFilename();
            InputStream inputStream = mpf.getInputStream();
            long size = mpf.getSize();

            manager = new PostgreSQLManager();
            manager.insert(fileName, inputStream, size, time);

            String file = fileMeta.getFileName();

            files.add(fileMeta);
            System.out.println("files.add(fileMeta)" + file);
        }
        System.out.println("files in list: " + files.size());
//        String fileName = files.get(0).getFileName();
        for (FileMeta fileMeta : files) {
            System.out.println(fileMeta.getFileName() + " : " + time);
        }
//        System.out.println(fileName + " : "+ time);
        return files;
    }

    /***************************************************
     * URL: /rest/controller/get/{value}
     * get(): get file as an attachment
     * @param response : passed by the server
     * @param value : value from the URL
     * @return void
     ****************************************************/
   /* @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
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
    }*/

}
