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
@Table(name = "invoices")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeInvoice;
    private int typeInvoice;
    private Date dateInvoice;
    private double totalMoney;
    private Date createAt;
    private Date updateAt;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "invoice",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<InvoiceDetail> invoiceDetail = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "import_invoice_id")
    private ImportVoice importVoice;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "disposal_invoice_id")
    private DisposalInvoice disposalInvoice;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_invoice_id")
    private SaleInvoice saleInvoice;
}
