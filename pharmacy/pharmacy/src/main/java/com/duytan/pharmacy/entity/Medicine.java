package com.duytan.pharmacy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medicines")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeMedicine;
    private String nameMedicine;
    private boolean prescriptionMedicine;// thuốc theo toa
    private String attention;
    private String description;
    private String dosage;// liều lượng
    private String ingredient;// thành phần
    private String instructionsUse;// hdsd
    private String packagingMethod; // cách đóng gói
    private String sideEffects;// tác dụng phụ
    private String unitOfCalculation;// dvt
    private int quantity;
    private double price;
    private Date usageTime;
    private String uses;

    @ManyToOne
    @JoinColumn(name = "type_medicine_id")
    private TypeMedicine typeMedicine;

    @OneToMany(mappedBy = "medicine",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<InvoiceDetail> invoiceDetail = new HashSet<>();
    @OneToMany(mappedBy = "medicine",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Batch> batch = new HashSet<>();
}
