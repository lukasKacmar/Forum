package sk.tsystems.forum.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Topic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "member_id", value = ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "section_id", value = ConstraintMode.NO_CONSTRAINT))
    private Section section;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Post> posts = new ArrayList<Post>();

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private int views;

    @Column(updatable = false, nullable = false)
    private Date creationDate;

    @Column
    private Date lastUpdateDate;

    @Column
    private boolean closed;

    public Topic() {
    }

    @PrePersist
    public void creation(){
        this.creationDate = new Date();
        this.lastUpdateDate = new Date();
    }

    @PreUpdate
    public void update(){
        this.lastUpdateDate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public boolean isClosed(){
        return closed;
    }

    public void setClosed(boolean closed){
        this.closed = closed;
    }

    public List<Post> getPosts(){
        return posts;
    }

    public void setPosts(List<Post> posts){
        this.posts = posts;
    }
}
