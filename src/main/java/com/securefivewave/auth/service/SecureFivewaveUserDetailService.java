package com.securefivewave.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.securefivewave.dto.dtomapper.UserDTOMapper;
import com.securefivewave.entity.User;
import com.securefivewave.service.implementation.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SecureFivewaveUserDetailService implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = UserDTOMapper.toUser(userServiceImpl.getUserByEmail(userName));
        SecureFivewaveUserDetail userDetails = new SecureFivewaveUserDetail(user);

        return userDetails;
    }
    
}
