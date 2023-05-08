package com.example.interview.service;

import java.util.List;
import java.util.Optional;

import com.example.interview.entity.Scan;

public interface ScanService {
	public List<Scan> getAllScans();
	public Optional<Scan> getScanById(Long id);
	
}
