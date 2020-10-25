<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.urise.webapp.model.*" %>
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
                                <c:if test="${resume.getSection(sType) != null}">
                                <textarea name="${sType.name()}" rows="15"
                                          id="listSection"><%=String.join("\n",
                                        ((ListSection) resume.getSection(sType)).getItems())%></textarea>
                                </c:if>
                                <c:if test="${resume.getSection(sType) == null}">
                                    <textarea name="${sType.name()}" rows="10" id="listSection"></textarea>
                                </c:if>
                            </li>
                        </ul>
                    </dl>
                </c:when>
                <c:when test="${(sType.name() == SectionType.EXPERIENCE) || (sType.name() == SectionType.EDUCATION)}">
                    <dl>
                        <dt><h4>${sType.title}</h4></dt>
                        <dd>
                            <ul class="wrapper">
                                <c:if test="${resume.getSection(sType) != null}">
                                    <c:forEach var="org" items="${resume.getSection(sType).organizations}">
                                        <c:forEach var="pos" items="${org.positions}">
                                            <li class="form-row">
                                                <label for="name">Название:</label>
                                                <input type="text" id="name" name="${sType.name()}"
                                                       value="${org.homepage.name}"/>
                                            </li>
                                            <li class="form-row">
                                                <label for="homepage">Домашняя страница:</label>
                                                <input type="text" id="homepage" name="${sType.name()}"
                                                       value="${org.homepage.url}"/>
                                            </li>
                                            <li class="form-row">
                                                <label for="startDate">Начальная дата:</label>
                                                <input type="text" id="startDate" name="${sType.name()}"
                                                       value="${pos.startDate}"/>
                                            </li>
                                            <li class="form-row">
                                                <label for="endDate">Конечная дата:</label>
                                                <input type="text" id="endDate" name="${sType.name()}"
                                                       value="${pos.endDate}"/>
                                            </li>
                                            <li class="form-row">
                                                <label for="position">Позиция:</label>
                                                <input type="text" id="position" name="${sType.name()}"
                                                       value="${pos.title}">
                                            </li>
                                            <li class="form-row">
                                                <label for="description">Описание:</label>
                                                <textarea name="${sType.name()}" rows="12"
                                                          id="description">${pos.description}</textarea>
                                            </li>
                                        </c:forEach>
                                        <hr>
                                    </c:forEach>
                                    <li class="form-row">
                                        <label for="addName">Название:</label>
                                        <input type="text" id="addName" name="${sType.name()}"/>
                                    </li>
                                    <li class="form-row">
                                        <label for="addHomepage">Домашняя страница:</label>
                                        <input type="text" id="addHomepage" name="${sType.name()}"/>
                                    </li>
                                    <li class="form-row">
                                        <label for="addStartDate">Начальная дата:</label>
                                        <input type="text" id="addStartDate" name="${sType.name()}" value="ГГГГ-ММ-Д"/>
                                    </li>
                                    <li class="form-row">
                                        <label for="addEndDate">Конечная дата:</label>
                                        <input type="text" id="addEndDate" name="${sType.name()}" value="ГГГГ-ММ-Д"/>
                                    </li>
                                    <li class="form-row">
                                        <label for="addPosition">Позиция:</label>
                                        <input type="text" id="addPosition" name="${sType.name()}">
                                    </li>
                                    <li class="form-row">
                                        <label for="addDescription">Описание:</label>
                                        <textarea name="${sType.name()}" rows="12" id="addDescription"></textarea>
                                    </li>
                                    <hr>
                                </c:if>
                                <c:if test="${resume.getSection(sType) == null}">
                                    <c:forEach begin="0" end="1" varStatus="loop">
                                        <li class="form-row">
                                            <label for="newName">Название:</label>
                                            <input type="text" id="newName" name="${sType.name()}"/>
                                        </li>
                                        <li class="form-row">
                                            <label for="newHomepage">Домашняя страница:</label>
                                            <input type="text" id="newHomepage" name="${sType.name()}"/>
                                        </li>
                                        <li class="form-row">
                                            <label for="newStartDate">Начальная дата:</label>
                                            <input type="text" id="newStartDate" name="${sType.name()}"
                                                   value="ГГГГ-ММ-Д"/>
                                        </li>
                                        <li class="form-row">
                                            <label for="newEndDate">Конечная дата:</label>
                                            <input type="text" id="newEndDate" name="${sType.name()}"
                                                   value="ГГГГ-ММ-Д"/>
                                        </li>
                                        <li class="form-row">
                                            <label for="newPosition">Позиция:</label>
                                            <input type="text" id="newPosition" name="${sType.name()}">
                                        </li>
                                        <li class="form-row">
                                            <label for="newDescription">Описание:</label>
                                            <textarea name="${sType.name()}" rows="12" id="newDescription"></textarea>
                                        </li>
                                        <hr>
                                    </c:forEach>
                                </c:if>
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
