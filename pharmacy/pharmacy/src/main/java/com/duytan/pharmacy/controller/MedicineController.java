package com.duytan.pharmacy.controller;

import com.duytan.pharmacy.dto.request.CustomerRequest;
import com.duytan.pharmacy.dto.request.MedicineRequest;
import com.duytan.pharmacy.dto.response.CustomerResponse;
import com.duytan.pharmacy.dto.response.MedicineResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.helper.response.ResponseObject;
import com.duytan.pharmacy.service.CustomerService;
import com.duytan.pharmacy.service.MedicineService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class MedicineController {
    MedicineService medicineService;

    @PostMapping("/create")
    public ResponseObject<MedicineResponse> create(@RequestBody MedicineRequest request){
        MedicineResponse data = medicineService.createMedicine(request);
        return ResponseObject.<MedicineResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
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
                .data(medicineService.getAllMedicine(page,size))
                .build();
    }
    @PutMapping("/update/{id}")
    public ResponseObject<MedicineResponse> update(@PathVariable Long id,@RequestBody MedicineRequest request){
        MedicineResponse data = medicineService.updateMedicine(id, request);
        return ResponseObject.<MedicineResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject<Void> update(@PathVariable Long id){
        medicineService.deleteMedicine(id);
        return ResponseObject.<Void>builder()
                .code(200)
                .message("SUCCESS")
                .build();
    }

    @GetMapping("/getById/{id}")
    public ResponseObject<MedicineResponse> getById(@PathVariable Long id){
        MedicineResponse data = medicineService.getMedicineById(id);
        return ResponseObject.<MedicineResponse>builder()
                .code(200)
                .message("SUCCESS")
                .data(data)
                .build();
    }

}
