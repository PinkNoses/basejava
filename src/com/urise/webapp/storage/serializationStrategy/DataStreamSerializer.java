package com.urise.webapp.storage.serializationStrategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL:
                        dos.writeUTF(((TextSection) section).getText());
                        break;
                    case ACHIEVEMENT, QUALIFICATIONS:
                        List<String> listOfString = ((ListSection) section).getList();
                        dos.writeInt(listOfString.size());
                        for (String element : listOfString) {
                            dos.writeUTF(element);
                        }
                        break;
                    case EDUCATION, EXPERIENCE:
                        List<Organization> listOfOrg = ((OrganizationSection) section).getListOrganization();
                        dos.writeInt(listOfOrg.size());
                        for (Organization org : listOfOrg) {
                            dos.writeUTF(org.getOrganizationName());
                            dos.writeUTF(org.getWebsite());
                            List<Organization.Period> periods = org.getPeriods();
                            dos.writeInt(periods.size());
                            for (Organization.Period period : periods) {
                                writeLocalDate(dos, period.getDateStart());
                                writeLocalDate(dos, period.getDateEnd());

                                dos.writeUTF(period.getTitle());
                                if (period.getDescription() != null) {
                                    dos.writeUTF(period.getDescription());
                                } else {
                                    dos.writeUTF("null");
                                }
                            }
                        }
                }
            }
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
        dos.writeInt(localDate.getDayOfMonth());
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        Resume resume;
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            resume = new Resume(uuid, fullName);
            int contactSize = dis.readInt();
            for (int i = 0; i < contactSize; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String value = dis.readUTF();
                resume.addContact(contactType, value);
                System.out.println(contactType.getTitle() + ": " + value);
            }
            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Section section = readSection(sectionType, dis);
                resume.addSection(sectionType, section);
                System.out.println(sectionType.getTitle() + ": " + section);
            }
            return resume;
        }
    }

    private Section readSection(SectionType sectionType, DataInputStream dis) throws IOException {
        switch (sectionType) {
            case OBJECTIVE, PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT, QUALIFICATIONS:
                int sizeOfStringList = dis.readInt();
                List<String> stringList = new ArrayList<>(sizeOfStringList);
                for (int i = 0; i < sizeOfStringList; i++) {
                    stringList.add(dis.readUTF());
                }
                return new ListSection(stringList);
            case EDUCATION, EXPERIENCE:
                int sizeOfOrgList = dis.readInt();
                List<Organization> orgList = new ArrayList<>(sizeOfOrgList);
                for (int i = 0; i < sizeOfOrgList; i++) {
                    String organizationName = dis.readUTF();
                    String website = dis.readUTF();
                    int sizeOfPeriodList = dis.readInt();
                    List<Organization.Period> periodList = new ArrayList<>(sizeOfPeriodList);
                    for (int j = 0; j < sizeOfPeriodList; j++) {
                        LocalDate dateStart = readLocalDate(dis);
                        LocalDate dateEnd = readLocalDate(dis);

                        String title = dis.readUTF();
                        String description = dis.readUTF();
                        if (description.equals("null")) {
                            description = null;
                        }
                        Organization.Period period = new Organization.Period(dateStart, dateEnd, title, description);
                        periodList.add(period);
                    }
                    Organization organization = new Organization(organizationName, website, periodList);
                    orgList.add(organization);
                }
                return new OrganizationSection(orgList);
        }
        throw new IllegalStateException();
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }
}