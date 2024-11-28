package com.duytan.pharmacy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "disposal_invoice")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisposalInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;
    private boolean status;
    private String disposalMethod;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
