<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <p>Список резюме:</p>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>${resume.getContact(ContactType.EMAIL)}</td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete">
                    <img src="img/delete.png" title="delete resume"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit">
                    <img src="img/pencil.png" title="edit resume"></a></td>
            </tr>
        </c:forEach>
    </table>
    <p><a href="resume?uuid=${""}&action=edit"><img src="img/add.png" title="new resume"> add resume</a></p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
