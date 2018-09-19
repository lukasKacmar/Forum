package sk.tsystems.forum.service.section;

import sk.tsystems.forum.entity.Category;
import sk.tsystems.forum.entity.Section;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class SectionServiceJPA implements SectionService {


    @PersistenceContext
    private EntityManager em;

    @Override
    public void addSection(Section section) {
        em.persist(section);
    }

    @Override
    public Section getSection(long id) {
        Section s = em
                .createQuery("SELECT s FROM Section s WHERE s.id= :id", Section.class)
                .setParameter("id", id)
                .getSingleResult();
        return s;
    }

    @Override
    public List<Section> getSections() {
        List<Section> sections = em
                .createQuery("SELECT s FROM Section s")
                .getResultList();
        return sections;
    }


    @Override
    public List<Section> getSections(Category category) {
        List<Section> sections = em
                .createQuery("SELECT s FROM Section s WHERE s.category = :category")
                .setParameter("category", category)
                .getResultList();
        return sections;
    }

    @Override
    public void deleteSection(long id) {
        Section s = null;
        s = em
                .createQuery("SELECT s FROM Section s WHERE s.id = :id", Section.class)
                .setParameter("id", id)
                .getSingleResult();
        if(s != null){
            em.remove(s);
        }
    }

    @Override
    public void addCategory(Category category) {
        em.persist(category);
    }

    @Override
    public Category getCategory(long id) {
        Category c = em
                .createQuery("SELECT c FROM Category c WHERE c.id= :id", Category.class)
                .setParameter("id", id)
                .getSingleResult();
        return c;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = em
                .createQuery("SELECT c FROM Category c")
                .getResultList();
        return categories;
    }
}
