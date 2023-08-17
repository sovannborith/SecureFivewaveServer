package com.securefivewave.dto.usertoken;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author User
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenResponse {
	private String userName;
	private Date expiryDate;
}
