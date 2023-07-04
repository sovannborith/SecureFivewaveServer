
package com.securefivewave.service.implementation;


import java.util.List;

import org.springframework.stereotype.Service;

import com.securefivewave.entity.AccountVerification;
import com.securefivewave.repository.IAccountVerficationRepository;
import com.securefivewave.service.IAccountVerficationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountVerificationServiceImpl implements IAccountVerficationService{

private final IAccountVerficationRepository accountVerficationRepository;

	@Override
	public AccountVerification createAccountVerfication(AccountVerification accountVerification) {
		return accountVerficationRepository.save(accountVerification);
	}

	@Override
	public AccountVerification updateAccountVerification(AccountVerification accountVerification) {
		return accountVerficationRepository.save(accountVerification);
	}

	@Override
	public AccountVerification getAccountVerificationById(Long id) {
		return accountVerficationRepository.findById(id).orElseThrow();
	}

	@Override
	public List<AccountVerification> getAccountVerificationByUserId(Long userId) {
		return accountVerficationRepository.getAccountVerficationByUserId(userId);
	}

	@Override
	public AccountVerification getAccountVerificationByUrl(String url){
		return accountVerficationRepository.getAccountVerificationByUrl(url);
	}

	@Override
	public void deleteAccountVerficationById(Long id) {
		accountVerficationRepository.deleteById(id);
	}
	
}
