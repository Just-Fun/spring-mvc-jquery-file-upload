<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Message</title>
    </head>
    <body>
        [<br/>
        <c:forEach items="${map}" var="entry" varStatus="loop">
            { <br/>
            "value": "${entry.key}", <br/>
            "count": ${entry.value} <br/>
             }
             ${!loop.last ? ',' : ''}<br/>
        </c:forEach>
        ]</br>
        </br>
        <div id="toUpload" class="toUpload">
            <a href="/spring-mvc-jquery-file-upload">Upload new files</a>
        </div>
    </body>
</html>