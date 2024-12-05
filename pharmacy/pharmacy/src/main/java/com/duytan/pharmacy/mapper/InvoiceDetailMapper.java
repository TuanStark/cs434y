package com.duytan.pharmacy.mapper;

import com.duytan.pharmacy.dto.request.InvoiceDetailRequest;
import com.duytan.pharmacy.dto.response.InvoiceDetailResponse;
import com.duytan.pharmacy.entity.InvoiceDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceDetailMapper {
    InvoiceDetail toInvoiceDetail(InvoiceDetailRequest request);
    InvoiceDetailResponse toInvoiceDetailResponse(InvoiceDetail invoiceDetail);
}
