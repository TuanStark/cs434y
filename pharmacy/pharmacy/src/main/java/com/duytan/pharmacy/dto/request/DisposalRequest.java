package com.duytan.pharmacy.dto.request;

import com.duytan.pharmacy.entity.Invoice;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisposalRequest {
    String reason;
    boolean status;
    String method;
    Long invoiceId;
}
