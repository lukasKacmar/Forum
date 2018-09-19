package sk.tsystems.forum.service.member;

import sk.tsystems.forum.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class MemberServiceJPA implements MemberService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void register(Member member) {
        em.persist(member);
    }

    @Override
    public Member login(String username, String password) {
        try {
            return em
                    .createQuery("SELECT m FROM Member m WHERE m.username = :username AND m.password = :password", Member.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Member getMember(long id) {
        try {
            return em
                    .createQuery("SELECT m FROM Member m WHERE m.id = :id", Member.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Member> getMembers() {
        List<Member> members = em
                .createQuery("SELECT m FROM Member m WHERE (rank='BANNED') OR (rank='GENERAL') ORDER BY username")
                .getResultList();
        return members;
    }

    @Override
    public void deleteMember(long id) {
        Member m = null;
        m = em
                .createQuery("SELECT m FROM Member m WHERE m.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult();
        if(m != null) {
            em.remove(m);
        }
    }

    @Override
    public boolean emailExists(String email) {
        try {
            Member m = em
                    .createQuery("SELECT m FROM Member m WHERE m.email = :email", Member.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return m != null;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public boolean usernameExists(String username) {
        try {
            Member m = em
                    .createQuery("SELECT m FROM Member m WHERE m.username = :username", Member.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return m != null;
        } catch (NoResultException ex) {
            return false;
        }
    }
}
