package com.noriteo.delinori_front.repository;

import com.noriteo.delinori_front.entity.Member;
import com.noriteo.delinori_front.entity.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1,100).forEach(i ->{
            Set<MemberRole> roleSet = new HashSet<>();
            roleSet.add(MemberRole.NORI);

            if (i >= 50){
                roleSet.add(MemberRole.DELI);
            }
            if (i >= 80) {
                roleSet.add(MemberRole.ADMIN);
            }

            Member member = Member.builder()
                    .mid("user " + i)
                    .mpw("1111")
                    .mname("사용자 " + i)
                    .roleSet(roleSet)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void updateMembers(){
        List<Member> memberList = memberRepository.findAll();

        memberList.forEach(member -> {
            member.changePassword(passwordEncoder.encode("1111"));

            memberRepository.save(member);
        });
    }
}
