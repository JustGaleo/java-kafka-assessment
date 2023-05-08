package com.example.interview.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.interview.entity.Scan;
import com.example.interview.repository.ScanRepository;

@Service
public class ScanServiceImpl implements ScanService{
	
	@Autowired
	private ScanRepository scanRepository;

	@Override
	public List<Scan> getAllScans() {
		List<Scan> scans = (List<Scan>) scanRepository.findAll();
		return scans;
	}

	@Override
	public Optional<Scan> getScanById(Long id) {
		Optional<Scan> scan = scanRepository.findById(Math.toIntExact(id));
		return scan;
	}
	
	

}
