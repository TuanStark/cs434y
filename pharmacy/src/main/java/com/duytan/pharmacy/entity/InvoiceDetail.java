package com.duytan.pharmacy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoice_detail")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private String unitOfCalculator;
    private double price;
    private double totalMoney;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
