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

        <div id="toUpload" class="toUpload">
            <a href="/spring-mvc-jquery-file-upload">Upload new files</a>
        </div>

        <dt>[</dt>
        <c:forEach items="${map}" var="endty" varStatus="loop">
            <dt> {</dt>
            <dt>"value": "${endty.key}",</dt>
            <dt>"count": ${endty.value} </dt>
            <dt>}${!loop.last ? ',' : ''}</dt>
        </c:forEach>
        <dt>]</dt>

    </body>
</html>