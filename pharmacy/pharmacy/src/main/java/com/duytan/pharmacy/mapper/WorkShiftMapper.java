package com.duytan.pharmacy.mapper;


import com.duytan.pharmacy.dto.request.WorkShiftRequest;
import com.duytan.pharmacy.dto.response.WorkShiftResponse;
import com.duytan.pharmacy.entity.WorkShift;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorkShiftMapper {
    WorkShift toWorkShift(WorkShiftRequest request);
    WorkShiftResponse toWorkShiftResponse(WorkShift workShift);
    void updateWorkShift(@MappingTarget WorkShift workShift, WorkShiftRequest request);
}
