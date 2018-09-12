package sk.tsystems.forum.service.member;

import sk.tsystems.forum.entity.Member;

import javax.persistence.EntityManager;
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
        Member m = em
                .createQuery("SELECT m FROM Member m WHERE m.username = :username AND m.password = :password", Member.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
        return m;
    }

    @Override
    public Member getMember(long id) {
        Member m = em
                .createQuery("SELECT m FROM Member m WHERE m.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult();
        return m;
    }

    @Override
    public List<Member> getMembers() {
        List<Member> members = em
                .createQuery("SELECT m FROM Member m")
                .getResultList();
        return members;
    }
}
