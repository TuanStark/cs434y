package com.duytan.pharmacy.controller;

import com.duytan.pharmacy.dto.request.AccountRequest;
import com.duytan.pharmacy.dto.response.AccountResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.helper.response.ResponseObject;
import com.duytan.pharmacy.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AccountController {
    AccountService accountService;

    @PostMapping("/create")
    public ResponseObject<AccountResponse> create(@RequestBody AccountRequest request){
        AccountResponse data = accountService.createAccount(request);
        return ResponseObject.<AccountResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
                .build();
    }
    @GetMapping("/all")
    public ResponseObject<PageResponse> getUser(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false) Integer size){
        if (size == null) {
            size = Integer.MAX_VALUE;
        }
        return ResponseObject.<PageResponse>builder()
                .code(200)
                .message("SUCCESS")
                .data(accountService.getAllAccount(page,size,size))
                .build();
    }
    @PutMapping("/update/{idAccount}")
    public ResponseObject<AccountResponse> update(@PathVariable Long idAccount,@RequestBody AccountRequest request){
        AccountResponse data = accountService.updateAccount(request,idAccount);
        return ResponseObject.<AccountResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
                .build();
    }

    @DeleteMapping("/delete/{idAccount}")
    public ResponseObject<Void> update(@PathVariable Long idAccount){
        accountService.deleteAccount(idAccount);
        return ResponseObject.<Void>builder()
                .code(200)
                .message("SUCCESS")
                .build();
    }

    @GetMapping("/get/{idAccount}")
    public ResponseObject<AccountResponse> getById(@PathVariable Long idAccount){
        AccountResponse response = accountService.getAccountById(idAccount);
        return ResponseObject.<AccountResponse>builder()
                .code(200)
                .data(response)
                .message("SUCCESS")
                .build();
    }

}
