package com.duytan.pharmacy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeMedicineResponse {
       Long id;
       String name;
       String image;
       String description;
}
