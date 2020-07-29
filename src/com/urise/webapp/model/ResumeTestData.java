package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Grigory Kislin");

        List<String> qualificationsList = new ArrayList<>();
        List<String> achievementList = new ArrayList<>();
        List<TextBlock> educationList = new ArrayList<>();
        List<TextBlock> experienceList = new ArrayList<>();

        TextListSection qualificationsSection = new TextListSection(qualificationsList);
        TextListSection achievementSection = new TextListSection(achievementList);
        TextBlockSection educationSection = new TextBlockSection(educationList);
        TextBlockSection experienceSection = new TextBlockSection(experienceList);

        resume.getContactsMap().put(ContactType.PHONE, "+7(921) 855-0482");
        resume.getContactsMap().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContactsMap().put(ContactType.EMAIL, "gkislin@yandex.ru");

        resume.getSectionMap().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума," +
                "сильная логика, креативность,инициативность. Пурист кода и архитектуры."));

        resume.getSectionMap().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и" +
                "корпоративного обучения по Java Web и Enterprise технологиям."));

        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        qualificationsList.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualificationsList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualificationsList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualificationsList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, " +
                "GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, " +
                "Selenium (htmlelements).");
        qualificationsList.add("Python: Django");
        qualificationsList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualificationsList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualificationsList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, " +
                "StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, " +
                "BPMN2, LDAP, OAuth1, OAuth2, JWT");
        qualificationsList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualificationsList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, " +
                "Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualificationsList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования");
        qualificationsList.add("Родной русский, английский \"upper intermediate\"");

        achievementList.add("С 2013 года: разработка проектов \"Разработка " +
                "Web приложения\", \"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок " +
                "и ведение проектов. Более 1000 выпускников.");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы " +
                "управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementList.add("Налаживание процесса разработки и непрерывной интеграции " +
                "ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения " +
                "управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и " +
                "авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievementList.add("Реализация c нуля Rich Internet Application приложения " +
                "на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, " +
                "Highstock для алгоритмического трейдинга.");
        achievementList.add("Создание JavaEE фреймворка для отказоустойчивого " +
                "взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация " +
                "онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievementList.add("Реализация протоколов по приему платежей всех основных " +
                "платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        educationList.add(new TextBlock(2013, 3, 2013, 5, "Coursera",
                "\"Functional Programming Principles in Scala\" by Martin Odersky"));
        educationList.add(new TextBlock(2011, 3, 2011, 4, "Luxoft",
                "Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML."));
        educationList.add(new TextBlock(2005, 1, 2004, 4, "Siemens AG",
                "3 месяца обучения мобильным IN сетям (Берлин)"));
        educationList.add(new TextBlock(1997, 9, 1998, 3, "Alcatel",
                "6 месяцев обучения цифровым телефонным сетям (Москва)"));
        educationList.add(new TextBlock(1993, 9, 1996, 7,
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "Аспирантура (программист С, С++)"));
        educationList.add(new TextBlock(1987, 9, 1993, 7,
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "Инженер (программист Fortran, C)"));
        educationList.add(new TextBlock(1984, 9, 1987, 6,
                "Заочная физико-техническая школа при МФТИ", "Закончил с отличием"));

        experienceList.add(new TextBlock(2013, 10, 2020, 7,
                "Java Online Projects", "Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experienceList.add(new TextBlock(2014, 10, 2016, 1, "Wrike",
                "Старший разработчик (backend)", "Проектирование и разработка " +
                "онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, " +
                "PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        experienceList.add(new TextBlock(2012, 4, 2014, 10, "RIT Center",
                "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: " +
                "релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                "сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, " +
                "OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python."));
        experienceList.add(new TextBlock(2010, 12, 2012, 4, "Luxoft (Deutsche Bank)",
                "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate," +
                "Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        experienceList.add(new TextBlock(2008, 6, 2010, 12, "Yota",
                "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка " +
                "для отдела \"Платежные Системы\" (GlassFish v2 .1, v3, OC4J, EJB3, JAX - WS RI 2.1, Servlet 2.4, JSP, " +
                "JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. " +
                "Разработка online JMX клиента (Python / Jython, Django, ExtJS)"));
        experienceList.add(new TextBlock(2007, 3, 2008, 6, "Enkata",
                "Разработчик ПО", "Реализация клиентской (Eclipse RCP) " +
                "и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS)частей кластерного J2EE приложения (OLAP, Data mining)"));
        experienceList.add(new TextBlock(2005, 1, 2007, 2, "Siemens AG",
                "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов," +
                "реализация и отладка ПО на мобильной IN платформе Siemens @vantage(Java, Unix)."));
        experienceList.add(new TextBlock(1997, 9, 2005, 1, "Alcatel",
                "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, " +
                "внедрение ПО цифровой телефонной станции Alcatel 1000 S12(CHILL, ASM)."));

        resume.getSectionMap().put(SectionType.QUALIFICATIONS, qualificationsSection);
        resume.getSectionMap().put(SectionType.ACHIEVEMENT, achievementSection);
        resume.getSectionMap().put(SectionType.EDUCATION, educationSection);
        resume.getSectionMap().put(SectionType.EXPERIENCE, experienceSection);

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