package com.duytan.pharmacy.service.impl;

import com.duytan.pharmacy.dto.request.InvoiceDTO;
import com.duytan.pharmacy.service.InvoiceDTOService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceDTOServiceImpl implements InvoiceDTOService {
    @Override
    public InvoiceDTO addCart(int quantity) {
        return null;
    }

    @Override
    public HashMap<Long, InvoiceDTO> editCart(int quantity) {
        return null;
    }

    @Override
    public HashMap<UUID, InvoiceDTO> deleteCart(Long idMedicine) {
        return null;
    }

    @Override
    public int totalQuantity() {
        return 0;
    }

    @Override
    public double totalPrice() {
        return 0;
    }

    @Override
    public HashMap<UUID, InvoiceDTO> getAllCart() {
        return null;
    }

    @Override
    public void removeAll() {

    }
}
