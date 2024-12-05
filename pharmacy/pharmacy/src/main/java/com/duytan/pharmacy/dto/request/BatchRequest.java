package com.duytan.pharmacy.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BatchRequest {
     String codeBatch;
     int quantity;
     Date importDate;
     Date productionDate;
     Date ExperationDate;
     Long importInvoiceID;
     Long medicineId;
}
