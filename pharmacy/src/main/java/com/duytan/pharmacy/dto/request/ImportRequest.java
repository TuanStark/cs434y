package com.duytan.pharmacy.dto.request;

import com.duytan.pharmacy.entity.Invoice;
import com.duytan.pharmacy.entity.Supplier;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImportRequest {
    Date importDate;
    Invoice invoiceRequest;
    Supplier supplier;
}