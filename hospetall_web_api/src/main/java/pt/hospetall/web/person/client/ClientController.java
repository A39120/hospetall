package pt.hospetall.web.person.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/client")
public class ClientController {

	private final ClientRepository clientRepository;

	@Autowired
	public ClientController(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Client> getClients(){
		return clientRepository.findAll();
	}
}
