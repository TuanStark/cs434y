package com.duytan.pharmacy.repository;

import com.duytan.pharmacy.entity.InvoiceDetail;
import com.duytan.pharmacy.entity.SaleInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail,Long> {
    List<InvoiceDetail> findByInvoice_Id(Long id);
}
