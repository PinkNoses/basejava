package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("I.Petrov");
        System.out.println(resume);
        //Заполнение раздела "Контакты"
        Map<ContactType, String> contacts = resume.getContacts();
        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "skype:grigory.kislin");
        contacts.put(ContactType.E_MAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "Профиль LinkedIn");
        contacts.put(ContactType.GITHUB, "Профиль GitHub");
        contacts.put(ContactType.STACKOVERFLOW, "Профиль Stackoverflow");
        contacts.put(ContactType.HOMEPAGE, "Домашняя страница");
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        System.out.println();

        //Заполнение раздела "Секции"
        Map<SectionType, Section> sections = resume.getSections();
        sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям"));
        sections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, " +
                "креативность, инициативность. " + "\nПурист кода и архитектуры."));

        List<String> achievements = new ArrayList<>();
        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения " +
                "автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, " +
                "участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 3500 выпускников.");
        sections.put(SectionType.ACHIEVEMENT, new ListSection(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        sections.put(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        List<Organisation> experiences = new ArrayList<>();
        experiences.add(new Organisation(LocalDate.of(2013, 10, 1), LocalDate.now(), "Java " +
                "Online Projects", "Автор проекта.", "Создание, организация и проведение Java " +
                "онлайн проектов и стажировок."));
        experiences.add(new Organisation(LocalDate.of(2014, 10, 1),
                LocalDate.of(2016, 1, 1), "Wrike", "Старший разработчик " +
                "(backend)", "Проектирование и разработка онлайн платформы управления проектами " +
                "Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
                "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        sections.put(SectionType.EXPERIENCE, new OrganisationSection(experiences));

        List<Organisation> educations = new ArrayList<>();
        educations.add(new Organisation(LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1),
                "Coursera", "'Functional Programming Principles in Scala' by Martin Odersky", null));
        educations.add(new Organisation(LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1),
                "Luxoft", "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null));
        sections.put(SectionType.EDUCATION, new OrganisationSection(educations));

        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            System.out.println(entry.getKey().getTitle() + ":\n" + entry.getValue() + "\n");
        }
    }
}
