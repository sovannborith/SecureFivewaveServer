package com.securefivewave.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuppressWarnings("serial")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_DEFAULT)
@Data
@Entity
@Table (name ="TBL_USER")
public class User implements Serializable, UserDetails  {
		
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "First name cannot be empty")
	@Column(name = "first_name")
	private String firstName;
	@NotEmpty(message = "Last name cannot be empty")
	@Column(name = "last_name")
	private String lastName;
	@NotEmpty(message = "Email cannot be empty")
	@Column(name = "email_addr")
	@Email(message = "Invalid email! Please enter a valid email address")
	private String email;
	@NotEmpty(message = "Password cannot be empty")
	@Column(name = "user_password")
	private String password;
	@Column(name = "mob_phone")
	private String phone;
	@Column(name = "user_bio")
	private String bio;
	@Column(name = "img_url")
	private String imgUrl;
	@Column(name = "is_enable")
	private Boolean isEnable;
	@Column(name = "is_locked")
	private Boolean isLocked;
	@Column(name = "is_mfa")
	private Boolean isMfa;
	@Column(name = "create_at")
	private LocalDateTime createdAt;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
