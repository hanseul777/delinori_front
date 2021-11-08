package com.noriteo.delinori_front.service;

import com.noriteo.delinori_front.dto.MemberDTO;
import com.noriteo.delinori_front.entity.Member;
import com.noriteo.delinori_front.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("==========loadUserByUsername==========" + username);

        Optional<Member> optionalMember = memberRepository.getMemberEager(username);

        Member member = optionalMember.orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));

        log.info("Member : " + member);

        MemberDTO memberDTO = MemberDTO.builder()
                .mid(member.getMid())
                .mpw(member.getMpw())
                .mname(member.getMname())
                .roles(member.getRoleSet().stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toSet()))
                .build();

        log.info("===========memberDTO============");
        log.info(memberDTO);

        return memberDTO;
    }
}
