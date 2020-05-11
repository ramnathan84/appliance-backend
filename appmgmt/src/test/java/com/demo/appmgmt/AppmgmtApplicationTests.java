package com.demo.appmgmt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.demo.appmgmt.model.Appliance;
import com.demo.appmgmt.payload.ApplianceRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AppmgmtApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void getAllAppliance() throws Exception {

		mvc.perform(get("/api/getAllAppliance").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].serialNumber").exists());
	}

	@Test
	public void getApplianceById() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/getApplianceById/{id}", 5).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(5));
	}

	@Test
	public void insertAppliance() throws Exception {

		ApplianceRequest appliance = new ApplianceRequest();
		appliance.setBrand("Mistubishi");
		appliance.setDateBought(new Date());
		appliance.setModel("T888");
		appliance.setSerialNumber("SN888");
		appliance.setStatus("NEW");
		String requestBody = new ObjectMapper().valueToTree(appliance).toString();

		mvc.perform(MockMvcRequestBuilders.post("/api/insertNewAppliance").content(requestBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}

	@Test
	public void checkApplianceExists() throws Exception {
		ApplianceRequest appliance = new ApplianceRequest();
		appliance.setBrand("GE");
		appliance.setDateBought(new Date());
		appliance.setModel("T1001");
		appliance.setSerialNumber("SN990");
		appliance.setStatus("NEW");

		String requestBody = new ObjectMapper().valueToTree(appliance).toString();

		mvc.perform(post("/api/insertNewAppliance").content(requestBody).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().is4xxClientError()).andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.message").value(
						"Appliance already exists. Not able to insert item with same Serial Number, Brand and Model."));

	}

	@Test
	public void updateAppliance() throws Exception {

		Appliance appliance = new Appliance();
		appliance.setBrand("GE");
		appliance.setDateBought(new Date());
		appliance.setModel("BB999");
		appliance.setSerialNumber("CC99");
		appliance.setStatus("SOLD");
		String requestBody = new ObjectMapper().valueToTree(appliance).toString();

		mvc.perform(MockMvcRequestBuilders.put("/api/updateAppliance/{id}", 15).content(requestBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.serialNumber").value("CC99"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.model").value("BB999"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SOLD"));
	}

	@Test
	public void deleteAppliance() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/api/deleteAppliance/{id}", 15)).andExpect(status().isOk());
	}

	@Test
	public void searchAppliance() throws Exception {
		ApplianceRequest appliance = new ApplianceRequest();
		appliance.setBrand("GE");

		String requestBody = new ObjectMapper().valueToTree(appliance).toString();

		mvc.perform(post("/api/searchAppliance").content(requestBody).contentType(MediaType.APPLICATION_JSON))
				.andDo(print());

	}

}
