package com.duytan.pharmacy.service.impl;

import com.duytan.pharmacy.dto.request.AccountRequest;
import com.duytan.pharmacy.dto.response.AccountResponse;
import com.duytan.pharmacy.entity.Account;
import com.duytan.pharmacy.entity.Role;
import com.duytan.pharmacy.entity.WorkShift;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.mapper.AccountMapper;
import com.duytan.pharmacy.repository.AccountRepository;
import com.duytan.pharmacy.repository.RoleRepository;
import com.duytan.pharmacy.repository.WorkShiftRepository;
import com.duytan.pharmacy.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountMapper accountMapper;
    AccountRepository accountRepository;
    RoleRepository roleRepository;
    WorkShiftRepository workShiftRepository;

    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        Account account = accountMapper.toAccount(request);
        Role role = roleRepository.findById(Long.valueOf(2))
                .orElseThrow(() -> new RuntimeException("Not found role"));
        account.setRole(role);
        WorkShift ws = workShiftRepository.findById(request.getIdWorkShift())
                        .orElseThrow(()-> new RuntimeException("Not found WorkShift"));
        account.setWorkShift(ws);
        accountRepository.save(account);

        return this.convertToAccountResponse(account);
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(AccountRequest request, Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Not found Account !"));
        accountMapper.updateAccount(account,request);
        Role role = roleRepository.findById(Long.valueOf(2))
                .orElseThrow(() -> new RuntimeException("Not found role"));
        account.setRole(role);
        WorkShift ws = workShiftRepository.findById(request.getIdWorkShift())
                .orElseThrow(()-> new RuntimeException("Not found WorkShift"));
        account.setWorkShift(ws);
        accountRepository.save(account);

        return this.convertToAccountResponse(account);
    }

    @Override
    @Transactional
    public void deleteAccount(Long idAccount) {
        Account account = accountRepository.findById(idAccount).orElseThrow(()-> new RuntimeException("Not found Account"));
        accountRepository.delete(account);
    }

    @Override
    public PageResponse<AccountResponse> getAllAccount(int page, int size, int limit) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page-1,size,sort);
        Page<Account> pageData = accountRepository.findAll(pageable);

        return PageResponse.<AccountResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(this::convertToAccountResponse).toList())
                .build();
    }

    @Override
    public AccountResponse getAccountById(Long idAccount) {
        Account account = accountRepository.findById(idAccount).orElseThrow(()-> new RuntimeException("Not found Account"));
        return this.convertToAccountResponse(account);
    }

    public AccountResponse convertToAccountResponse(Account account){
        AccountResponse response = accountMapper.toAccountResponse(account);
        response.setRoleName(account.getRole().getName());
        response.setIdWorkShift(account.getWorkShift().getId());
        return response;
    }
}
