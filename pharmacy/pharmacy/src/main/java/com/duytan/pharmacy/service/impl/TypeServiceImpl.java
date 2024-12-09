package com.duytan.pharmacy.service.impl;

import com.duytan.pharmacy.dto.request.TypeMedicineRequest;
import com.duytan.pharmacy.dto.response.CustomerResponse;
import com.duytan.pharmacy.dto.response.TypeMedicineResponse;
import com.duytan.pharmacy.entity.Customer;
import com.duytan.pharmacy.entity.TypeMedicine;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.mapper.TypeMapper;
import com.duytan.pharmacy.repository.TypeMedicineRepository;
import com.duytan.pharmacy.service.TypeMedicineService;
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
public class TypeServiceImpl implements TypeMedicineService {
    TypeMedicineRepository typeMedicineRepository;
    TypeMapper typeMapper;

    @Override
    @Transactional
    public TypeMedicineResponse createType(TypeMedicineRequest request) {
        TypeMedicine type = typeMapper.toTypeMedicine(request);
        return typeMapper.toTypeMedicineResponse(typeMedicineRepository.save(type));
    }

    @Override
    @Transactional
    public TypeMedicineResponse updateType(Long id, TypeMedicineRequest request) {
        TypeMedicine type = typeMedicineRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Not found type"));
        typeMapper.updateType(type,request);
        return typeMapper.toTypeMedicineResponse(typeMedicineRepository.save(type));
    }

    @Override
    public TypeMedicineResponse getTypeById(Long id) {
        TypeMedicine type = typeMedicineRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Not found type"));
        return typeMapper.toTypeMedicineResponse(type);
    }

    @Override
    @Transactional
    public void deleteType(Long id) {
        TypeMedicine type = typeMedicineRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Not found type"));
        typeMedicineRepository.delete(type);
    }

    @Override
    @Transactional
    public PageResponse<TypeMedicineResponse> getAllType(int page, int size) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<TypeMedicine> pageData = typeMedicineRepository.findAll(pageable);

        return PageResponse.<TypeMedicineResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream()
                        .map(type -> typeMapper.toTypeMedicineResponse(type))
                        .collect(Collectors.toList()))
                .build();
    }
}
