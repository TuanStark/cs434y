package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.DisposalRequest;
import com.duytan.pharmacy.dto.request.SaleRequest;
import com.duytan.pharmacy.dto.response.DisposalResponse;
import com.duytan.pharmacy.dto.response.SaleResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface DisposalInvoiceService {
    DisposalResponse createDisposalInvoice(DisposalRequest request, Long idInvoice);
    DisposalResponse getDisposalInvoice(Long idInvoice);
}
