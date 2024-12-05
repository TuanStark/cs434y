package com.duytan.pharmacy.mapper;

import com.duytan.pharmacy.dto.request.SaleRequest;
import com.duytan.pharmacy.dto.response.SaleResponse;
import com.duytan.pharmacy.entity.SaleInvoice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SaleInvoiceMapper {
    SaleInvoice toSaleInvoice(SaleRequest request);
    SaleResponse toSaleResponse(SaleInvoice saleInvoice);
    void updateSaleInvoice(@MappingTarget SaleInvoice saleInvoice, SaleRequest request);
}
