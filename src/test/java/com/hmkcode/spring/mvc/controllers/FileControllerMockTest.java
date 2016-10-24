package com.hmkcode.spring.mvc.controllers;

import com.hmkcode.spring.mvc.model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * Created by Serzh on 10/23/16.
 */
public class FileControllerMockTest {


    private FileController controller;
    private DatabaseManager manager;

    private MultipartHttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @Before
    public void setup() {
        controller = new FileController();
        manager = mock(DatabaseManager.class);
        request = mock(MultipartHttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }


    @Test
    public void upload() throws Exception {

        long sessionTime = 111111111L;
        HashMap<String, String> multipartFiles = new LinkedHashMap<>();
        multipartFiles.put("some1", "some1");
        multipartFiles.put("some2", "some2");
        Iterator<String> itr = multipartFiles.keySet().iterator();

        MultipartFile firstFile = new MockMultipartFile("data", "filename.txt",
                "text/plain", "some xml".getBytes());

        when(session.getCreationTime()).thenReturn(sessionTime);
        when(request.getFileNames()).thenReturn(itr);
        when(request.getFile(itr.next())).thenReturn(firstFile); // TODO understand why 'mpf' == null still
        when(request.getFile(itr.next())).thenReturn(firstFile);

        controller.upload(request, session);

        verify(session).getCreationTime();
        verify(request).getFileNames();
//        verify(request).getFile(itr.next());
//        verify(manager).insertFile(firstFile.getOriginalFilename(), firstFile.getInputStream(), sessionTime);
    }

    @Test
    public void testGetResultRedirect() throws Exception {
        controller.getResult(request, response, session);
        verify(response).sendRedirect("/spring-mvc-jquery-file-upload");
    }
}