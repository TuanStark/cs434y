package com.duytan.pharmacy.controller;

import com.duytan.pharmacy.dto.request.CustomerRequest;
import com.duytan.pharmacy.dto.request.WorkShiftRequest;
import com.duytan.pharmacy.dto.response.CustomerResponse;
import com.duytan.pharmacy.dto.response.WorkShiftResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.helper.response.ResponseObject;
import com.duytan.pharmacy.service.CustomerService;
import com.duytan.pharmacy.service.WorkShiftService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CustomerController {
    CustomerService customerService;

    @PostMapping("/create")
    public ResponseObject<CustomerResponse> create(@ModelAttribute CustomerRequest request){
        CustomerResponse data = customerService.createCustomer(request);
        return ResponseObject.<CustomerResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
                .build();
    }
    @GetMapping("/all")
    public ResponseObject<PageResponse> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false) Integer size){
        if (size == null) {
            size = Integer.MAX_VALUE;
        }
        return ResponseObject.<PageResponse>builder()
                .code(200)
                .message("SUCCESS")
                .data(customerService.getAllCustomer(page,size))
                .build();
    }
    @PutMapping("/update/{id}")
    public ResponseObject<CustomerResponse> update(@PathVariable Long id,@ModelAttribute CustomerRequest request){
        CustomerResponse data = customerService.updateCustomer(id, request);
        return ResponseObject.<CustomerResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject<Void> update(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return ResponseObject.<Void>builder()
                .code(200)
                .message("SUCCESS")
                .build();
    }

    @GetMapping("/getById/{id}")
    public ResponseObject<CustomerResponse> getById(@PathVariable Long id){
        CustomerResponse data = customerService.getCustomer(id);
        return ResponseObject.<CustomerResponse>builder()
                .code(200)
                .message("SUCCESS")
                .data(data)
                .build();
    }

}
