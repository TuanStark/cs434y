package com.duytan.pharmacy.mapper;

import com.duytan.pharmacy.dto.request.AccountRequest;
import com.duytan.pharmacy.dto.request.TypeMedicineRequest;
import com.duytan.pharmacy.dto.response.AccountResponse;
import com.duytan.pharmacy.dto.response.TypeMedicineResponse;
import com.duytan.pharmacy.entity.Account;
import com.duytan.pharmacy.entity.TypeMedicine;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "Spring")
public interface TypeMapper {
    TypeMedicine toTypeMedicine(TypeMedicineRequest request);
    TypeMedicineResponse toTypeMedicineResponse(TypeMedicine type);
    void updateType(@MappingTarget TypeMedicine type, TypeMedicineRequest request);
}
