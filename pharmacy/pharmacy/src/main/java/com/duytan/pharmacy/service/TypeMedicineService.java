package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.TypeMedicineRequest;
import com.duytan.pharmacy.dto.response.TypeMedicineResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface TypeMedicineService {
    TypeMedicineResponse createType(TypeMedicineRequest request);
    TypeMedicineResponse updateType(Long id,TypeMedicineRequest request);
    void deleteType(Long id);
    PageResponse<TypeMedicineResponse> getAllType(int page, int size);

}