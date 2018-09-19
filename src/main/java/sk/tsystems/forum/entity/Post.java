package sk.tsystems.forum.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "topic_id", value = ConstraintMode.NO_CONSTRAINT))
    private Topic topic;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "member_id", value = ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(updatable = false, nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private Date lastUpdateDate;

    public Post() {
    }

    @PrePersist
    public void creation() {
        this.creationDate = new Date();
        this.lastUpdateDate = new Date();
    }

    @PreUpdate
    public void update() {
        this.lastUpdateDate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
