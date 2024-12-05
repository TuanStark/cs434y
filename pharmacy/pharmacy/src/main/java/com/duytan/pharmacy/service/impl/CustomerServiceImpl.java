package com.duytan.pharmacy.service.impl;

import com.duytan.pharmacy.dto.request.CustomerRequest;
import com.duytan.pharmacy.dto.response.CustomerResponse;
import com.duytan.pharmacy.dto.response.WorkShiftResponse;
import com.duytan.pharmacy.entity.Customer;
import com.duytan.pharmacy.entity.WorkShift;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.mapper.CustomerMapper;
import com.duytan.pharmacy.repository.CustomerRepository;
import com.duytan.pharmacy.service.CustomerService;
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

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = customerMapper.toCustomer(request);
        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public Customer createCustomerForSale(String phone, String fullName) {
        Customer customer = Customer.builder()
                .fullName(fullName)
                .phone(phone)
                .build();
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("Not found Customer"));
        customerMapper.updateCustomer(customer,request);
        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("Not found Customer"));
        customerRepository.delete(customer);
    }

    @Override
    public CustomerResponse getCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("Not found Customer"));
        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    public PageResponse<CustomerResponse> getAllCustomer(int page, int size) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Customer> pageData = customerRepository.findAll(pageable);

        return PageResponse.<CustomerResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream()
                        .map(customer -> customerMapper.toCustomerResponse(customer))
                        .collect(Collectors.toList()))
                .build();
    }
}
