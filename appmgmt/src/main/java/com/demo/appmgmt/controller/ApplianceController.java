package com.demo.appmgmt.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.appmgmt.exception.ResourceNotFoundException;
import com.demo.appmgmt.model.Appliance;
import com.demo.appmgmt.payload.ApiResponse;
import com.demo.appmgmt.payload.ApplianceRequest;
import com.demo.appmgmt.repository.ApplianceRepository;
import com.demo.appmgmt.service.ApplianceService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApplianceController {

	@Autowired
	private ApplianceRepository applianceRepository;

	@Autowired
	private ApplianceService applianceService;

	@GetMapping("/getAllAppliance")
	public List<Appliance> getAllAppliance() {
		return applianceRepository.findAll();
	}

	@GetMapping("/getApplianceById/{id}")
	ResponseEntity<?> getGroup(@PathVariable Long id) {
		Optional<Appliance> appliance = applianceRepository.findById(id);
		return appliance.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/insertNewAppliance")
	public ResponseEntity<?> registerUser(@Valid @RequestBody ApplianceRequest applianceRequest)
			throws URISyntaxException {

		if (applianceRepository.existsBySerialNumberAndBrandAndModel(applianceRequest.getSerialNumber(),
				applianceRequest.getBrand(), applianceRequest.getModel())) {
			return new ResponseEntity(new ApiResponse(false,
					"Appliance already exists. Not able to insert item with same Serial Number, Brand and Model."),
					HttpStatus.BAD_REQUEST);
		}

		Appliance appliance = new Appliance();
		appliance.setBrand(applianceRequest.getBrand());
		appliance.setModel(applianceRequest.getModel());
		appliance.setSerialNumber(applianceRequest.getSerialNumber());
		appliance.setDateBought(applianceRequest.getDateBought());
		appliance.setStatus(applianceRequest.getStatus());

		Appliance insertedAppliance = applianceRepository.save(appliance);

		return ResponseEntity.created(new URI("/updateAppliance/" + insertedAppliance.getId())).body(insertedAppliance);
	}

	@PostMapping("/searchAppliance")
	public List<Appliance> getAppliance(@RequestBody ApplianceRequest applianceRequest)
			throws ResourceNotFoundException {

		List<Appliance> searchedAppliance = applianceService.searchAppliance(applianceRequest);

		if (searchedAppliance.isEmpty()) {
			throw new ResourceNotFoundException("Sorry, no matches found for your search term!");
		}

		return searchedAppliance;

	}

	@PutMapping("/updateAppliance/{id}")
	public ResponseEntity<Appliance> updateAppliance(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Appliance appliance) throws ResourceNotFoundException {

		Appliance applianceExists = applianceRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("The following appliance is not found : " + appliance.toString()));

		applianceExists.setBrand(appliance.getBrand());
		applianceExists.setModel(appliance.getModel());
		applianceExists.setSerialNumber(appliance.getSerialNumber());
		applianceExists.setDateBought(appliance.getDateBought());
		applianceExists.setStatus(appliance.getStatus());

		Appliance updatedAppliance = applianceRepository.save(applianceExists);

		return ResponseEntity.ok().body(updatedAppliance);
	}

	@DeleteMapping("/deleteAppliance/{id}")
	public ResponseEntity<Appliance> deleteAppliance(@PathVariable(value = "id") Long id) throws Exception {

		Appliance applianceFound = applianceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Appliance not found!"));

		applianceRepository.delete(applianceFound);

		return new ResponseEntity(new ApiResponse(true, "Appliance successfully deleted!"), HttpStatus.OK);
	}

}
