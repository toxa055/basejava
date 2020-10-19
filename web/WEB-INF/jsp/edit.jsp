<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="cType" items="${ContactType.values()}">
            <dl>
                <dt>${cType.title}</dt>
                <dd><input type="text" name="${cType.name()}" size=30 value="${resume.getContact(cType)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sType" items="${SectionType.values()}">
            <jsp:useBean id="sType" type="com.urise.webapp.model.SectionType"/>
            <c:choose>
                <c:when test="${(sType.name() == SectionType.PERSONAL) || (sType.name() == SectionType.OBJECTIVE)}">
                    <dl>
                        <dt>${sType.title}</dt>
                        <dd><input type="text" name="${sType.name()}" size=120 value="${resume.getSection(sType)}"></dd>
                    </dl>
                </c:when>
                <c:when test="${(sType.name() == SectionType.ACHIEVEMENT) || (sType.name() == SectionType.QUALIFICATIONS)}">
                    <dl>
                        <dt>${sType.title}</dt>
                        <c:if test="${resume.getSection(sType) != null}">
                            <dd>
                                <textarea name="${sType.name()}" id="${sType.name()}" cols="100"
                                          rows="20"><%=String.join("\n",
                                        ((ListSection) resume.getSection(sType)).getItems())%></textarea>
                            </dd>
                        </c:if>
                        <c:if test="${resume.getSection(sType) == null}">
                            <dd>
                                <textarea name="${sType.name()}" id="${sType.name()}" cols="100" rows="10"></textarea>
                            </dd>
                        </c:if>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>
        <br/>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
