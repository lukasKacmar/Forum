package sk.tsystems.forum.service.section;

import sk.tsystems.forum.entity.Category;
import sk.tsystems.forum.entity.Section;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class SectionServiceJPA implements SectionService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addSection(Section section) {
        try {
            em.persist(section);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateSection(Section section) {
        try {
            em.merge(section);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Section getSection(long id) {
        try {
            return em
                    .createQuery("SELECT s FROM Section s WHERE s.id= :id", Section.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Section> getSections() {
        try {
            return em
                    .createQuery("SELECT s FROM Section s")
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }


    @Override
    public List<Section> getSections(Category category) {
        try {
            return em
                    .createQuery("SELECT s FROM Section s WHERE s.category = :category")
                    .setParameter("category", category)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void deleteSection(long id) {
        Section s = null;

        try {
            s = em
                    .createQuery("SELECT s FROM Section s WHERE s.id = :id", Section.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }

        if(s != null){
            em.remove(s);
        }
    }

    @Override
    public void addCategory(Category category) {
        try {
            em.persist(category);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Category getCategory(long id) {
        try {
            return em
                    .createQuery("SELECT c FROM Category c WHERE c.id= :id", Category.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Category> getCategories() {
        try {
            return em
                    .createQuery("SELECT c FROM Category c")
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void deleteCategory(long id) {
        Category c = null;

        try {
            c = em
                    .createQuery("SELECT c FROM Category c WHERE c.id = :id", Category.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }

        if(c != null){
            em.remove(c);
        }
    }
}
