package com.duytan.pharmacy.mapper;

import com.duytan.pharmacy.dto.request.CustomerRequest;
import com.duytan.pharmacy.dto.request.MedicineRequest;
import com.duytan.pharmacy.dto.response.CustomerResponse;
import com.duytan.pharmacy.dto.response.MedicineResponse;
import com.duytan.pharmacy.entity.Customer;
import com.duytan.pharmacy.entity.Medicine;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MedicineMapper {
    Medicine toMedicine(MedicineRequest request);
    MedicineResponse toMedicineResponse(Medicine medicine);
    void updateMedicine(@MappingTarget Medicine medicine, MedicineRequest request);
}
