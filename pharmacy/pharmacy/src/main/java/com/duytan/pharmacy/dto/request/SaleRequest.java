package com.duytan.pharmacy.dto.request;

import com.duytan.pharmacy.entity.Customer;
import com.duytan.pharmacy.entity.Invoice;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleRequest {
     String phone;
     String CustomerName;
     String examinationCode;
     String examinationPlace;
     Long invoiceID;
}
