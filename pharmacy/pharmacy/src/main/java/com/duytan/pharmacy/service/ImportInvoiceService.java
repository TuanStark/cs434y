package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.ImportRequest;
import com.duytan.pharmacy.dto.request.SaleRequest;
import com.duytan.pharmacy.dto.response.ImportResponse;
import com.duytan.pharmacy.dto.response.SaleResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface ImportInvoiceService {
    ImportResponse createImportInvoice(ImportRequest request, Long idInvoice);
    ImportResponse getImportInvoice(Long idInvoice);
}
