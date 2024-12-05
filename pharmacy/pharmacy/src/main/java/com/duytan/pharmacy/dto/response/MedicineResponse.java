package com.duytan.pharmacy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicineResponse {
      Long id;
      String codeMedicine;
      String nameMedicine;
      boolean prescriptionMedicine;
      String attention;
      String description;
      String dosage;
      String ingredient;
      String instructionsUse;
      String packagingMethod;
      String sideEffects;
      String unitOfCalculation;
      int quantity;
      double price;
      Date usageTime;
      String uses;
      String nameType;
}
