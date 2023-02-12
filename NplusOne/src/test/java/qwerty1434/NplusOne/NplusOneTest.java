package qwerty1434.NplusOne;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import qwerty1434.NplusOne.domain.Member;
import qwerty1434.NplusOne.domain.Team;
import qwerty1434.NplusOne.repository.MRepository;
import qwerty1434.NplusOne.repository.MemberRepository;
import qwerty1434.NplusOne.repository.TRepository;
import qwerty1434.NplusOne.repository.TeamRepository;

import javax.persistence.EntityManager;

@Slf4j
@SpringBootTest
public class NplusOneTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void setData(){
        log.info("데이터 생성 시작");
        Team t1 = new Team("t1");
        Team t2 = new Team("t2");
        Team t3 = new Team("t3");
        Team t4 = new Team("t4");
        Team t5 = new Team("t5");
        Team t6 = new Team("t6");
        Team t7 = new Team("t7");
        Team t8 = new Team("t8");
        Team t9 = new Team("t9");
        Team t10 = new Team("t10");
        teamRepository.save(t1);
        teamRepository.save(t2);
        teamRepository.save(t3);
        teamRepository.save(t4);
        teamRepository.save(t5);
        teamRepository.save(t6);
        teamRepository.save(t7);
        teamRepository.save(t8);
        teamRepository.save(t9);
        teamRepository.save(t10);

        Member m1 = new Member("m1",t1);
        Member m2 = new Member("m2",t2);
        Member m3 = new Member("m3",t3);
        Member m4 = new Member("m4",t4);
        Member m5 = new Member("m5",t5);
        Member m6 = new Member("m6",t6);
        Member m7 = new Member("m7",t7);
        Member m8 = new Member("m8",t8);
        Member m9 = new Member("m9",t9);
        Member m10 = new Member("m10",t10);
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);
        memberRepository.save(m5);
        memberRepository.save(m6);
        memberRepository.save(m7);
        memberRepository.save(m8);
        memberRepository.save(m9);
        memberRepository.save(m10);
        log.info("데이터 생성 끝");
        log.info("===========================================");
    }

    @Test
    @Transactional
    public void NplusOne(){
        em.flush();
        em.clear(); // 영속성 컨텍스트의 초기화. 영속성 컨텍스트가 아닌 DB에서 값을 가져오기 위함
        log.info("멤버 데이터 속 팀 호출하기");
        memberRepository.findAll().stream().forEach(m -> m.getTeam().getTeamName());
        // 스트림이 낯선 사람을 위한 코드. 위 코드는 아래 코드와 동일합니다.
        // for (Member member : memberRepository.findAll()) {
        //     member.getTeam().getTeamName();
        // }
    }
    @Test
    @Transactional
    public void SolveNplusOne(){
        em.flush();
        em.clear(); // 영속성 컨텍스트의 초기화. 영속성 컨텍스트가 아닌 DB에서 값을 가져오기 위함
        log.info("멤버 데이터 속 팀 호출하기");
        memberRepository.findAllWithFetch().stream().forEach(m -> m.getTeam().getTeamName());
    }
}
