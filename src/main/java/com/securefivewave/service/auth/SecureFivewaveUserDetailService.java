package com.securefivewave.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.securefivewave.entity.User;
import com.securefivewave.repository.IUserRepository;
import com.securefivewave.service.role.RoleServiceImpl;
import com.securefivewave.service.user_role.UserRoleServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SecureFivewaveUserDetailService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final UserRoleServiceImpl userRoleServiceImpl;
    private final RoleServiceImpl roleServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user =  userRepository.getUserByEmail(userName);//UserDTOMapper.toUser(userServiceImpl.getUserByEmail(userName));
        SecureFivewaveUserDetail userDetails = new SecureFivewaveUserDetail(user,userRoleServiceImpl,roleServiceImpl);

        return userDetails;
    }
    
}
