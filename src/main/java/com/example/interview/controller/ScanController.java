package com.example.interview.controller;

import com.example.interview.entity.Scan;
import com.example.interview.models.DateRangeRequest;
import com.example.interview.service.ScanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/scan") // This means URL's start with /demo (after Application path)
public class ScanController {

	@Autowired
	private ScanService scanService;

	@GetMapping
	public ResponseEntity<List<Scan>> getAllScans() {
		List<Scan> scans = (List<Scan>) scanService.getAllScans();
		return new ResponseEntity<>(scans, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Scan> getScanById(@PathVariable("id") Long id) {
		Optional<Scan> scan = scanService.getScanById(id);
		if (scan.isPresent()) {
			return new ResponseEntity<>(scan.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/byDate")
	public ResponseEntity<List<Scan>> getScanByDateRange(@RequestBody DateRangeRequest request) {
		if (request.getType() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			if (request.getType().equals("scan")) {
				List<Scan> scans = (List<Scan>) scanService.getScanByDateRange(request);
				return new ResponseEntity<>(scans, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

	}
}
