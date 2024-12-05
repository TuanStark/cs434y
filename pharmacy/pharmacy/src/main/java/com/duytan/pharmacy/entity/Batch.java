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
@Table(name = "batch")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeBatch;
    private int quantity;
    private Date importDate;
    private Date productionDate;//ngày sản xuất
    private Date ExperationDate;// hạn sử dụng

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
    @ManyToOne
    @JoinColumn(name = "import_invoice_id")
    private ImportVoice importVoice;
}
