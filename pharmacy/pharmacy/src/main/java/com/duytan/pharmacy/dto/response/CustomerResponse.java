package com.duytan.pharmacy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
      Long id;
      String fullName;
      String phone;
      String address;
      Date dateOfBirth;
      boolean sex;
}
