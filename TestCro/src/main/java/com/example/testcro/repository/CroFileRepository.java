package com.example.testcro.repository;

import com.example.testcro.Data;
import com.example.testcro.entity.CroFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CroFileRepository extends JpaRepository<CroFile, Long> {
}
