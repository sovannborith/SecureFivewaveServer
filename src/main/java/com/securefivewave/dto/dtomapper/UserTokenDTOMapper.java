package com.securefivewave.dto.dtomapper;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.securefivewave.dto.UserTokenDTO;
import com.securefivewave.entity.UserToken;

@Component
public class UserTokenDTOMapper {
	public static UserTokenDTO fromUserToken(UserToken userToken) {
		UserTokenDTO userTokenDto = new UserTokenDTO();
		BeanUtils.copyProperties(userToken, userTokenDto);
		
		return userTokenDto;
	}

	public static UserTokenTDO fromUserToken(Optional<UserToken> userToken) {
		if(userToken.isPresent())
		{
			return fromUserToken(userToken);
		}
		else
		{
			return null;
		}
	}
	
	public static UserToken toUserToken(UserTokenDTO userTokenDTO) {
		UserToken userToken = new UserToken();
		BeanUtils.copyProperties(userTokenDTO,userToken);
		
		return userToken;
	}

	public static UserToken toUserToken(Optional<UserTokenDTO> userTokenDTO) {
		if(userTokenDTO.isPresent())
		{
			return toUserToken(userTokenDTO);
		}
		else
		{
			return null;
		}
	}
}
