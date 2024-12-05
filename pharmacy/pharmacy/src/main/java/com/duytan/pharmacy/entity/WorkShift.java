package com.duytan.pharmacy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "work_shifts")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int time;

    @OneToMany(mappedBy = "workShift",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Account> news = new HashSet<>();
}
