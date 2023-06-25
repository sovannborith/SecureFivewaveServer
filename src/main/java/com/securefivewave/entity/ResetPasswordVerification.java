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
@Table (name ="TBL_RESET_PASSWORD_VERIFICATION")
public class ResetPasswordVerification {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "User ID cannot be empty")
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "url")
	private String url;
	@Column(name = "expiration_date")
	private LocalDateTime expirationDate;
}
