package com.example.interview.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.interview.entity.Scan;

public interface ScanRepository extends CrudRepository<Scan, Integer> {
	

}