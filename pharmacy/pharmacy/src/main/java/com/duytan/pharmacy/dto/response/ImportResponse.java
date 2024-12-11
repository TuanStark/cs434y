package com.duytan.pharmacy.dto.response;

import com.duytan.pharmacy.entity.Invoice;
import com.duytan.pharmacy.entity.Supplier;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImportResponse {
    Long id;
    Date importDate;
    String codeBatch;
    Date productionDate;
    double sellingPrice;
    SupplierResponse nameSupplier;
    Long InvoiceId;
}
