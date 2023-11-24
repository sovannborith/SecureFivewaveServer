
package com.securefivewave.service.user_event;
import java.util.List;

import org.springframework.stereotype.Service;

import com.securefivewave.entity.UserEvent;
import com.securefivewave.repository.IUserEventRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements IUserEventService{

	private final IUserEventRepository userEventRepository;
	
	@Override
	public UserEvent createUserEvent(UserEvent userEvent) {
		return userEventRepository.save(userEvent);
	}
	@Override
	public List<UserEvent> getUserEventByUserId(Long userId) {
		return userEventRepository.getUserEventByUserId(userId);
	}
	@Override
	public UserEvent getUserEventById(Long id) {
		return userEventRepository.findById(id).get();
	}
	@Override
	public UserEvent update(UserEvent userEvent) {
		return userEventRepository.save(userEvent);
	}
	@Override
	public void deleteById(Long id) {
		userEventRepository.deleteById(id);
	}
}
