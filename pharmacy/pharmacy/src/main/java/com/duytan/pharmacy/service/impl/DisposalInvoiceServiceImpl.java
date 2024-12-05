package com.duytan.pharmacy.service.impl;

import com.duytan.pharmacy.dto.request.DisposalRequest;
import com.duytan.pharmacy.dto.response.DisposalResponse;
import com.duytan.pharmacy.entity.DisposalInvoice;
import com.duytan.pharmacy.mapper.DisposalInvoiceMapper;
import com.duytan.pharmacy.repository.DisposalInvoiceRepository;
import com.duytan.pharmacy.service.DisposalInvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DisposalInvoiceServiceImpl implements DisposalInvoiceService {
    DisposalInvoiceRepository disposalInvoiceRepository;
    DisposalInvoiceMapper disposalInvoiceMapper;

    @Override
    public DisposalResponse createDisposalInvoice(DisposalRequest request, Long idInvoice) {
        DisposalInvoice disposalInvoice = disposalInvoiceMapper.toSaleInvoice(request);
        return disposalInvoiceMapper.toSaleResponse(disposalInvoiceRepository.save(disposalInvoice));
    }

    @Override
    public DisposalResponse getDisposalInvoice(Long idInvoice) {
        return null;
    }
}
