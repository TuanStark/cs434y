package com.duytan.pharmacy.service.impl;

import com.duytan.pharmacy.dto.request.SupplierRequest;
import com.duytan.pharmacy.dto.response.SupplierResponse;
import com.duytan.pharmacy.entity.Supplier;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.mapper.SupplierMapper;
import com.duytan.pharmacy.repository.SupplierRepository;
import com.duytan.pharmacy.service.SupplierService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierServiceImpl implements SupplierService {
    SupplierRepository supplierRepository;
    SupplierMapper supplierMapper;

    @Override
    @Transactional
    public SupplierResponse createSupplier(SupplierRequest request) {
        Supplier supplier = supplierMapper.toSupplier(request);
        return supplierMapper.toSupplierResponse(supplierRepository.save(supplier));
    }

    @Override
    @Transactional
    public SupplierResponse updateSupplier(Long id, SupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(()->new RuntimeException("Not found Supplier"));
        supplierMapper.updateSupplier(supplier,request);
        return supplierMapper.toSupplierResponse(supplierRepository.save(supplier));
    }

    @Override
    @Transactional
    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(()->new RuntimeException("Not found Supplier"));
        supplierRepository.delete(supplier);
    }

    @Override
    public SupplierResponse getSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(()->new RuntimeException("Not found Supplier"));
        return supplierMapper.toSupplierResponse(supplierRepository.save(supplier));
    }

    @Override
    public PageResponse<SupplierResponse> getAllSupplier(int page, int size, String keyword) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Supplier> pageData;
        if (keyword == null || keyword.trim().isEmpty()) {
            pageData = supplierRepository.findAll(pageable);
        } else {
            pageData = supplierRepository.searchByKeyword(keyword, pageable);
        }

        return PageResponse.<SupplierResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream()
                        .map(supplier -> supplierMapper.toSupplierResponse(supplier))
                        .collect(Collectors.toList()))
                .build();
    }

}
