package com.duytan.pharmacy.repository;

import com.duytan.pharmacy.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
