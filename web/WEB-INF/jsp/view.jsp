<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
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
    <h2>${resume.fullName}<a href="resume?uuid=${resume.uuid}&action=edit">
        <img src="img/pencil.png" title="edit resume"></a></h2>
    <h3>Контакты:</h3>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
        ${contactEntry.key.toHtml(contactEntry.value)}<br/>
    </c:forEach><br/>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.urise.webapp.model.SectionType,
            com.urise.webapp.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
        <c:choose>
            <c:when test="${(type == SectionType.PERSONAL) || (type == SectionType.OBJECTIVE)}">
                <h4>${type.title}:</h4>
                <li>${section}<br/></li>
                <br/>
            </c:when>
            <c:when test="${(type == SectionType.ACHIEVEMENT) || (type == SectionType.QUALIFICATIONS)}">
                <h4>${type.title}:</h4>
                <c:forEach var="item" items="${(section).items}">
                    <li>${item}<br/></li>
                </c:forEach><br/>
            </c:when>
            <c:when test="${(type == SectionType.EXPERIENCE) || (type == SectionType.EDUCATION)}">
                <h4>${type.title}:</h4>
                <c:forEach var="org" items="${section.organizations}">
                    <h5>${org.homepage.name} <a href="http://${org.homepage.url}">${org.homepage.url}</a></h5>
                    <c:forEach var="pos" items="${org.positions}">
                        <jsp:useBean id="pos" type="com.urise.webapp.model.Organization.Position"/>
                        <p><%=HtmlUtil.formatDates(pos)%><br/>
                                ${pos.title}<br/>
                                ${pos.description}
                        </p>
                    </c:forEach>
                </c:forEach><br/>
            </c:when>
        </c:choose>
    </c:forEach>
    <button onclick="window.history.back()">Назад</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
