package com.duytan.pharmacy.service.impl;

import com.duytan.pharmacy.dto.request.WorkShiftRequest;
import com.duytan.pharmacy.dto.response.AccountResponse;
import com.duytan.pharmacy.dto.response.WorkShiftResponse;
import com.duytan.pharmacy.entity.Account;
import com.duytan.pharmacy.entity.WorkShift;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.mapper.WorkShiftMapper;
import com.duytan.pharmacy.repository.WorkShiftRepository;
import com.duytan.pharmacy.service.WorkShiftService;
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
public class WorkShiftServiceImpl implements WorkShiftService {
    WorkShiftRepository workShiftRepository;
    WorkShiftMapper workShiftMapper;

    @Override
    @Transactional
    public WorkShiftResponse createWorkShift(WorkShiftRequest request) {
        WorkShift workShift = workShiftMapper.toWorkShift(request);
        return workShiftMapper.toWorkShiftResponse(workShiftRepository.save(workShift));
    }

    @Override
    @Transactional
    public WorkShiftResponse updateWorkShift(Long id, WorkShiftRequest request) {
        WorkShift workShift = workShiftRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Not found WorkShift"));
        workShiftMapper.updateWorkShift(workShift,request);
        return workShiftMapper.toWorkShiftResponse(workShiftRepository.save(workShift));
    }

    @Override
    @Transactional
    public void deleteWorkShift(Long id) {
        WorkShift workShift = workShiftRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Not found WorkShift"));
        workShiftRepository.delete(workShift);
    }

    @Override
    public PageResponse<WorkShiftResponse> getAllWorkShift(int page, int size) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<WorkShift> pageData = workShiftRepository.findAll(pageable);

        return PageResponse.<WorkShiftResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream()
                        .map(workShift -> workShiftMapper.toWorkShiftResponse(workShift))
                        .collect(Collectors.toList()))
                .build();
    }

}
