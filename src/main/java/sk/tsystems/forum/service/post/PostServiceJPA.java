package sk.tsystems.forum.service.post;


import sk.tsystems.forum.entity.Post;
import sk.tsystems.forum.entity.Topic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class PostServiceJPA implements PostService {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void addPost(Post post) {
        em.persist(post);
    }

    @Override
    public void updatePost(long id, String content) {
        Post post = em.find(Post.class, id);
        post.setContent(content);
    }

    @Override
    public Post getPost(long id) {
        Post p = em
                .createQuery("SELECT p FROM Post p WHERE p.id = :id", Post.class)
                .setParameter("id", id)
                .getSingleResult();
        return p;
    }

    @Override
    public List<Post> getPosts(Topic topic) {
        List<Post> posts = em
                .createQuery("SELECT p FROM Post p WHERE p.topic = :topic ORDER BY creation_date")
                .setParameter("topic", topic)
                .getResultList();
        return posts;
    }

    @Override
    public long getCount(Topic topic) {

        long replies;
        replies = ((long) em
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
