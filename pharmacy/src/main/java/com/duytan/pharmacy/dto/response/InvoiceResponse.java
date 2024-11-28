package com.duytan.pharmacy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceResponse {
    Long id;
    int typeInvoice;
    Date invoiceDate;
    double totalMoney;
    Long idAccount;
    Date createAt;
}
