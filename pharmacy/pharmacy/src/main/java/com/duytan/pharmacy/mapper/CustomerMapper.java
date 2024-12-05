package com.duytan.pharmacy.mapper;

import com.duytan.pharmacy.dto.request.CustomerRequest;
import com.duytan.pharmacy.dto.response.CustomerResponse;
import com.duytan.pharmacy.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerRequest request);
    CustomerResponse toCustomerResponse(Customer customer);
    void updateCustomer(@MappingTarget Customer customer, CustomerRequest request);
}
