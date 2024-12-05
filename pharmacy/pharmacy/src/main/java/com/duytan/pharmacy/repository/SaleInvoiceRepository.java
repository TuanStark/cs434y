package com.duytan.pharmacy.repository;

import com.duytan.pharmacy.entity.SaleInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleInvoiceRepository extends JpaRepository<SaleInvoice,Long> {
    SaleInvoice findByInvoice_Id(Long id);
}
