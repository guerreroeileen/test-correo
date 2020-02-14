package co.com.novaip.tecuido.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.autodiscover.exception.AutodiscoverLocalException;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

@RestController
public class TestCorreo {

	@GetMapping("/correo")
	public String probarCorreo() throws Exception {
		ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
		ExchangeCredentials credentials = new WebCredentials("pruebanovamedia", "novamedia123");
		service.setCredentials(credentials);
		//service.setEnableScpLookup(true);
		//service.setUrl(new java.net.URI("https://webmail.travelpro.com"));
		
		service.autodiscoverUrl("pruebanovamedia@outlook.com", new IAutodiscoverRedirectionUrl() {
			@Override
			public boolean autodiscoverRedirectionUrlValidationCallback(String url) throws AutodiscoverLocalException {
				return url.toLowerCase().startsWith("https://");
			}
		});
		
		EmailMessage msg = new EmailMessage(service);
        msg.setSubject("Hello world!");
        msg.setBody(MessageBody.getMessageBodyFromText("Sent using the EWS Java API."));
        msg.getToRecipients().add("eileenguerrerogomez@outlook.es");
        msg.send();
        
        
        

		return service.toString();
	}

}
