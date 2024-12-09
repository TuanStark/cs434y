package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.InvoiceRequest;
import com.duytan.pharmacy.dto.response.InvoiceResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface InvoiceService {
    InvoiceResponse createInvoice(InvoiceRequest request);
    InvoiceResponse getInvoiceById(Long id);
    PageResponse<InvoiceResponse> getAll(int page, int size);
}
