package ru.javawebinar.topjava.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CONTACT_TEL_DETAIL")
public class ContactTelDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TEL_TYPE")
    private String telType;

    @Column(name = "TEL_NUMBER")
    private String telNumber;

    @Version
    @Column(name = "VERSION")
    private int version;

    private Contact contact;

    public ContactTelDetail() {}

    public ContactTelDetail(String telType, String telNumber) {
        this.telType = telType;
        this.telNumber = telNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelType() {
        return telType;
    }

    public void setTelType(String telType) {
        this.telType = telType;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "ContactTelDetail{" +
                "id=" + id +
                ", telType='" + telType + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", version=" + version +
                ", Contact id=" + getContact().getId() +
                '}';
    }
}

