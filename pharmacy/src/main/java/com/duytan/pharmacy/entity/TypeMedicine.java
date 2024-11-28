package com.duytan.pharmacy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type_medicine")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;

    @OneToMany(mappedBy = "typeMedicine",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Medicine> medicine = new HashSet<>();
}
