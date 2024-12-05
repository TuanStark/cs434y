package com.duytan.pharmacy.mapper;

import com.duytan.pharmacy.dto.request.SupplierRequest;
import com.duytan.pharmacy.dto.response.SupplierResponse;
import com.duytan.pharmacy.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toSupplier(SupplierRequest request);
    SupplierResponse toSupplierResponse(Supplier supplier);
    void updateSupplier(@MappingTarget Supplier supplier, SupplierRequest request);
}
