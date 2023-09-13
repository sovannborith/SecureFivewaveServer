package com.securefivewave.service;

import java.util.List;
import com.securefivewave.entity.UserToken;

public interface IUserTokenService {
    public UserToken createUserToken(UserToken userToken);
    public UserToken getUserTokenByUserId(Long userId);
    public UserToken getUserTokenById(Long id);
    public UserToken getUserTokenByToken(String token);
    public UserToken update(UserToken userToken);
    public void deleteById(Long id);
    public List<UserToken> getAllValidUserTokenByUserId(Long userId);
    public UserToken getUserTokenByRefreshToken(String refreshToken);
}
