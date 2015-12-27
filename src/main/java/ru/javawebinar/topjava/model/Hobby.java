package ru.javawebinar.topjava.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HOBBY")
public class Hobby implements Serializable {

    @Id
    @Column(name = "HOBBY_ID")
    private String hobbyId;

    @ManyToMany
    @JoinTable(name = "CONTACT_HOBBY_DETAIL",
            joinColumns = @JoinColumn(name = "HOBBY_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTACT_ID"))
    private Set<Contact> contacts = new HashSet<>();

    public Hobby() {}

    public String getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(String hobbyId) {
        this.hobbyId = hobbyId;
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "hobbyId='" + hobbyId + '\'' +
                '}';
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }
}
