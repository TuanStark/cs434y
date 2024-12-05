package com.duytan.pharmacy.controller;

import com.duytan.pharmacy.dto.request.InvoiceRequest;
import com.duytan.pharmacy.dto.response.InvoiceResponse;
import com.duytan.pharmacy.helper.response.ResponseObject;
import com.duytan.pharmacy.service.InvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class InvoiceController {
    InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseObject<InvoiceResponse> create(@RequestBody InvoiceRequest request){
        InvoiceResponse data = invoiceService.createInvoice(request);
        return ResponseObject.<InvoiceResponse>builder()
                .data(data)
                .message("SUCCESS")
                .code(200)
                .build();
    }
}
