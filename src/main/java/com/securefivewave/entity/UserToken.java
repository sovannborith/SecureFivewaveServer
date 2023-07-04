package com.securefivewave.entity;

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
@Table (name ="TBL_USER_TOKEN")
public class UserToken {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "User Id cannot be empty")
	@Column(name = "user_id")
	private Long userId;
	@NotEmpty(message = "Token type cannot be empty")
	@Column(name = "token_type")
	private String tokenType;
	@NotEmpty(message = "Access token cannot be empty")
	@Column(name = "access_token")
	private String accessToken;
	@Column(name = "refresh_token")
	private String refreshToken;
	@Column(name = "is_revoked")
	private boolean isRevoked;
	@Column(name = "is_expired")
	private boolean isExpired;
	
}
