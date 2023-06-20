package com.urise.webapp.storage.serializationStrategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy {
    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeCollection(dos, contacts.entrySet(), element -> {
                dos.writeUTF(element.getKey().name());
                dos.writeUTF(element.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();
            writeCollection(dos, sections.entrySet(), element -> {
                SectionType sectionType = element.getKey();
                Section section = element.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) section).getText());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> listOfString = ((ListSection) section).getList();
                        writeCollection(dos, listOfString, dos::writeUTF);
                    }
                    case EDUCATION, EXPERIENCE -> {
                        List<Organization> listOfOrg = ((OrganizationSection) section).getListOrganization();
                        writeCollection(dos, listOfOrg, organization -> {
                            dos.writeUTF(organization.getOrganizationName());
                            dos.writeUTF(organization.getWebsite());
                            List<Organization.Period> periods = organization.getPeriods();
                            writeCollection(dos, periods, period -> {
                                writeLocalDate(dos, period.getDateStart());
                                writeLocalDate(dos, period.getDateEnd());
                                dos.writeUTF(period.getTitle());
                                if (period.getDescription() != null) {
                                    dos.writeUTF(period.getDescription());
                                } else {
                                    dos.writeUTF("null");
                                }
                            });
                        });
                    }
                }
            });
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        Resume resume;
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            resume = new Resume(uuid, fullName);
            readCollection(dis, () -> {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String value = dis.readUTF();
                resume.addContact(contactType, value);
            });
            readCollection(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Section section = readSection(sectionType, dis);
                resume.addSection(sectionType, section);
            });
            return resume;
        }
    }

    private interface ElementWriter<T> {
        void write(T element) throws IOException;

    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
        dos.writeInt(localDate.getDayOfMonth());
    }

    private Section readSection(SectionType sectionType, DataInputStream dis) throws IOException {
        return switch (sectionType) {
            case OBJECTIVE, PERSONAL -> new TextSection(dis.readUTF());
            case ACHIEVEMENT, QUALIFICATIONS -> new ListSection(readList(dis, dis::readUTF));
            case EDUCATION, EXPERIENCE -> new OrganizationSection(readList(dis, () -> {
                String organizationName = dis.readUTF();
                String website = dis.readUTF();
                List<Organization.Period> periodList = readList(dis, () -> {
                            LocalDate dateStart = readLocalDate(dis);
                            LocalDate dateEnd = readLocalDate(dis);
                            String title = dis.readUTF();
                            String description = dis.readUTF();
                            if (description.equals("null")) {
                                description = null;
                            }
                            return new Organization.Period(dateStart, dateEnd, title, description);
                        }
                );
                return new Organization(organizationName, website, periodList);
            }));
        };
    }

    @FunctionalInterface
    private interface ElementReader<T> {
        T read() throws IOException;
    }

    @FunctionalInterface
    private interface SectionReader {
        void read() throws IOException;
    }

    private void readCollection(DataInputStream dis, SectionReader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }
}