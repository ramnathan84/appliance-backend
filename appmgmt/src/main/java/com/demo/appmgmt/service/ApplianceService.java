package com.demo.appmgmt.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import com.demo.appmgmt.model.Appliance;
import com.demo.appmgmt.payload.ApplianceRequest;

@Service
public class ApplianceService {

	@PersistenceContext
	private EntityManager em;

	public List<Appliance> searchAppliance(ApplianceRequest searchedAppliance) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Appliance> cq = cb.createQuery(Appliance.class);
		Root<Appliance> quest = cq.from(Appliance.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchedAppliance.getBrand() != null) {
			predicates.add(cb.like(quest.get("brand"), "%" + searchedAppliance.getBrand() + "%"));
		}
		if (searchedAppliance.getSerialNumber() != null) {
			predicates.add(cb.like(quest.get("serialNumber"), "%" + searchedAppliance.getSerialNumber() + "%"));
		}
		if (searchedAppliance.getModel() != null) {
			predicates.add(cb.like(quest.get("model"), "%" + searchedAppliance.getModel() + "%"));
		}
		if (searchedAppliance.getStatus() != null) {
			predicates.add(cb.like(quest.get("status"), "%" + searchedAppliance.getStatus() + "%"));
		}

		if (searchedAppliance.getDateBought() != null) {
			predicates.add(cb.equal(quest.get("dateBought"), searchedAppliance.getDateBought()));
		}
		// other predicates

		cq.select(quest).where(predicates.toArray(new Predicate[] {}));
		List<Appliance> appliances = em.createQuery(cq).getResultList();
		return appliances;
	}

}
