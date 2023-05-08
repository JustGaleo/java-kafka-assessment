package com.example.interview.service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.interview.entity.Scan;
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
	public List<Scan> getScanByDateRange(String type, String date1, String date2) {
		List<Scan> scans = (List<Scan>) scanRepository.findAll();
		List<Scan> response = null;
		if (date1 != null && date2 != null) {
			LocalDateTime start = LocalDateTime.parse(date1);
			LocalDateTime end = LocalDateTime.parse(date2);
			response = scans.stream().filter(e -> e.getTimestamp().isAfter(start) && e.getTimestamp().isBefore(end))
					.toList();
		} else {
			if (date1 == null) {
				LocalDateTime end = LocalDateTime.parse(date2);
				response = scans.stream().filter(e -> e.getTimestamp().isBefore(end)).toList();
			} else if (date2 == null) {
				LocalDateTime start = LocalDateTime.parse(date1);
				response = scans.stream().filter(e -> e.getTimestamp().isAfter(start)).toList();
			}
		}
		return response;

	}

}
