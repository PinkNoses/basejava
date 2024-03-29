package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ResumeTestData {
    public Resume createResume(String fullName) {
        Resume resume = new Resume(fullName);
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
        sections.put(SectionType.OBJECTIVE, new TextSection("objective"));
        sections.put(SectionType.PERSONAL, new TextSection("personal"));

        List<String> achievements = new ArrayList<>();
        achievements.add("ACHIEVEMENT_1");
        achievements.add("ACHIEVEMENT_2");
        sections.put(SectionType.ACHIEVEMENT, new ListSection(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("QUALIFICATIONS_1");
        qualifications.add("QUALIFICATIONS_2");
        qualifications.add("QUALIFICATIONS_3");
        sections.put(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        //Заполнение раздела "Опыт работы"
        /*List<Organization> experiences = new ArrayList<>();

        List<Organization.Period> expJavaops = new ArrayList<>();
        List<Organization.Period> expWrike = new ArrayList<>();

        expJavaops.add(new Organization.Period(LocalDate.of(2013, 10, 1), LocalDate.now(), "Автор проекта.",
                "Создание, организация и проведение Java " + "онлайн проектов и стажировок."));
        experiences.add(new Organization("Java Online Projects", "https://javaops.ru/", expJavaops));

        expWrike.add(new Organization.Period(LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления " +
                "проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
                "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        experiences.add(new Organization("Wrike", "https://www.wrike.com/", expWrike));

        sections.put(SectionType.EXPERIENCE, new OrganizationSection(experiences));

        //Заполнение раздела "Образование"
        List<Organization> educations = new ArrayList<>();

        List<Organization.Period> eduCoursera = new ArrayList<>();
        eduCoursera.add(new Organization.Period(LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1),
                "'Functional Programming Principles in Scala' by Martin Odersky", null));
        educations.add(new Organization("Coursera", "https://www.coursera.org/learn/scala-" +
                "functional-programming", eduCoursera));

        List<Organization.Period> eduLuxoft = new ArrayList<>();
        eduLuxoft.add(new Organization.Period(LocalDate.of(2011, 3, 1), LocalDate.of(2013, 5, 1),
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null));
        educations.add(new Organization("Luxoft", "https://prmotion.me/?ID=22366", eduLuxoft));

        List<Organization.Period> eduITMOUniversity = new ArrayList<>();
        eduITMOUniversity.add(new Organization.Period(LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1),
                "Аспирантура (программист С, С++)", null));
        eduITMOUniversity.add(new Organization.Period(LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1),
                "Инженер (программист Fortran, C)", null));
        educations.add(new Organization("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", "https://itmo.ru/", eduITMOUniversity));
        sections.put(SectionType.EDUCATION, new OrganizationSection(educations));

        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            System.out.println(entry.getKey().getTitle() + ":\n" + entry.getValue() + "\n");
        }*/
        return resume;
    }

}