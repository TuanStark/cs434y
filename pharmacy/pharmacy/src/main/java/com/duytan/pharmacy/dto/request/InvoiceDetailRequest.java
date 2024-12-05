package com.duytan.pharmacy.dto.request;

import com.duytan.pharmacy.entity.Invoice;
import com.duytan.pharmacy.entity.Medicine;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceDetailRequest {
     int quantity;
     String unitOfCalculator;
     double price;
     double totalMoney;
     Long medicineID;
     Long invoiceID;
}