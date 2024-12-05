package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.MedicineRequest;
import com.duytan.pharmacy.dto.response.MedicineResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface MedicineService {
    MedicineResponse createMedicine(MedicineRequest request);
    MedicineResponse updateMedicine(Long id,MedicineRequest request);
    void deleteMedicine(Long id);
    MedicineResponse getMedicineById(Long id);
    PageResponse<MedicineResponse> getAllMedicine(int page, int size);
}
