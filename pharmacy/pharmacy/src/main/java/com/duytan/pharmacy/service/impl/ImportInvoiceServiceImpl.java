package com.duytan.pharmacy.service.impl;

import com.duytan.pharmacy.dto.request.ImportRequest;
import com.duytan.pharmacy.dto.response.ImportResponse;
import com.duytan.pharmacy.entity.ImportVoice;
import com.duytan.pharmacy.entity.Supplier;
import com.duytan.pharmacy.mapper.ImportInvoiceMapper;
import com.duytan.pharmacy.repository.ImportInvoiceRepository;
import com.duytan.pharmacy.repository.SupplierRepository;
import com.duytan.pharmacy.service.ImportInvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImportInvoiceServiceImpl implements ImportInvoiceService {
    ImportInvoiceRepository importInvoiceRepository;
    ImportInvoiceMapper importInvoiceMapper;
    SupplierRepository supplierRepository;

    @Override
    public ImportResponse createImportInvoice(ImportRequest request, Long idInvoice) {
        ImportVoice importInvoice = importInvoiceMapper.toSaleInvoice(request);
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(()-> new RuntimeException("Not found Supplier"));
        importInvoice.setSupplier(supplier);
        importInvoiceRepository.save(importInvoice);
        ImportResponse response = importInvoiceMapper.toSaleResponse(importInvoice);
        response.setNameSupplier(importInvoice.getSupplier().getName());
        return response;
    }

    @Override
    public ImportResponse getImportInvoice(Long idInvoice) {
        return null;
    }
}
