package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.WorkShiftRequest;
import com.duytan.pharmacy.dto.response.WorkShiftResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface WorkShiftService {
    WorkShiftResponse createWorkShift(WorkShiftRequest request);
    WorkShiftResponse updateWorkShift(Long id,WorkShiftRequest request);
    void deleteWorkShift(Long id);
    PageResponse<WorkShiftResponse> getAllWorkShift(int page, int size);
}
