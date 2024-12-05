package com.duytan.pharmacy.service.impl;


import com.duytan.pharmacy.dto.request.InvoiceRequest;
import com.duytan.pharmacy.dto.response.*;
import com.duytan.pharmacy.entity.*;
import com.duytan.pharmacy.mapper.*;
import com.duytan.pharmacy.repository.*;
import com.duytan.pharmacy.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceServiceImpl implements InvoiceService {
    InvoiceRepository invoiceRepository;
    InvoiceMapper invoiceMapper;
    InvoiceDetailService invoiceDetailService;
    AccountRepository accountRepository;
    SaleInvoiceService saleInvoiceService;
    ImportInvoiceRepository importInvoiceRepository;
    DisposalInvoiceRepository disposalInvoiceRepository;
    SaleInvoiceRepository saleInvoiceRepository;
    ImportInvoiceMapper importInvoiceMapper;
    DisposalInvoiceMapper disposalInvoiceMapper;
    SaleInvoiceMapper saleInvoiceMapper;
    InvoiceDetailMapper invoiceDetailMapper;
    InvoiceDetailRepository invoiceDetailRepository;
    DisposalInvoiceService disposalInvoiceService;
    ImportInvoiceService importInvoiceService;

    @Override
    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Invoice invoice = invoiceMapper.toInvoice(request);
        invoiceRepository.save(invoice);
        setInvoiceType(invoice, request.getTypeInvoice(), request);

        Account account = accountRepository.findById(request.getAccountID())
                .orElseThrow(() -> new RuntimeException("Not Found Account"));
        invoice.setAccount(account);
        invoiceRepository.save(invoice);

        if (request.getInvoiceDetailRequestList() != null && !request.getInvoiceDetailRequestList().isEmpty()) {
            request.getInvoiceDetailRequestList().forEach(detailRequest ->
                    invoiceDetailService.createInvoiceDetail(detailRequest, invoice.getId())
            );
        }

        return this.convertInvoice(invoice, request);
    }

    private void setInvoiceType(Invoice invoice, int typeInvoice, InvoiceRequest request) {
        switch (typeInvoice) {
            case 1:
                invoice.setTypeInvoice(1);
                importInvoiceService.createImportInvoice(request.getImportInvoiceID(), invoice.getId());
                ImportVoice importVoice = importInvoiceRepository.findByInvoice_Id(invoice.getId());
                if (importVoice == null) {
                    throw new RuntimeException("ImportInvoice not create");
                }
                invoice.setImportVoice(importVoice);
                break;
            case 2:
                invoice.setTypeInvoice(2);
                saleInvoiceService.createSaleInvoice(request.getSaleInvoiceID(), invoice.getId());
                SaleInvoice saleInvoice = saleInvoiceRepository.findByInvoice_Id(invoice.getId());
                if (saleInvoice == null) {
                    throw new RuntimeException("SaleInvoice not create");
                }
                invoice.setSaleInvoice(saleInvoice);
                break;
            case 3:
                invoice.setTypeInvoice(3);
                disposalInvoiceService.createDisposalInvoice(request.getDisposalInvoiceID(), invoice.getId());
                DisposalInvoice disposalInvoice = disposalInvoiceRepository.findByInvoice_Id(invoice.getId());
                if(disposalInvoice == null){
                    throw new RuntimeException("DisposalInvoice not create");
                }
                invoice.setDisposalInvoice(disposalInvoice);
                break;
            default:
                throw new RuntimeException("TypeInvoice not invalid");
        }
    }

    @Override
    public InvoiceResponse getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với ID: " + id));
        InvoiceRequest request = new InvoiceRequest();
        return convertInvoice(invoice, request);
    }

    public InvoiceResponse convertInvoice(Invoice invoice, InvoiceRequest request) {
        InvoiceResponse response = invoiceMapper.toResponse(invoice);
        List<InvoiceDetailResponse> list = new ArrayList<>();
        response.setNameAccount(invoice.getAccount().getFullName());
        if (response.getTypeInvoice() == 1) {
            ImportResponse importResponse = importInvoiceMapper.toSaleResponse(invoice.getImportVoice());
            response.setImportInvoiceID(importResponse);
        } else if (response.getTypeInvoice() == 2) {
            SaleResponse saleResponse = saleInvoiceMapper.toSaleResponse(invoice.getSaleInvoice());
            response.setSaleInvoiceID(saleResponse);
        } else if (response.getTypeInvoice() == 3) {
            DisposalResponse disposalResponse = disposalInvoiceMapper.toSaleResponse(invoice.getDisposalInvoice());
            response.setDisposalInvoiceID(disposalResponse);
        }
        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findByInvoice_Id(invoice.getId());
        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            InvoiceDetailResponse detailResponse = invoiceDetailMapper.toInvoiceDetailResponse(invoiceDetail);

            if (invoiceDetail.getMedicine() != null) {
                detailResponse.setCodeMedicine(invoiceDetail.getMedicine().getCodeMedicine());
                detailResponse.setNameMedicine(invoiceDetail.getMedicine().getNameMedicine());
                detailResponse.setPrescriptionMedicine(invoiceDetail.getMedicine().isPrescriptionMedicine() ? 1 : 0);
            } else {
                log.warn("Hóa đơn chi tiết với ID {} không có thông tin thuốc.", invoiceDetail.getId());
            }
            detailResponse.setInvoiceID(invoiceDetail.getInvoice().getId());
            list.add(detailResponse);
        }
        response.setInvoiceDetailRequestList(list);
        return response;
    }

}
