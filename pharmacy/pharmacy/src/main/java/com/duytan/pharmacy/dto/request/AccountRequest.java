package com.duytan.pharmacy.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest {
     String username;
     String password;
     boolean status;
     String fullName;
     String phone;
     String address;
     boolean sex;
     Date dateOfBirth;
    Long idWorkShift;
}
