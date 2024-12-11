package com.duytan.pharmacy.controller;

import com.duytan.pharmacy.dto.request.InvoiceRequest;
import com.duytan.pharmacy.dto.response.InvoiceResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.helper.response.ResponseObject;
import com.duytan.pharmacy.service.InvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class InvoiceController {
    InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseObject<InvoiceResponse> create(@RequestBody InvoiceRequest request){
        System.out.println("TypeInvoice: " + request.getTypeInvoice());
        InvoiceResponse data = invoiceService.createInvoice(request);
        return ResponseObject.<InvoiceResponse>builder()
                .data(data)
                .message("SUCCESS")
                .code(200)
                .build();
    }

    @GetMapping("/all")
    public ResponseObject<PageResponse> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false) Integer size){
        if (size == null) {
            size = Integer.MAX_VALUE;
        }
        return ResponseObject.<PageResponse>builder()
                .code(200)
                .message("SUCCESS")
                .data(invoiceService.getAll(page,size))
                .build();
    }

    @GetMapping("/getById/{idInvoice}")
    public ResponseObject<InvoiceResponse> getById(@RequestParam Long idInvoice){
        InvoiceResponse data = invoiceService.getInvoiceById(idInvoice);

        return ResponseObject.<InvoiceResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
                .build();
    }
}
