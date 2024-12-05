package com.duytan.pharmacy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceDetailResponse {
    Long id;
    // Medicine
    int quantity;
    String unitOfCalculator;
    double price;
    double totalMoney;
    String codeMedicine;
    String nameMedicine;
    int prescriptionMedicine;
    // idInvoice
    Long invoiceID;
}
