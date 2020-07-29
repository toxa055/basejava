package com.urise.webapp.model;

import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Grigory Kislin");

        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");

        resume.addTextSection(SectionType.PERSONAL, "Аналитический склад ума, сильная логика, креативность," +
                "инициативность. Пурист кода и архитектуры.");

        resume.addTextSection(SectionType.OBJECTIVE, "Ведущий стажировок и корпоративного обучения по Java Web " +
                "и Enterprise технологиям.");

        resume.createTextListSection(SectionType.QUALIFICATIONS);
        resume.addTextListSection(SectionType.QUALIFICATIONS, "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, " +
                "Tomcat, Jetty, WebLogic, WSO2");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "Version control: Subversion, Git, Mercury, " +
                "ClearCase, Perforce");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "DB: PostgreSQL(наследование, pgplsql, PL/Python), " +
                "Redis (Jedis), H2, Oracle");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "MySQL, SQLite, MS SQL, HSQLDB");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "Languages: Java, Scala, Python/Jython/PL-Python, " +
                "JavaScript, Groovy");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "Java Frameworks: Java 8 (Time API, Streams), Guava, " +
                "Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), " +
                "Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, " +
                "Selenium (htmlelements).");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "Python: Django");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, " +
                "JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, " +
                "ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "Инструменты: Maven + plugin development, " +
                "Gradle, настройка Ngnix");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "администрирование Hudson/Jenkins, Ant + custom task, " +
                "SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "Отличное знание и опыт применения концепций ООП, " +
                "SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        resume.addTextListSection(SectionType.QUALIFICATIONS, "Родной русский, английский \"upper intermediate\"");

        resume.createTextListSection(SectionType.ACHIEVEMENT);
        resume.addTextListSection(SectionType.ACHIEVEMENT, "С 2013 года: разработка проектов \"Разработка " +
                "Web приложения\", \"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок " +
                "и ведение проектов. Более 1000 выпускников.");
        resume.addTextListSection(SectionType.ACHIEVEMENT, "Реализация двухфакторной аутентификации для онлайн платформы " +
                "управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.addTextListSection(SectionType.ACHIEVEMENT, "Налаживание процесса разработки и непрерывной интеграции " +
                "ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением " +
                "на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        resume.addTextListSection(SectionType.ACHIEVEMENT, "Реализация c нуля Rich Internet Application приложения " +
                "на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, " +
                "Highstock для алгоритмического трейдинга.");
        resume.addTextListSection(SectionType.ACHIEVEMENT, "Создание JavaEE фреймворка для отказоустойчивого " +
                "взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация " +
                "онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        resume.addTextListSection(SectionType.ACHIEVEMENT, "Реализация протоколов по приему платежей всех основных " +
                "платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        resume.createTextBlockSection(SectionType.EDUCATION);
        resume.addTextBlockSection(SectionType.EDUCATION, 2013, 3, 2013, 5,
                "Coursera", null, "\"Functional Programming Principles in Scala\" by Martin Odersky");
        resume.addTextBlockSection(SectionType.EDUCATION, 2011, 3, 2011, 4,
                "Luxoft", null, "Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.");

        resume.createTextBlockSection(SectionType.EXPERIENCE);
        resume.addTextBlockSection(SectionType.EXPERIENCE, 2013, 10, 2020, 7,
                "Java Online Projects", "Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        resume.addTextBlockSection(SectionType.EXPERIENCE, 2014, 10, 2016, 1,
                "Wrike", "Старший разработчик (backend)", "Проектирование и разработка " +
                        "онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, " +
                        "PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");

        for (Map.Entry<ContactType, String> contact : resume.getContactsMap().entrySet()) {
            System.out.println(contact.getKey().getTitle() + ": " + contact.getValue());
        }

        System.out.println("\n===========");

        for (Map.Entry<SectionType, AbstractSection> section : resume.getSectionMap().entrySet()) {
            SectionType sectionType = section.getKey();
            AbstractSection sectionValue = section.getValue();
            System.out.println(sectionType + " / " + sectionType.getTitle());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    System.out.println(((TextSection) sectionValue).getDescription());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    ((TextListSection) sectionValue).getList().forEach(System.out::println);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    ((TextBlockSection) sectionValue).getList().forEach(System.out::println);
                    break;
                default:
                    System.out.println("No data in resume");
                    break;
            }
            System.out.println("===========");
        }
    }
}