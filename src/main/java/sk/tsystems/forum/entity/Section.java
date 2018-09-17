package sk.tsystems.forum.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Section implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @OneToMany(mappedBy = "section", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Topic> topics = new ArrayList<Topic>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "member_id", value = ConstraintMode.CONSTRAINT))
    private Member member;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "category_id", value = ConstraintMode.NO_CONSTRAINT))
    private Category category;

    public Section() {
    }

    public Section(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Topic> getTopics(){
        return topics;
    }

    public void setTopics(List<Topic> topics){
        this.topics = topics;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
