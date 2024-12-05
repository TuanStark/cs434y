package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.InvoiceDetailRequest;
import com.duytan.pharmacy.dto.response.InvoiceDetailResponse;
import org.springframework.stereotype.Service;

@Service
public interface InvoiceDetailService {
    InvoiceDetailResponse createInvoiceDetail(InvoiceDetailRequest request, Long idInvoice);
    InvoiceDetailResponse getByIdInvoice(Long idInvoice);
}
