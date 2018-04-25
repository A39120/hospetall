package pt.hospetall.web.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

	private final ClientRepository clientRepository;

	@Autowired
	ClientController(ClientRepository clientRepository){
			this.clientRepository = clientRepository;
	}

	
	@RequestMapping(method = RequestMethod.GET, path = "/")
	List<Client> getClientList(
		@RequestParam(value="lastName", required=false) String lastName
		@RequestParam(value="telephone", required=false) String phone
		@RequestParam(value="email", required=false) String email
		@RequestParam(value="address", required=false) String address
	){
		if(lastName != null) 
			return clientRepository.findByFamilyName(lastName);

		if(phone != null)
			return clientRepository.findByTelephone(phone);

		if(email != null)
			return clientRepository.findByEmail(email);

		if(address != null)
			return clientRepository.findByAddress(address);
	
		return clientRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	Client getClient(@PathVariable int id){
			return clientRepository.getOne(id);
	}


	List<Client> getClientByLastName(
			){ 
		return clientRepository.findByFamilyName(lastName);

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody String input){
			Client client = new Client();
			client.setName(input);
			clientRepository.save(client);

			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand().toUri();

			return ResponseEntity.created(location).build();
	}
}
