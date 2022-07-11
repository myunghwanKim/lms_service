package com.example.lms.member.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.example.lms.components.MailComponents;
import com.example.lms.member.domain.Member;
import com.example.lms.member.exception.MemberNotEmailAuthException;
import com.example.lms.member.model.MemberInput;
import com.example.lms.member.repository.MemberRepository;
import com.example.lms.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final MailComponents mailComponents;
	
	/**
	 * 회원 가입
	 */
	@Override
	public boolean register(MemberInput memberInput) {
		Optional<Member> optionalMember = 
				memberRepository.findById(memberInput.getUserId());
		if (optionalMember.isPresent()) {
			return false;
		}
		
		String encPassword = BCrypt.hashpw(memberInput.getPassword(), BCrypt.gensalt());
		
		String uuid = UUID.randomUUID().toString();
		
		Member member = Member.builder()
				.userId(memberInput.getUserId())
				.userName(memberInput.getUserName())
				.phone(memberInput.getPhone())
				.password(encPassword)
				.createAt(LocalDateTime.now())
				.emailAuthYn(false)
				.eamilAuthKey(uuid)
				.build();
		
		memberRepository.save(member);
		
		String email = memberInput.getUserId();
		String subject = "lms 사이트 가입을 축하드립니다.";
		String text = "<p>lms 사이트 가입을 축하드립니다.</p>"
				+ "<p>아래 링크를 클릭하셔서 가입을 완료 하세요.</p>"
				+ "<div><a target='_blank' href='http://localhost:8080/member/email-auth?id="
				+ uuid + "'> 가입 완료 </a></div>";
		mailComponents.sendMail(email, subject, text);
		
		return true;
	}

	@Override
	public boolean emailAuth(String uuid) {
		Optional<Member> optionalMember = memberRepository.findByEamilAuthKey(uuid);
		if (!optionalMember.isPresent()) {
			return false;
		}
		
		Member member = optionalMember.get();
		member.setEmailAuthYn(true);
		member.setEmailAuthAt(LocalDateTime.now());
		memberRepository.save(member);
		
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Member> optionalMemeber = memberRepository.findById(username);
		if (!optionalMemeber.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		
		Member member = optionalMemeber.get();
		
		if (!member.isEmailAuthYn()) {
			throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		
		return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
	}
}
