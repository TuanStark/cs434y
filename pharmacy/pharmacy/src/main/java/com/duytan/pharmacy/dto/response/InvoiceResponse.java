package com.duytan.pharmacy.dto.response;

import com.duytan.pharmacy.dto.request.InvoiceDetailRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceResponse {
    Long id;
    int typeInvoice;
    String codeInvoice;
    Date dateInvoice;
    double totalMoney;
    Date createAt;

    DisposalResponse disposalInvoiceID;
    SaleResponse saleInvoiceID;
    ImportResponse importInvoiceID;
    String nameAccount;
    List<InvoiceDetailResponse> invoiceDetailRequestList;
}
