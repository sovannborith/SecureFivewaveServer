package com.securefivewave.entity;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_DEFAULT)
@Data
@Entity
@Table (name ="TBL_USER_OTP")
public class UserOtp {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "User ID cannot be empty")
	@Column(name = "user_id")
	private Long userId;
	@NotEmpty(message = "User Otp cannot be empty")
	@Column(name = "user_otp")
	private String userOtp;
	@Column(name = "created_at")
	private LocalDateTime createdAt;	
	@Column(name = "otp_expired_at")
	private LocalDateTime otpExpiredAt;
}
