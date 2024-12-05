package com.duytan.pharmacy.mapper;

import com.duytan.pharmacy.dto.request.BatchRequest;
import com.duytan.pharmacy.dto.response.BatchResponse;
import com.duytan.pharmacy.entity.Batch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BatchMapper {
    Batch toBatch(BatchRequest request);
    BatchResponse toBatchResponse(Batch batch);
}
