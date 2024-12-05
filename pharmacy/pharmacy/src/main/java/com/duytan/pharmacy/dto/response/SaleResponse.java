package com.duytan.pharmacy.dto.response;

import com.duytan.pharmacy.entity.Invoice;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleResponse {
     Long id;
     String nameCustomer;
     String phone;

     String examinationCode;
     String examinationPlace;
}
