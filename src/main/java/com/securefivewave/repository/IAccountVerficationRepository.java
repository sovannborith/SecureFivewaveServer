package com.securefivewave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.securefivewave.entity.AccountVerification;
public interface IAccountVerficationRepository extends ListCrudRepository<AccountVerification, Long> {
	

	@Query(value = "SELECT U.* from TBL_ACCOUNT_VERFICATION U WHERE USER_ID=:userId", nativeQuery = true)
	List<AccountVerification> getAccountVerficationByUserId(@Param("userId") Long userId);
	
	@Query(value = "SELECT U.* from TBL_ACCOUNT_VERFICATION U WHERE U.URL=:url", nativeQuery=true)
	AccountVerification getAccountVerificationByUrl(@Param("url") String url);

}
