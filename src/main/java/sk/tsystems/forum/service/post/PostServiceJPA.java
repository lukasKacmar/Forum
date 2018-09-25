package sk.tsystems.forum.service.post;


import sk.tsystems.forum.entity.Post;
import sk.tsystems.forum.entity.Topic;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
            return replies-1;
        }
    }
}
