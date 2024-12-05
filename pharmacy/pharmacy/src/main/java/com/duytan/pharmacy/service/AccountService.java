package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.AccountRequest;
import com.duytan.pharmacy.dto.response.AccountResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    AccountResponse createAccount(AccountRequest request);
    AccountResponse updateAccount(AccountRequest request, Long id);
    void deleteAccount(Long idAccount);
    PageResponse<AccountResponse> getAllAccount(int page, int size, int limit);
    AccountResponse getAccountById(Long idAccount);
}
