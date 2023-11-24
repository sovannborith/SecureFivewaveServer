package com.securefivewave.service.account_verification;

import java.util.List;

import com.securefivewave.entity.AccountVerification;

public interface IAccountVerficationService {

    public AccountVerification createAccountVerfication(AccountVerification accountVerification);

    public AccountVerification updateAccountVerification(AccountVerification accountVerification);

    public AccountVerification getAccountVerificationById(Long id);

    public List<AccountVerification> getAccountVerificationByUserId(Long userId);

    public AccountVerification getAccountVerificationByUrl(String url);

    public void deleteAccountVerficationById(Long id);
    
}
