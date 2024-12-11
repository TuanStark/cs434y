package com.duytan.pharmacy.repository;

import com.duytan.pharmacy.entity.ImportVoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportInvoiceRepository extends JpaRepository<ImportVoice,Long> {
    ImportVoice findByInvoice_Id(Long id);
}
