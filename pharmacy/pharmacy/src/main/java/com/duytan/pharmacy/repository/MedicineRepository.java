package com.duytan.pharmacy.repository;

import com.duytan.pharmacy.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine,Long> {
}
