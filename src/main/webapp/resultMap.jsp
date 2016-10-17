<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />

<html>
    <head>
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <title>Result</title>
    </head>
    <body>

        <div class="container">
            <div id="toUpload" class="toUpload">
              <a href="/spring-mvc-jquery-file-upload" class="btn btn-default" role="button">Upload new files</a>
            </div>

            <dt>[</dt>
            <c:forEach items="${map}" var="endty" varStatus="loop">
                <dt> {</dt>
                <dt>"value": "${endty.key}",</dt>
                <dt>"count": ${endty.value} </dt>
                <dt>}${!loop.last ? ',' : ''}</dt>
            </c:forEach>
            <dt>]</dt>
        </div>
    </body>
</html>