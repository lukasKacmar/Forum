package sk.tsystems.forum.service.topic;

import sk.tsystems.forum.entity.Section;
import sk.tsystems.forum.entity.Topic;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class TopicServiceJPA implements TopicService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addTopic(Topic topic) {
        try {
            em.persist(topic);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateTopic(Topic topic) {
        em.merge(topic);
    }

    @Override
    public Topic getTopic(Long id) {
        try {
            return em
                    .createQuery("SELECT t FROM Topic t WHERE t.id = :id", Topic.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Topic> getTopics(Section section) {
        try {
            return em
                    .createQuery("SELECT t FROM Topic t WHERE t.section = :section")
                    .setParameter("section", section)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Topic> findTopics(String searchText) {
        return em
                .createQuery("SELECT t FROM Topic t WHERE t.title LIKE :searchText")
                .setParameter("searchText", "%" + searchText + "%")
                .getResultList();
    }

    @Override
    public long getCount(Section section) {
        try {
            return (long) em
                    .createQuery("SELECT COUNT(*) FROM Topic t WHERE t.section = :section")
                    .setParameter("section", section)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return 0;
        }
    }

    @Override
    public void deleteTopic(long id) {
        Topic t = null;
        t = em
                .createQuery("SELECT t FROM Topic t WHERE t.id = :id", Topic.class)
                .setParameter("id", id)
                .getSingleResult();
        if(t != null) {
            em.remove(t);
        }
    }
}
