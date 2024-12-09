package com.duytan.pharmacy.controller;

import com.duytan.pharmacy.dto.request.TypeMedicineRequest;
import com.duytan.pharmacy.dto.request.WorkShiftRequest;
import com.duytan.pharmacy.dto.response.TypeMedicineResponse;
import com.duytan.pharmacy.dto.response.WorkShiftResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.helper.response.ResponseObject;
import com.duytan.pharmacy.service.TypeMedicineService;
import com.duytan.pharmacy.service.WorkShiftService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TypeController {
    TypeMedicineService typeMedicineService;

    @PostMapping("/create")
    public ResponseObject<TypeMedicineResponse> create(@ModelAttribute TypeMedicineRequest request){
        TypeMedicineResponse data = typeMedicineService.createType(request);
        return ResponseObject.<TypeMedicineResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
                .build();
    }
    @GetMapping("/all")
    public ResponseObject<PageResponse> getUser(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false) Integer size){
        if (size == null) {
            size = Integer.MAX_VALUE;
        }
        return ResponseObject.<PageResponse>builder()
                .code(200)
                .message("SUCCESS")
                .data(typeMedicineService.getAllType(page,size))
                .build();
    }
    @PutMapping("/update/{id}")
    public ResponseObject<TypeMedicineResponse> update(@PathVariable Long id,@ModelAttribute TypeMedicineRequest request){
        TypeMedicineResponse data = typeMedicineService.updateType(id, request);
        return ResponseObject.<TypeMedicineResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject<Void> delete(@PathVariable Long id){
        typeMedicineService.deleteType(id);
        return ResponseObject.<Void>builder()
                .code(200)
                .message("SUCCESS")
                .build();
    }

    @GetMapping("/getById/{id}")
    public ResponseObject<TypeMedicineResponse> getbyId(@PathVariable Long id){
        TypeMedicineResponse response = typeMedicineService.getTypeById(id);
        return ResponseObject.<TypeMedicineResponse>builder()
                .code(200)
                .message("SUCCESS")
                .data(response)
                .build();
    }

}
