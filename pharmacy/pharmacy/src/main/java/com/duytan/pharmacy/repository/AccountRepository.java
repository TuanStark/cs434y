package com.duytan.pharmacy.repository;

import com.duytan.pharmacy.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
