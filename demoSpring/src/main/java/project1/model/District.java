package project1.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "district_person", joinColumns = { @JoinColumn(name = "district_id") }, inverseJoinColumns = {
            @JoinColumn(name = "person_id") })
    Set<Person> people = new HashSet<>();

    public Set<Person> getPeople() {
        return people;
    }

    public void addAPerson(Person person){
        this.people.add(person);
    }

    public void removeAPerson(Person person){
        this.people.remove(person);
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
