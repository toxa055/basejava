package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = fillResume("uuid1", "Grigory Kislin");

        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey().getTitle() + ": " + contact.getValue());
        }

        System.out.println("\n===========");

        for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
            SectionType sectionType = section.getKey();
            AbstractSection sectionValue = section.getValue();
            System.out.println(sectionType + " / " + sectionType.getTitle());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    System.out.println(((SimpleTextSection) sectionValue).getDescription());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    ((ListSection) sectionValue).getItems().forEach(System.out::println);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    ((OrganizationSection) sectionValue).getOrganizations().forEach(System.out::println);
                    break;
                default:
                    System.out.println("No data in resume");
                    break;
            }
            System.out.println("===========");
        }
    }

    public static Resume fillResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        List<String> qualificationsList = new ArrayList<>();
        List<String> achievementList = new ArrayList<>();
        List<Organization> educationList = new ArrayList<>();
        List<Organization> experienceList = new ArrayList<>();
        ListSection qualificationsSection = new ListSection(qualificationsList);
        ListSection achievementSection = new ListSection(achievementList);
        OrganizationSection educationSection = new OrganizationSection(educationList);
        OrganizationSection experienceSection = new OrganizationSection(experienceList);

        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.MOBILE, "+7(921) 855-0482");
        resume.addContact(ContactType.HOME_PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "gkislin");
        resume.addContact(ContactType.GITHUB, "gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "548473");
        resume.addContact(ContactType.HOME_PAGE, "gkislin.ru");

        resume.addSection(SectionType.PERSONAL, new SimpleTextSection("Аналитический склад ума," +
                "сильная логика, креативность,инициативность. Пурист кода и архитектуры."));

        resume.addSection(SectionType.OBJECTIVE, new SimpleTextSection("Ведущий стажировок и" +
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
        qualificationsList.add("Родной русский, английский upper intermediate");

        achievementList.add("С 2013 года: разработка проектов Разработка Web приложения, Java Enterprise, " +
                "Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное " +
                "взаимодействие (JMS/AKKA). Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
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

        educationList.add(new Organization("Coursera", null,
                new Organization.Position(2013, Month.MARCH, 2013, Month.MAY,
                        "Functional Programming Principles in Scala by Martin Odersky", null)));
        educationList.add(new Organization("Luxoft", null,
                new Organization.Position(2011, Month.MARCH, 2011, Month.APRIL,
                        "Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.", null)));
        educationList.add(new Organization("Siemens AG", null,
                new Organization.Position(2005, Month.JANUARY, 2004, Month.APRIL,
                        "3 месяца обучения мобильным IN сетям (Берлин)", null)));
        educationList.add(new Organization("Alcatel", null,
                new Organization.Position(1997, Month.SEPTEMBER, 1998, Month.MARCH,
                        "6 месяцев обучения цифровым телефонным сетям (Москва)", null)));
        educationList.add(new Organization("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", null,
                new Organization.Position(1993, Month.SEPTEMBER, 1996, Month.JULY,
                        "Аспирантура (программист С, С++)", null),
                new Organization.Position(1987, Month.SEPTEMBER, 1993, Month.JULY,
                        "Инженер (программист Fortran, C)", null)));
        educationList.add(new Organization("Заочная физико-техническая школа при МФТИ", null,
                new Organization.Position(1984, Month.SEPTEMBER, 1987, Month.JUNE,
                        "Закончил с отличием", null)));

        experienceList.add(new Organization("Java Online Projects", "javaops.ru",
                new Organization.Position(2013, Month.OCTOBER,
                        "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.")));
        experienceList.add(new Organization("Wrike", "wrike.com",
                new Organization.Position(2014, Month.OCTOBER, 2016, Month.JANUARY,
                        "Старший разработчик (backend)", "Проектирование и разработка " +
                        "онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, " +
                        "PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));
        experienceList.add(new Organization("RIT Center", null,
                new Organization.Position(2012, Month.APRIL, 2014, Month.OCTOBER,
                        "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: " +
                        "релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                        "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                        "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего " +
                        "назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование " +
                        "из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, " +
                        "Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                        "Unix shell remote scripting via ssh tunnels, PL/Python.")));
        experienceList.add(new Organization("Luxoft (Deutsche Bank)", "luxoft.ru",
                new Organization.Position(2010, Month.DECEMBER, 2012, Month.APRIL,
                        "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate," +
                        "Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                        "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                        "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")));
        experienceList.add(new Organization("Yota", "yota.ru",
                new Organization.Position(2008, Month.JUNE, 2010, Month.DECEMBER,
                        "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела " +
                        "Платежные Системы (GlassFish v2 .1, v3, OC4J, EJB3, JAX - WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                        "Реализация администрирования, статистики и мониторинга фреймворка. " +
                        "Разработка online JMX клиента (Python / Jython, Django, ExtJS)")));
        experienceList.add(new Organization("Enkata", "enkata.com",
                new Organization.Position(2007, Month.MARCH, 2008, Month.JUNE,
                        "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, " +
                        "Hibernate 3.0, Tomcat, JMS)частей кластерного J2EE приложения (OLAP, Data mining)")));
        experienceList.add(new Organization("Siemens AG", "siemens.com",
                new Organization.Position(2005, Month.JANUARY, 2007, Month.FEBRUARY,
                        "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов," +
                        "реализация и отладка ПО на мобильной IN платформе Siemens @vantage(Java, Unix).")));
        experienceList.add(new Organization("Alcatel", "alcatel.ru",
                new Organization.Position(1997, Month.SEPTEMBER, 2005, Month.JANUARY,
                        "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, " +
                        "внедрение ПО цифровой телефонной станции Alcatel 1000 S12(CHILL, ASM).")));

        resume.addSection(SectionType.QUALIFICATIONS, qualificationsSection);
        resume.addSection(SectionType.ACHIEVEMENT, achievementSection);
        resume.addSection(SectionType.EDUCATION, educationSection);
        resume.addSection(SectionType.EXPERIENCE, experienceSection);
        return resume;
    }
}