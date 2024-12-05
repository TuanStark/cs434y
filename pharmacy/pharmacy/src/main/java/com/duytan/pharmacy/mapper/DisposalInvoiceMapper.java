package com.duytan.pharmacy.mapper;

import com.duytan.pharmacy.dto.request.DisposalRequest;
import com.duytan.pharmacy.dto.request.ImportRequest;
import com.duytan.pharmacy.dto.response.DisposalResponse;
import com.duytan.pharmacy.dto.response.ImportResponse;
import com.duytan.pharmacy.entity.DisposalInvoice;
import com.duytan.pharmacy.entity.ImportVoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisposalInvoiceMapper {
    DisposalInvoice toSaleInvoice(DisposalRequest request);
    DisposalResponse toSaleResponse(DisposalInvoice disposalInvoice);
    //void updateSaleInvoice(@MappingTarget ImportVoice importVoice, ImportRequest request);
}
