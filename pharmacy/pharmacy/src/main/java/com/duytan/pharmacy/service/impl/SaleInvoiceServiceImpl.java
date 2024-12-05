package com.duytan.pharmacy.service.impl;

import com.duytan.pharmacy.dto.request.CustomerRequest;
import com.duytan.pharmacy.dto.request.SaleRequest;
import com.duytan.pharmacy.dto.response.MedicineResponse;
import com.duytan.pharmacy.dto.response.SaleResponse;
import com.duytan.pharmacy.entity.Customer;
import com.duytan.pharmacy.entity.Invoice;
import com.duytan.pharmacy.entity.Medicine;
import com.duytan.pharmacy.entity.SaleInvoice;
import com.duytan.pharmacy.helper.pagination.PageResponse;
import com.duytan.pharmacy.mapper.SaleInvoiceMapper;
import com.duytan.pharmacy.repository.CustomerRepository;
import com.duytan.pharmacy.repository.InvoiceRepository;
import com.duytan.pharmacy.repository.SaleInvoiceRepository;
import com.duytan.pharmacy.service.CustomerService;
import com.duytan.pharmacy.service.SaleInvoiceService;
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

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SaleInvoiceServiceImpl implements SaleInvoiceService {
    SaleInvoiceRepository saleInvoiceRepository;
    InvoiceRepository invoiceRepository;
    CustomerRepository customerRepository;
    SaleInvoiceMapper saleInvoiceMapper;
    CustomerService customerService;

    @Override
    @Transactional
    public SaleResponse createSaleInvoice(SaleRequest request, Long idInvoice) {
        Customer customer = customerRepository.findByPhoneAndFullName(request.getPhone(), request.getCustomerName());

        if (customer == null) {
            customer = customerService.createCustomerForSale(request.getPhone(), request.getCustomerName());
        }

        SaleInvoice sale = saleInvoiceMapper.toSaleInvoice(request);
        Invoice invoice = invoiceRepository.findById(idInvoice)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với id: " + idInvoice));
        sale.setInvoice(invoice);
        sale.setCustomer(customer);
        saleInvoiceRepository.save(sale);

        SaleResponse response = saleInvoiceMapper.toSaleResponse(sale);
        response.setPhone(customer.getPhone());
        response.setNameCustomer(customer.getFullName());

        return response;
    }



    @Override
    public SaleResponse getSaleInvoice(Long idInvoice) {
        Invoice invoice = invoiceRepository.findById(idInvoice).orElseThrow(()-> new RuntimeException("Not found Invoice"));
        SaleInvoice sale = saleInvoiceRepository.findByInvoice_Id(invoice.getId());

        Customer customer = customerRepository.findByPhoneAndFullName(sale.getCustomer().getPhone(),sale.getCustomer().getFullName());
        SaleResponse response = saleInvoiceMapper.toSaleResponse(sale);
        response.setPhone(customer.getPhone());
        response.setNameCustomer(customer.getFullName());
        return response;
    }

    @Override
    public PageResponse<SaleResponse> getAll(int page, int size) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<SaleInvoice> pageData = saleInvoiceRepository.findAll(pageable);

        if (pageData.isEmpty()) {
            return PageResponse.<SaleResponse>builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalPage(0)
                    .totalElement(0)
                    .data(Collections.emptyList())
                    .build();
        }
        return PageResponse.<SaleResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream()
                        .map(sale -> {
                            Customer customer = customerRepository.findByPhoneAndFullName(sale.getCustomer().getPhone(),sale.getCustomer().getFullName());
                            SaleResponse response = saleInvoiceMapper.toSaleResponse(sale);
                            response.setPhone(customer.getPhone());
                            response.setNameCustomer(customer.getFullName());
                            return response;
                        })
                        .collect(Collectors.toList()))
                .build();
    }
}
