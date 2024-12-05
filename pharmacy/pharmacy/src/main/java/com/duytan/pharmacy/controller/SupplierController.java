package com.duytan.pharmacy.controller;

import com.duytan.pharmacy.dto.request.MedicineRequest;
import com.duytan.pharmacy.dto.request.SupplierRequest;
import com.duytan.pharmacy.dto.response.MedicineResponse;
import com.duytan.pharmacy.dto.response.SupplierResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.helper.response.ResponseObject;
import com.duytan.pharmacy.service.MedicineService;
import com.duytan.pharmacy.service.SupplierService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class SupplierController {
    SupplierService supplierService;

    @PostMapping("/create")
    public ResponseObject<SupplierResponse> create(@RequestBody SupplierRequest request){
        SupplierResponse data = supplierService.createSupplier(request);
        return ResponseObject.<SupplierResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
                .build();
    }
    @GetMapping("/all")
    public ResponseObject<PageResponse> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "keyword", required = false) String keyword){
        if (size == null) {
            size = Integer.MAX_VALUE;
        }
        return ResponseObject.<PageResponse>builder()
                .code(200)
                .message("SUCCESS")
                .data(supplierService.getAllSupplier(page,size,keyword))
                .build();
    }
    @PutMapping("/update/{id}")
    public ResponseObject<SupplierResponse> update(@PathVariable Long id,@RequestBody SupplierRequest request){
        SupplierResponse data = supplierService.updateSupplier(id, request);
        return ResponseObject.<SupplierResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject<Void> update(@PathVariable Long id){
        supplierService.deleteSupplier(id);
        return ResponseObject.<Void>builder()
                .code(200)
                .message("SUCCESS")
                .build();
    }

    @GetMapping("/getById/{id}")
    public ResponseObject<SupplierResponse> getById(@PathVariable Long id){
        SupplierResponse data = supplierService.getSupplier(id);
        return ResponseObject.<SupplierResponse>builder()
                .code(200)
                .message("SUCCESS")
                .data(data)
                .build();
    }

}
