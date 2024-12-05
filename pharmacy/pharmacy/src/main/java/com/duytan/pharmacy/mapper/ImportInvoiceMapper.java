package com.duytan.pharmacy.mapper;

import com.duytan.pharmacy.dto.request.ImportRequest;
import com.duytan.pharmacy.dto.request.SaleRequest;
import com.duytan.pharmacy.dto.response.ImportResponse;
import com.duytan.pharmacy.dto.response.SaleResponse;
import com.duytan.pharmacy.entity.ImportVoice;
import com.duytan.pharmacy.entity.SaleInvoice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ImportInvoiceMapper {
    ImportVoice toSaleInvoice(ImportRequest request);
    ImportResponse toSaleResponse(ImportVoice importVoice);
    //void updateSaleInvoice(@MappingTarget ImportVoice importVoice, ImportRequest request);
}
