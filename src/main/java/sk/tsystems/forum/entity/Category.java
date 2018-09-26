package sk.tsystems.forum.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Category implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 60)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
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

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
