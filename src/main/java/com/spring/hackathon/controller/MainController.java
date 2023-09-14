package com.spring.hackathon.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.hackathon.dto.FlightSearchRequestDTO;
import com.spring.hackathon.entity.Airline;
import com.spring.hackathon.entity.Airport;
import com.spring.hackathon.entity.Flight;
import com.spring.hackathon.service.MainService;



@RestController
@RequestMapping("/api/v1/flights")
public class MainController {
	
	
	@Autowired
	private MainService mainService;

    
	
	
	@PostMapping("/readAirline")
    public void readAndSaveAirline(@RequestParam String filePath) throws IOException {
        mainService.readJsonFileAndSaveToMongo(filePath);
    }
	
	@PostMapping("/readAirport")
    public ResponseEntity<String> readAndSaveAirport(@RequestParam String filePath) throws IOException {
        mainService.readAirPortFileAndSaveToMongo(filePath);
        return new ResponseEntity<>("Data Added...", HttpStatus.OK);
    }
	
	// triggering this method when this end-point will hit from postman
	@PostMapping("/importFlights")
    public void importFlights() {
        mainService.importFlightsFromJsonFile();
    }
	
	
	// fetching all airports
	@GetMapping("/fetchAirports")
	public ResponseEntity<List<Airport>> readAllAirport() {
		List<Airport> allAirports = mainService.readAllAirports();
		return new ResponseEntity<List<Airport>>(allAirports, HttpStatus.OK);
	}
	
	
	// fetching airports by iata code
	@GetMapping("/iatacode/{iataCode}")
	public ResponseEntity<List<Airport>> readAirportByIataCode(@PathVariable String iataCode) {
		
		List<Airport> code = mainService.readAirportByIataCode(iataCode);
		
		return new ResponseEntity<>(code, HttpStatus.OK);
	}
	
	// fetching airline by iata code
	@GetMapping("/fetchAirline/{code}")
	public ResponseEntity<Airline> fetchAirlineByIataCode(@PathVariable String code) {
		Airline airline = mainService.readAirlineByIataCode(code);
		
		return new ResponseEntity<>(airline, HttpStatus.OK);
	}
	
	public ResponseEntity<List<Airline>> fetchAllAirline() {
		List<Airline> readAllAirline = mainService.readAllAirline();
		return new ResponseEntity<>(readAllAirline, HttpStatus.OK);
	}
	
//	@PostMapping("/searchFlight")
//	public ResponseEntity<FlightResponseDTO> searchFlightsRunningOnRoute(@RequestBody FlightSearchRequestDTO flightSearchRequestDTO) throws Exception {
//		
//		 FlightResponseDTO responseDTO = mainService.searchFlight(flightSearchRequestDTO);
//		
//		return ResponseEntity.ok(responseDTO);
//	}
	
	@PostMapping("/searchFlight")
	public ResponseEntity<List<Flight>> searchFlight(@RequestBody FlightSearchRequestDTO flightSearchRequestDTO) throws Exception {
		
		List<Flight> flight = mainService.searchFlight(flightSearchRequestDTO);
		
		return new ResponseEntity<>(flight, HttpStatus.OK);
	}
	

}
