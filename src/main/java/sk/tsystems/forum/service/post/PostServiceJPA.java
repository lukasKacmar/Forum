package sk.tsystems.forum.service.post;


import sk.tsystems.forum.entity.Like;
import sk.tsystems.forum.entity.Member;
import sk.tsystems.forum.entity.Post;
import sk.tsystems.forum.entity.Topic;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public class PostServiceJPA implements PostService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addPost(Post post) {
        try {
            em.persist(post);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updatePost(long id, String content) {
        Post post = em.find(Post.class, id);
        post.setContent(content);
    }

    @Override
    public Post getPost(long id) {
        try {
            return em
                    .createQuery("SELECT p FROM Post p WHERE p.id = :id", Post.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Post> getPosts(Topic topic) {
        try {
            return em
                    .createQuery("SELECT p FROM Post p WHERE p.topic = :topic ORDER BY creation_date")
                    .setParameter("topic", topic)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public long getCount(Topic topic) {
        long replies = ((long) em
                .createQuery("SELECT COUNT(*) FROM Post p WHERE p.topic = :topic")
                .setParameter("topic", topic)
                .getSingleResult());
        if (replies == 0) {
            return 0;
        } else {
            return replies - 1;
        }
    }

    @Override
    public void updatePost(Post post) {
        em.merge(post);
    }

    @Override
    public void deletePost(long id) {
        Post p = null;
        p = em
                .createQuery("SELECT p FROM Post p WHERE p.id = :id", Post.class)
                .setParameter("id", id)
                .getSingleResult();
        if (p != null) {
            em.remove(p);
        }
    }

    @Override
    public void likePost(Like like) {
        em.persist(like);
    }

    @Override
    public void unlikePost(Post post, Member member) {
        Like l = em
                .createQuery("SELECT l FROM Like l WHERE l.post = :post AND l.member = :member", Like.class)
                .setParameter("post", post)
                .setParameter("member", member)
                .getSingleResult();
        if (l != null) {
            em.remove(l);
        }
    }

    @Override
    public Like getLike(Post post, Member member) {
        try {
            return em
                    .createQuery("SELECT l FROM Like l WHERE l.post = :post AND l.member = :member", Like.class)
                    .setParameter("post", post)
                    .setParameter("member", member)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public long getLikesCount(Post post) {
        return ((long) em
                .createQuery("SELECT COUNT(*) FROM Like l WHERE l.post = :post")
                .setParameter("post", post)
                .getSingleResult());
    }

    @Override
    public List<Post> findPosts(String searchText) {
        return em
                .createQuery("SELECT p FROM Post p WHERE p.content LIKE :searchText")
                .setParameter("searchText", "%" + searchText + "%")
                .getResultList();
    }

    @Override
    public Date getLastPostDate(Topic topic){
//        List<Post> posts;
//        try {
//            posts = em
//                    .createQuery("SELECT p FROM Post p WHERE p.topic = :topic ORDER BY creation_date DESC")
//                    .setParameter("topic", topic)
//                    .getResultList();
//        } catch (NoResultException ex) {
//            return null;
//        }
//        return posts.get(0).getCreationDate();
        try {
            return (Date)em
                    .createQuery("SELECT MAX(p.creationDate) FROM Post p WHERE p.topic = :topic")
                    .setParameter("topic", topic)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
