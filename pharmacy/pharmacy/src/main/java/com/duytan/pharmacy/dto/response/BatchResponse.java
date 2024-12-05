package com.duytan.pharmacy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BatchResponse {
     Long id;
     String codeBatch;
     int quantity;
     Date importDate;
     Date productionDate;
     Date ExperationDate;
     Long importInvoiceID;
     Long medicineId;
}
