package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.SaleRequest;
import com.duytan.pharmacy.dto.response.SaleResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface SaleInvoiceService {
    SaleResponse createSaleInvoice(SaleRequest request,Long idInvoice);
    SaleResponse getSaleInvoice(Long idInvoice);
    PageResponse<SaleResponse> getAll(int page, int size);
}
