package com.duytan.pharmacy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "role_id")
   // @JoinColumn(name = "role_name", referencedColumnName = "name")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "work_shift_id")
    private WorkShift workShift;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "information_account_id")
    private InformationAccount informationAccount;

    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Invoice> invoice = new HashSet<>();
}
