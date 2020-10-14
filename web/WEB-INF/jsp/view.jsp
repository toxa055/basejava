<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">
        <img src="img/pencil.png" title="edit resume"></a></h2>
    <b>Контакты:</b><br/>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
        <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
    </c:forEach><br/>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.urise.webapp.model.SectionType,
            com.urise.webapp.model.AbstractSection>"/>
        <c:choose>
            <c:when test="${sectionEntry.key == SectionType.PERSONAL || sectionEntry.key == SectionType.OBJECTIVE}">
                <b><%=sectionEntry.getKey().getTitle()%>:</b><br/>
                <li><%=sectionEntry.getValue()%><br/></li>
            </c:when>
            <c:when test="${sectionEntry.key == SectionType.ACHIEVEMENT || sectionEntry.key == SectionType.QUALIFICATIONS}">
                <b><%=sectionEntry.getKey().getTitle()%>:</b><br/>
                <c:forEach var="item" items="<%=((ListSection) sectionEntry.getValue()).getItems()%>">
                    <li>${item}<br/></li>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
