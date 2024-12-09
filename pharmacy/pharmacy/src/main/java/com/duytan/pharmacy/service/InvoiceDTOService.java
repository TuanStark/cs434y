package com.duytan.pharmacy.service;

import com.duytan.pharmacy.dto.request.InvoiceDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public interface InvoiceDTOService {
    InvoiceDTO addCart(int quantity);
    HashMap<Long, InvoiceDTO> editCart(int quantity);
    HashMap<UUID, InvoiceDTO> deleteCart(Long idMedicine);
    int totalQuantity();
    double totalPrice();
    HashMap<UUID, InvoiceDTO> getAllCart();
    void removeAll();
}
