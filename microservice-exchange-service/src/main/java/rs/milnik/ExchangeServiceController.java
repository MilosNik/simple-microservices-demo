package rs.milnik;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ExchangeServiceController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ExchangeServiceProxy proxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public ExchangeServiceBean convertCurrency
		(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<ExchangeServiceBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", ExchangeServiceBean.class,
				uriVariables);
		
		ExchangeServiceBean response = responseEntity.getBody();
		
		return new ExchangeServiceBean(response.getId(), from, to, response.getConversionRate(),
				quantity, quantity.multiply(response.getConversionRate()), response.getPort());
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public ExchangeServiceBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		ExchangeServiceBean response = proxy.retrieveExchangeValue(from, to);
		
		logger.info("{}", response);
		
		return new ExchangeServiceBean(response.getId(), from, to, response.getConversionRate(), quantity,
				quantity.multiply(response.getConversionRate()), response.getPort());
	}
	
}
