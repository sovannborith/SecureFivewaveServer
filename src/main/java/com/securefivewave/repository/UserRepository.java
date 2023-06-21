package com.securefivewave.repository;

import java.util.Collection;

import com.securefivewave.domain.User;

public interface UserRepository<T extends User> {
	/*Basic CRUD Operations */
	T create(T data);
	Collection<T> list(int page, int pageSize);
	T get(Long id);
	T update(T data);
	Boolean delete(Long id);
	Collection<T> getUserByEmail(String email);
}
