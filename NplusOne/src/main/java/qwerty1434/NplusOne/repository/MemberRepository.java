package qwerty1434.NplusOne.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import qwerty1434.NplusOne.domain.Member;


import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }
    public List<Member> findAll(){
//        String q = "select m from Member m";
        String q = "select m from Member m inner join m.team t"; // 위와 동일하게 N+1문제 발생
        return em.createQuery(q,Member.class).getResultList();
    }

    public List<Member> findAllWithFetch(){
        String q = "select m from Member m join fetch m.team t";
        return em.createQuery(q,Member.class).getResultList();
    }

}
