<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
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
            <ul class="wrapper">
                <li class="form-row">
                    <label for="fullName"><b>Имя</b></label>
                    <input type="text" id="fullName" name="fullName" value="${resume.fullName}"/>
                </li>
            </ul>
        </dl>
        <h3>Контакты:</h3>
        <dl>
            <ul class="wrapper">
                <c:forEach var="cType" items="${ContactType.values()}">
                    <li class="form-row">
                        <label for="contact">${cType.title}</label>
                        <input type="text" id="contact" name="${cType.name()}" value="${resume.getContact(cType)}"/>
                    </li>
                </c:forEach>
            </ul>
        </dl>
        <h3>Секции:</h3>
        <c:forEach var="sType" items="${SectionType.values()}">
            <jsp:useBean id="sType" type="com.urise.webapp.model.SectionType"/>
            <c:choose>
                <c:when test="${(sType.name() == SectionType.PERSONAL) || (sType.name() == SectionType.OBJECTIVE)}">
                    <dl>
                        <ul class="wrapper">
                            <li class="form-row">
                                <label for="textSection"><b>${sType.title}</b></label>
                                <input type="text" id="textSection" name="${sType.name()}"
                                       value="${resume.getSection(sType)}"/>
                            </li>
                        </ul>
                    </dl>
                </c:when>
                <c:when test="${(sType.name() == SectionType.ACHIEVEMENT) || (sType.name() == SectionType.QUALIFICATIONS)}">
                    <dl>
                        <ul class="wrapper">
                            <li class="form-row">
                                <label for="listSection"><b>${sType.title}</b></label>
                                <textarea name="${sType.name()}" rows="15"
                                          id="listSection"><%=String.join("\n",
                                        ((ListSection) resume.getSection(sType)).getItems())%></textarea>
                            </li>
                        </ul>
                    </dl>
                </c:when>
                <c:when test="${(sType.name() == SectionType.EXPERIENCE) || (sType.name() == SectionType.EDUCATION)}">
                    <dl>
                        <dt><h4>${sType.title}</h4></dt>
                        <dd>
                            <ul class="wrapper">
                                <c:forEach var="org" items="${resume.getSection(sType).organizations}"
                                           varStatus="counter">
                                    <li class="form-row">
                                        <label for="name">Название:</label>
                                        <input type="text" id="name" name="${sType.name()}"
                                               value="${org.homepage.name}"/>
                                    </li>
                                    <li class="form-row">
                                        <label for="homepage">Домашняя страница:</label>
                                        <input type="text" id="homepage" name="${sType.name()}url"
                                               value="${org.homepage.url}"/>
                                    </li>
                                    <c:forEach var="pos" items="${org.positions}">
                                        <jsp:useBean id="pos" type="com.urise.webapp.model.Organization.Position"/>
                                        <li class="form-row">
                                            <label for="startDate">Начальная дата:</label>
                                            <input type="text" id="startDate"
                                                   name="${sType.name()}${counter.index}startDate"
                                                   value="<%=DateUtil.format(pos.getStartDate())%>"
                                                   placeholder="yyyy/MM"/>
                                        <li class="form-row">
                                            <label for="endDate">Конечная дата:</label>
                                            <input type="text" id="endDate"
                                                   name="${sType.name()}${counter.index}endDate"
                                                   value="<%=DateUtil.format(pos.getEndDate())%>"
                                                   placeholder="yyyy/MM"/>
                                        </li>
                                        <li class="form-row">
                                            <label for="title">Позиция:</label>
                                            <input type="text" id="title"
                                                   name="${sType.name()}${counter.index}title" value="${pos.title}">
                                        </li>
                                        <li class="form-row">
                                            <label for="description">Описание:</label>
                                            <textarea name="${sType.name()}${counter.index}description" rows="12"
                                                      id="description">${pos.description}</textarea>
                                        </li>
                                    </c:forEach>
                                    <hr>
                                </c:forEach>
                            </ul>
                        </dd>
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
