package sk.tsystems.forum.service.post;


import sk.tsystems.forum.entity.Like;
import sk.tsystems.forum.entity.Member;
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
        long replies = 0;

        try {
            replies = ((long) em
                    .createQuery("SELECT COUNT(*) FROM Post p WHERE p.topic = :topic")
                    .setParameter("topic", topic)
                    .getSingleResult());
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }

        if (replies == 0) {
            return 0;
        } else {
            return replies - 1;
        }
    }

    @Override
    public void updatePost(Post post) {
        try {
            em.merge(post);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deletePost(long id) {
        Post p = null;

        try {
            p = em
                    .createQuery("SELECT p FROM Post p WHERE p.id = :id", Post.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }

        if (p != null) {
            em.remove(p);
        }
    }

    @Override
    public void likePost(Like like) {
        try {
            em.persist(like);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void unlikePost(Post post, Member member) {
        Like l = null;

        try {
            l = em
                    .createQuery("SELECT l FROM Like l WHERE l.post = :post AND l.member = :member", Like.class)
                    .setParameter("post", post)
                    .setParameter("member", member)
                    .getSingleResult();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }

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
        try {
            return ((long) em
                    .createQuery("SELECT COUNT(*) FROM Like l WHERE l.post = :post")
                    .setParameter("post", post)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return 0;
        }
    }

    @Override
    public List<Post> findPosts(String searchText) {
        try {
            return em
                    .createQuery("SELECT p FROM Post p WHERE p.content LIKE :searchText")
                    .setParameter("searchText", "%" + searchText + "%")
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
