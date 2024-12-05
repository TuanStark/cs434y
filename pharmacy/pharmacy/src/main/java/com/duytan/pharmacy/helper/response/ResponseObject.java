package com.duytan.pharmacy.helper.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseObject<T> {
    private String message;
    private int code;
    private T data;
}
