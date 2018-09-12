package sk.tsystems.forum.service.section;

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
}
