package com.securefivewave.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.securefivewave.entity.User;

//public interface IUserRepository extends ListCrudRepository<User, Long>, ListPagingAndSortingRepository<User, Long>  {
public interface IUserRepository<T extends User>{	
/*Basic CRUD Operations */
	
	
	
	
	T create(T data);
	Collection<T> list(int page, int pageSize);
	T get(Long id);
	T update(T data);
	Boolean delete(Long id);
	Optional<T> getUserByEmail(String email);
	Optional<T> getUserRoleByUserId(Long id);
}
