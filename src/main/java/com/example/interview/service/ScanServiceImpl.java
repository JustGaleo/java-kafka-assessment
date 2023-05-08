package com.example.interview.service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.interview.entity.Scan;
import com.example.interview.models.DateRangeRequest;
import com.example.interview.repository.ScanRepository;

@Service
public class ScanServiceImpl implements ScanService {

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

	@Override
	public List<Scan> getScanByDateRange(DateRangeRequest request) {
		List<Scan> scans = (List<Scan>) scanRepository.findAll();
		List<Scan> response = null;
		if (request.getStartDate() != null && request.getEndDate() != null) {
			LocalDateTime start = LocalDateTime.parse(request.getStartDate());
			LocalDateTime end = LocalDateTime.parse(request.getEndDate());
			response = scans.stream().filter(e -> e.getTimestamp().isAfter(start) && e.getTimestamp().isBefore(end))
					.toList();
		} else {
			if(request.getStartDate() == null && request.getEndDate() == null) {
				response = scans;
			}else if (request.getStartDate() == null) {
				LocalDateTime end = LocalDateTime.parse(request.getEndDate());
				response = scans.stream().filter(e -> e.getTimestamp().isBefore(end)).toList();
			} else if (request.getEndDate() == null) {
				LocalDateTime start = LocalDateTime.parse(request.getStartDate());
				response = scans.stream().filter(e -> e.getTimestamp().isAfter(start)).toList();
			} 
		}
		return response;

	}

}
