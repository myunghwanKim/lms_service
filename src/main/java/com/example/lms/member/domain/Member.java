package com.example.lms.member.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member {
	
	@Id
	private String userId;

	private String userName;
	private String phone;
	private String password;

	private LocalDateTime createAt; 
	
	private boolean emailAuthYn;
	private LocalDateTime emailAuthAt;
	private String eamilAuthKey;
}