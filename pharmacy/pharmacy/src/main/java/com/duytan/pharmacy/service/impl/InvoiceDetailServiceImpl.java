package com.duytan.pharmacy.service.impl;

import com.duytan.pharmacy.dto.request.InvoiceDetailRequest;
import com.duytan.pharmacy.dto.response.InvoiceDetailResponse;
import com.duytan.pharmacy.entity.Invoice;
import com.duytan.pharmacy.entity.InvoiceDetail;
import com.duytan.pharmacy.entity.Medicine;
import com.duytan.pharmacy.mapper.InvoiceDetailMapper;
import com.duytan.pharmacy.repository.InvoiceDetailRepository;
import com.duytan.pharmacy.repository.InvoiceRepository;
import com.duytan.pharmacy.repository.MedicineRepository;
import com.duytan.pharmacy.service.InvoiceDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceDetailServiceImpl implements InvoiceDetailService {
    InvoiceDetailRepository invoiceDetailRepository;
    InvoiceDetailMapper invoiceDetailMapper;
    MedicineRepository medicineRepository;
    InvoiceRepository invoiceRepository;

    @Override
    @Transactional
    public InvoiceDetailResponse createInvoiceDetail(InvoiceDetailRequest request, Long idInvoice) {
        InvoiceDetail detail = invoiceDetailMapper.toInvoiceDetail(request);

        Medicine medicine = medicineRepository.findById(request.getMedicineID())
                .orElseThrow(()-> new RuntimeException("Not found Medicine"));
        Invoice invoice = invoiceRepository.findById(idInvoice)
                .orElseThrow(()-> new RuntimeException("Not found Invoice"));
        detail.setInvoice(invoice);
        detail.setMedicine(medicine);
        invoiceDetailRepository.save(detail);
        if(invoice.getTypeInvoice() == 2){
            // update quantity medicine after Invoice
            medicine.setQuantity(medicine.getQuantity() - request.getQuantity());
            medicineRepository.save(medicine);
        }else if(invoice.getTypeInvoice() == 1){
            if(medicine.getQuantity() == 0){
                medicine.setQuantity(request.getQuantity());
                medicine.setPrice(request.getPrice());
                medicine.setUsageTime(request.getExperationDate());
            }else{
                medicine.setQuantity(medicine.getQuantity() + request.getQuantity());
                medicine.setPrice(request.getPrice());
                medicine.setUsageTime(request.getExperationDate());
            }
        }

        return invoiceDetailMapper.toInvoiceDetailResponse(detail);
    }

    @Override
    public InvoiceDetailResponse getByIdInvoice(Long idInvoice) {
        InvoiceDetail invoiceDetail = invoiceDetailRepository.findById(idInvoice)
                .orElseThrow(()-> new RuntimeException("Not found InvoiceDetail"));

        InvoiceDetailResponse response = invoiceDetailMapper.toInvoiceDetailResponse(invoiceDetail);
        response.setCodeMedicine(invoiceDetail.getMedicine().getCodeMedicine());
        response.setNameMedicine(invoiceDetail.getMedicine().getNameMedicine());
        response.setPrescriptionMedicine(invoiceDetail.getMedicine().isPrescriptionMedicine() == true ? 1 : 0);
        response.setInvoiceID(invoiceDetail.getInvoice().getId());
        return response;
    }
}
