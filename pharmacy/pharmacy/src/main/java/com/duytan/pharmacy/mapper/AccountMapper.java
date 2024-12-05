package com.duytan.pharmacy.mapper;

import com.duytan.pharmacy.dto.request.AccountRequest;
import com.duytan.pharmacy.dto.response.AccountResponse;
import com.duytan.pharmacy.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "Spring")
public interface AccountMapper {
    Account toAccount(AccountRequest request);
    AccountResponse toAccountResponse(Account account);
    void updateAccount(@MappingTarget Account account, AccountRequest request);
}
