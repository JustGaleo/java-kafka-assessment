package com.example.interview.functionality;

import java.time.LocalDateTime;
import java.util.List;

import com.example.interview.entity.Scan;
import com.example.interview.models.DateRangeRequest;

public class DateValidation {
	
	public List<Scan> getDateValidation(List<Scan> scans, DateRangeRequest request){
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
