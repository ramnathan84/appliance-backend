package com.demo.appmgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.appmgmt.model.Appliance;

@Repository
public interface ApplianceRepository extends JpaRepository<Appliance, Long> {

	Boolean existsBySerialNumberAndBrandAndModel(String serialNumber, String brand, String model);

}
