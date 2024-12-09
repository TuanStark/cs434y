package com.duytan.pharmacy.repository;

import com.duytan.pharmacy.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine,Long> {
    @Query("SELECT m FROM Medicine m WHERE " +
            "LOWER(m.nameMedicine) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.uses) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.ingredient) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Medicine> findByNameMedicine(@Param("keyword") String keyword);
}
