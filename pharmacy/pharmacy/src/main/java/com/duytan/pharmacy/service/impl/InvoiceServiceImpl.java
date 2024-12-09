package com.duytan.pharmacy.service.impl;


import com.duytan.pharmacy.dto.request.InvoiceRequest;
import com.duytan.pharmacy.dto.response.*;
import com.duytan.pharmacy.entity.*;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.mapper.*;
import com.duytan.pharmacy.repository.*;
import com.duytan.pharmacy.service.*;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        invoice.setDateInvoice(new Date());
        String datePart = new SimpleDateFormat("yyMMdd").format(new Date());
        int randomPart = new Random().nextInt(90) + 10;
        String randomString = (datePart + randomPart).substring(0, 7);
        invoice.setCodeInvoice("HD"+randomString);
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

        return this.convertInvoice(invoice);
    }

    private void setInvoiceType(Invoice invoice, int typeInvoice, InvoiceRequest request) {
        switch (typeInvoice) {
            case 1:
                invoice.setTypeInvoice(typeInvoice);
                importInvoiceService.createImportInvoice(request.getImportInvoiceID(), invoice.getId());
                ImportVoice importVoice = importInvoiceRepository.findByInvoice_Id(invoice.getId());
                if (importVoice == null) {
                    throw new RuntimeException("ImportInvoice not create");
                }
                invoice.setImportVoice(importVoice);
                break;
            case 2:
                invoice.setTypeInvoice(typeInvoice);
                saleInvoiceService.createSaleInvoice(request.getSaleInvoiceID(), invoice.getId());
                SaleInvoice saleInvoice = saleInvoiceRepository.findByInvoice_Id(invoice.getId());
                if (saleInvoice == null) {
                    throw new RuntimeException("SaleInvoice not create");
                }
                invoice.setSaleInvoice(saleInvoice);
                break;
            case 3:
                invoice.setTypeInvoice(typeInvoice);
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
                .orElseThrow(() -> new RuntimeException("Not found Invoice"));
        return this.convertInvoice(invoice);
    }

    @Override
    public PageResponse<InvoiceResponse> getAll(int page, int size) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Invoice> pageData = invoiceRepository.findAll(pageable);

        if (pageData.isEmpty()) {
            return PageResponse.<InvoiceResponse>builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalPage(0)
                    .totalElement(0)
                    .data(Collections.emptyList())
                    .build();
        }
        return PageResponse.<InvoiceResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(this::convertInvoice).toList())
                .build();

    }

    public InvoiceResponse convertInvoice(Invoice invoice) {
        InvoiceResponse response = invoiceMapper.toResponse(invoice);
        List<InvoiceDetailResponse> list = new ArrayList<>();
        response.setNameAccount(invoice.getAccount().getFullName());
        if (response.getTypeInvoice() == 1) {
            ImportResponse importResponse = importInvoiceMapper.toSaleResponse(invoice.getImportVoice());
            importResponse.setNameSupplier(invoice.getImportVoice().getSupplier().getName());
            response.setImportInvoiceID(importResponse);
        } else if (response.getTypeInvoice() == 2) {
            SaleResponse saleResponse = saleInvoiceMapper.toSaleResponse(invoice.getSaleInvoice());
            saleResponse.setNameCustomer(invoice.getSaleInvoice().getCustomer().getFullName());
            saleResponse.setPhone(invoice.getSaleInvoice().getCustomer().getPhone());
            response.setSaleInvoiceID(saleResponse);
        } else if (response.getTypeInvoice() == 3) {
            DisposalResponse disposalResponse = disposalInvoiceMapper.toSaleResponse(invoice.getDisposalInvoice());
            disposalResponse.setMethod(invoice.getDisposalInvoice().getDisposalMethod());
            disposalResponse.setReason(invoice.getDisposalInvoice().getReason());
            response.setDisposalInvoiceID(disposalResponse);
        }
        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findByInvoice_Id(invoice.getId());
        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            InvoiceDetailResponse detailResponse = invoiceDetailMapper.toInvoiceDetailResponse(invoiceDetail);

            if (invoiceDetail.getMedicine() != null) {
                detailResponse.setCodeMedicine(invoiceDetail.getMedicine().getCodeMedicine());
                detailResponse.setNameMedicine(invoiceDetail.getMedicine().getNameMedicine());
                detailResponse.setPrescriptionMedicine(invoiceDetail.getMedicine().isPrescriptionMedicine() ? 1 : 0);
            }
            detailResponse.setInvoiceID(invoiceDetail.getInvoice().getId());
            list.add(detailResponse);
        }
        response.setInvoiceDetailRequestList(list);
        return response;
    }

}
