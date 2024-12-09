package com.duytan.pharmacy.dto.request;

import com.duytan.pharmacy.dto.response.MedicineResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceDTO {
    int quantity;
    double totalPrice;
    MedicineResponse medicine;
    double priceDiscount;

}
