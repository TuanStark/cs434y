package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.CustomerRequest;
import com.duytan.pharmacy.dto.response.CustomerResponse;
import com.duytan.pharmacy.entity.Customer;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);
    Customer createCustomerForSale(String phone, String fullName);
    CustomerResponse updateCustomer(Long id,CustomerRequest request);
    void deleteCustomer(Long id);
    CustomerResponse getCustomer(Long id);
    PageResponse<CustomerResponse> getAllCustomer(int page, int size);
}
