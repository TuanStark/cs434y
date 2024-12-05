package com.duytan.pharmacy.service.impl;

import com.duytan.pharmacy.dto.request.MedicineRequest;
import com.duytan.pharmacy.dto.response.CustomerResponse;
import com.duytan.pharmacy.dto.response.MedicineResponse;
import com.duytan.pharmacy.entity.Customer;
import com.duytan.pharmacy.entity.Medicine;
import com.duytan.pharmacy.entity.TypeMedicine;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.mapper.MedicineMapper;
import com.duytan.pharmacy.repository.MedicineRepository;
import com.duytan.pharmacy.repository.TypeMedicineRepository;
import com.duytan.pharmacy.service.MedicineService;
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

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MedicineServiceImpl implements MedicineService {
    MedicineRepository medicineRepository;
    MedicineMapper medicineMapper;
    TypeMedicineRepository typeMedicineRepository;

    @Override
    @Transactional
    public MedicineResponse createMedicine(MedicineRequest request) {
        Medicine medicine = medicineMapper.toMedicine(request);
        TypeMedicine type = typeMedicineRepository.findById(request.getIdType()).orElseThrow(()-> new RuntimeException("Not found type"));
        medicine.setTypeMedicine(type);
        medicineRepository.save(medicine);

        MedicineResponse response = medicineMapper.toMedicineResponse(medicine);
        response.setNameType(medicine.getTypeMedicine().getName());

        return response;
    }

    @Override
    @Transactional
    public MedicineResponse updateMedicine(Long id, MedicineRequest request) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Not found medicine"));
        medicineMapper.updateMedicine(medicine,request);
        TypeMedicine type = typeMedicineRepository.findById(request.getIdType()).orElseThrow(()-> new RuntimeException("Not found type"));
        medicine.setTypeMedicine(type);
        medicineRepository.save(medicine);

        MedicineResponse response = medicineMapper.toMedicineResponse(medicine);
        response.setNameType(medicine.getTypeMedicine().getName());
        return response;
    }

    @Override
    @Transactional
    public void deleteMedicine(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Not found medicine"));
        medicineRepository.delete(medicine);
    }

    @Override
    @Transactional
    public MedicineResponse getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Not found medicine"));
        MedicineResponse response = medicineMapper.toMedicineResponse(medicine);
        response.setNameType(medicine.getTypeMedicine().getName());
        return response;
    }

    @Override
    public PageResponse<MedicineResponse> getAllMedicine(int page, int size) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Medicine> pageData = medicineRepository.findAll(pageable);

        if (pageData.isEmpty()) {
            return PageResponse.<MedicineResponse>builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalPage(0)
                    .totalElement(0)
                    .data(Collections.emptyList())
                    .build();
        }
        return PageResponse.<MedicineResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream()
                        .map(medicine -> {
                            MedicineResponse response = medicineMapper.toMedicineResponse(medicine);
                            response.setNameType(medicine.getTypeMedicine().getName());
                            return response;
                        })
                        .collect(Collectors.toList()))
                .build();
    }
}
