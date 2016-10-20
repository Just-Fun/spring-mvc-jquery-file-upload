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

    DatabaseManager manager;
    Service service;

    FileMeta fileMeta = null;
    long sessionTime = 0;
    LinkedList<FileMeta> files = new LinkedList<>();

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public LinkedList<FileMeta> upload(MultipartHttpServletRequest request, /*HttpServletResponse response, */HttpSession session) throws IOException {

        manager = new PostgreSQLManager();
        sessionTime = session.getCreationTime();
        MultipartFile mpf;

        Iterator<String> itr = request.getFileNames();

        //2. get each file
        while (itr.hasNext()) {
            synchronized (this) {
                //2.1 get next MultipartFile
                mpf = request.getFile(itr.next());

                String originalFilename = mpf.getOriginalFilename();

                //2.3 create new fileMeta
                fileMeta = new FileMeta();
                fileMeta.setFileName(originalFilename);

                fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
                fileMeta.setFileType(mpf.getContentType());

                InputStream inputStream = mpf.getInputStream();
                manager.insertFile(originalFilename, inputStream, sessionTime);

                files.add(fileMeta);
            }
        }
        return files;
    }

    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
    public void getResult(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {

        if (sessionTime == 0) {
            response.sendRedirect("/spring-mvc-jquery-file-upload");
        } else {
            service = new Service(manager);

            Map<String, Integer> result = service.run(sessionTime);

            request.setAttribute("map", result);

            session.invalidate();
            request.getRequestDispatcher("/resultMap.jsp").forward(request, response);

            manager.insertResult(sessionTime, result);
            files = new LinkedList<>();
        }
    }
}
