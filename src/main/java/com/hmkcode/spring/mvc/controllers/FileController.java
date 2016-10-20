package com.hmkcode.spring.mvc.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hmkcode.spring.mvc.model.DatabaseManager;
import com.hmkcode.spring.mvc.model.PostgreSQLManager;
import com.hmkcode.spring.mvc.service.Service;
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

    //    @Autowired
    DatabaseManager manager;// TODO bean
    Service service;

    LinkedList<FileMeta> files/* = new LinkedList<>();*/;
    FileMeta fileMeta = null;
    long sessionTime = 0;

    /***************************************************
     * URL: /rest/controller/upload
     * upload(): receives files
     * @param request : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     ****************************************************/
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {

        manager = new PostgreSQLManager();// TODO bean
        files = new LinkedList<>();
        sessionTime = session.getCreationTime();
        MultipartFile mpf;

        Iterator<String> itr = request.getFileNames();

        //2. get each file
        synchronized(this) { // TODO optimized
            while (itr.hasNext()) {

                //2.1 get next MultipartFile
                mpf = request.getFile(itr.next());

                //2.2 if files > 10 remove the first from the list
                if (files.size() >= 10) {
                    files.pop();
                }
                String originalFilename = mpf.getOriginalFilename();
                System.out.println("From mpf: " + originalFilename);

                //2.3 create new fileMeta
                fileMeta = new FileMeta();
                fileMeta.setFileName(originalFilename);
                String file = fileMeta.getFileName();
                System.out.println("From fileMeta: " + file);

                fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
                fileMeta.setFileType(mpf.getContentType());
//            fileMeta.setTimeCreated(sessionTime);

                InputStream inputStream = mpf.getInputStream();
                manager.insertFile(originalFilename, inputStream, sessionTime);

                files.add(fileMeta);
                System.out.println("files.add(fileMeta)" + fileMeta.getFileName());
            }
        }
//        System.out.println("files in list: " + files.size());
//        String fileName = files.get(0).getFileName();
        System.out.println("Foreach: ");
        for (FileMeta fileMeta : files) { // TODO разобраться
            System.out.println(fileMeta.getFileName());
        }
//        System.out.println(fileName + " : "+ sessionTime);
        return files;
    }

    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
    public void getResult(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {

        if (sessionTime == 0) {
            System.out.println("sessionTime == 0");
            response.sendRedirect("/spring-mvc-jquery-file-upload");
        } else {
            service = new Service(); // TODO bean

            Map<String, Integer> result = service.run(sessionTime);

            request.setAttribute("map", result);

            System.out.println("/getResult, sessionTime: " + sessionTime);

            session.invalidate();
            request.getRequestDispatcher("/resultMap.jsp").forward(request, response);
            manager.insertResult(sessionTime, result);
        }
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
