package sk.tsystems.forum.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "_like")
public class Like implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "member_id", value = ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "post_id", value = ConstraintMode.NO_CONSTRAINT))
    private Post post;

    public Like() {
    }

    public Like(Member member, Post post) {
        this.member = member;
        this.post = post;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
