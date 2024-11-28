package com.duytan.pharmacy.repository;

import com.duytan.pharmacy.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
