package com.duytan.pharmacy.dto.request;

import com.duytan.pharmacy.dto.response.InvoiceDetailResponse;
import com.duytan.pharmacy.entity.DisposalInvoice;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceRequest {
    int typeInvoice;
    String codeInvoice;
    Date dateInvoice;
    double totalMoney;
    Date createAt;

    DisposalRequest disposalInvoiceID;
    SaleRequest saleInvoiceID;
    ImportRequest importInvoiceID;
    Long accountID;
    List<InvoiceDetailRequest> invoiceDetailRequestList;
}
