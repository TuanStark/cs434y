package com.duytan.pharmacy.dto.request;

import com.duytan.pharmacy.entity.DisposalInvoice;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceRequest {
    int typeInvoice;
    String codeInvoice;
    Date invoiceDate;
    double totalMoney;
    Long idAccount;
    Date createAt;

    DisposalInvoice disposalRequest;// lấy id của disposalInvoice
}
