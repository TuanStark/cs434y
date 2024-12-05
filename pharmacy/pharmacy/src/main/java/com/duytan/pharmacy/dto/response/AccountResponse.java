package com.duytan.pharmacy.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    Long id;
    String fullName;
    String phone;
    boolean sex;
    Date dateOfBirth;
    String address;
    String username;
    String password;
    boolean status;
    String roleName;
    String nameWorkShift;
}
