package com.duytan.pharmacy.mapper;

import com.duytan.pharmacy.dto.request.InvoiceRequest;
import com.duytan.pharmacy.dto.response.InvoiceResponse;
import com.duytan.pharmacy.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice toInvoice(InvoiceRequest request);
    InvoiceResponse toResponse(Invoice invoice);
    void updateInvoice(@MappingTarget Invoice invoice, InvoiceRequest request);
}
