package com.securefivewave.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import com.securefivewave.entity.UserEvent;

public interface IUserEventRepository extends ListCrudRepository<UserEvent, Long> {
	
	@Query(value = "SELECT U.* from TBL_USER_EVENT U WHERE U.USER_ID=:userId", nativeQuery=true)
	public List<UserEvent> getUserEventByUserId(Long userId);
}
