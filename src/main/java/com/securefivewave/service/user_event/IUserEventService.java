package com.securefivewave.service.user_event;
import java.util.List;

import com.securefivewave.entity.UserEvent;

public interface IUserEventService {
    public UserEvent createUserEvent(UserEvent userEvent);
    public List<UserEvent> getUserEventByUserId(Long userId);
    public UserEvent getUserEventById(Long id);
    public UserEvent update(UserEvent userEvent);
    public void deleteById(Long id);
}
