package com.duytan.pharmacy.controller;

import com.duytan.pharmacy.dto.request.AccountRequest;
import com.duytan.pharmacy.dto.request.WorkShiftRequest;
import com.duytan.pharmacy.dto.response.AccountResponse;
import com.duytan.pharmacy.dto.response.WorkShiftResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.helper.response.ResponseObject;
import com.duytan.pharmacy.service.WorkShiftService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ws")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class WorkShiftController {
    WorkShiftService workShiftService;

    @PostMapping("/create")
    public ResponseObject<WorkShiftResponse> create(@ModelAttribute WorkShiftRequest request){
        WorkShiftResponse data = workShiftService.createWorkShift(request);
        return ResponseObject.<WorkShiftResponse>builder()
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
                .data(workShiftService.getAllWorkShift(page,size))
                .build();
    }
    @PutMapping("/update/{id}")
    public ResponseObject<WorkShiftResponse> update(@PathVariable Long id,@ModelAttribute WorkShiftRequest request){
        WorkShiftResponse data = workShiftService.updateWorkShift(id, request);
        return ResponseObject.<WorkShiftResponse>builder()
                .data(data)
                .code(200)
                .message("SUCCESS")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject<Void> delete(@PathVariable Long id){
        workShiftService.deleteWorkShift(id);
        return ResponseObject.<Void>builder()
                .code(200)
                .message("SUCCESS")
                .build();
    }
    @GetMapping("/getById/{id}")
    public ResponseObject<WorkShiftResponse> getById(@PathVariable Long id){
        WorkShiftResponse response = workShiftService.getByIdWorkShift(id);
        return ResponseObject.<WorkShiftResponse>builder()
                .data(response)
                .code(200)
                .message("SUCCESS")
                .build();
    }
}
