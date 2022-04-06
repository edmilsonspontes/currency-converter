package com.elektron.currency;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.elektron.currency.dto.ConverterDTO;
import com.elektron.currency.request.TransactionRequest;
import com.elektron.currency.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ConverterTests {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionService transactionService;

	@Test
	void contextLoads() {
	}
	
	/**
	 * Tests if the transaction executed successfully
	 * @throws Exception
	 */
	@Test void convertTrueSucessTest() throws Exception {
		TransactionRequest request = new TransactionRequest(1l, "BRL", "USD");
		
		mockMvc.perform(post("/converter")
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(request)))
		        .andExpect(status().isOk());
		
	    ConverterDTO converterDTO = transactionService.converter(request.getUserId(), request.getSourceCurrency(), request.getDestinationCurrency());

	    Assertions.assertEquals(converterDTO.isSuccess(), true);
	    Assertions.assertEquals(converterDTO.getError() == null, true);
	    Assertions.assertEquals(converterDTO.getTransaction() != null, true);
	    Assertions.assertEquals(converterDTO.getTransaction().getUserId(), request.getUserId());
	    Assertions.assertEquals(converterDTO.getTransaction().getSourceCurrency(), request.getSourceCurrency());
	    Assertions.assertEquals(converterDTO.getTransaction().getDestinationCurrency(), request.getDestinationCurrency());
	    Assertions.assertEquals(converterDTO.getTransaction().getConversionRate() != null, true);
	    Assertions.assertEquals(converterDTO.getTransaction().getDateTime() != null, true);
	    Assertions.assertEquals(converterDTO.getTransaction().getSourceValue() != null, true);
	    Assertions.assertEquals(converterDTO.getTransaction().getDestinationValue() != null, true);
	}
	
	/**
	 * Tests if the transaction failed
	 * @throws Exception
	 */
	@Test void convertFalseSucessTest() throws Exception {
		TransactionRequest request = new TransactionRequest(1l, "XXX", "USD");
		
		mockMvc.perform(post("/converter")
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(request)))
		        .andExpect(status().isBadRequest());
		
	    ConverterDTO converterDTO = transactionService.converter(request.getUserId(), request.getSourceCurrency(), request.getDestinationCurrency());

	    Assertions.assertEquals(converterDTO.isSuccess(), false);
	    Assertions.assertEquals(converterDTO.getError() == null, false);
	    Assertions.assertEquals(converterDTO.getTransaction() != null, false);
	    Assertions.assertEquals((Boolean)converterDTO.getError().get("success") == null, false);
	    Assertions.assertEquals((Integer)converterDTO.getError().get("code") == null, false);
	    Assertions.assertEquals((String)converterDTO.getError().get("info") == null, false);
	}

}
