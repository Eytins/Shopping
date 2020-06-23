<%@ page contentType="image/jpeg" %>
<%@ page language="java" pageEncoding="utf-8" %>

<jsp:useBean id="image" scope="page" class="com.zte.shopping.util.CheckCode"></jsp:useBean>

<%
    String str = image.getCode(0, 0, response.getOutputStream());
        session.setAttribute("checkCapture", str);
        out.clear();
        out = pageContext.pushBody();
%>