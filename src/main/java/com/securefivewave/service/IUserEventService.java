package com.securefivewave.service;
import com.securefivewave.entity.UserEvent;

public interface IUserEventService {
    public UserEvent createUserEvent(UserEvent userEvent);
    public UserEvent getUserEventByUserId(Long userId);
    public UserEvent getUserEventById(Long id);
    public UserEvent update(UserEvent userEvent);
    public void deleteById(Long id);
}
