package com.securefivewave.auth.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.securefivewave.dto.UserRoleDTO;
import com.securefivewave.entity.User;
import com.securefivewave.service.implementation.RoleServiceImpl;
import com.securefivewave.service.implementation.UserRoleServiceImpl;
import com.securefivewave.service.implementation.UserServiceImpl;

import lombok.Data;

@Data
public class SecureFivewaveUserDetail implements UserDetails {

    private final UserRoleServiceImpl userRoleServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleServiceImpl;

    private String userName;
    private String password;
    private boolean isEnabled;
    private List<GrantedAuthority> authorities;

    public SecureFivewaveUserDetail(User user) {
        userRoleServiceImpl =new UserRoleServiceImpl(null);
        userServiceImpl = new UserServiceImpl(null, null, null);
        roleServiceImpl = new RoleServiceImpl(null);
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.isEnabled = user.getIsEnable();        
        this.authorities = userAuthority(user.getId());
        
    }
    
    private List<GrantedAuthority> userAuthority(Long userId){

        List<UserRoleDTO> userRoles = userRoleServiceImpl.getUserRoleByUserId(userId);
        List<GrantedAuthority> authority =new ArrayList<>();
        for(UserRoleDTO urDto : userRoles)
        {
            authority.add(new SimpleGrantedAuthority(roleServiceImpl.getRoleNameById(urDto.getRoleId())));
        }
        return authority;
    }


    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
    @Override
    public String getUsername() {
        return userName;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }    
}
