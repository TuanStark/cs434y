package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.SupplierRequest;
import com.duytan.pharmacy.dto.response.SupplierResponse;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface SupplierService {
    SupplierResponse createSupplier(SupplierRequest request);
    SupplierResponse updateSupplier(Long id,SupplierRequest request);
    void deleteSupplier(Long id);
    SupplierResponse getSupplier(Long id);
    public PageResponse<SupplierResponse> getAllSupplier(int page, int size, String keyword);
}
