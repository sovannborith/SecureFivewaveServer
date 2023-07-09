
package com.securefivewave.service.implementation;
import org.springframework.stereotype.Service;

import com.securefivewave.entity.UserEvent;
import com.securefivewave.service.IUserEventService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements IUserEventService{
	
	@Override
	public UserEvent createUserEvent(UserEvent userEvent) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createUserEvent'");
	}
	@Override
	public UserEvent getUserEventByUserId(Long userId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getUserEventByUserId'");
	}
	@Override
	public UserEvent getUserEventById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getUserEventById'");
	}
	@Override
	public UserEvent update(UserEvent userEvent) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}
	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
	}
	
}
