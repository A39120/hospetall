package pt.hospetall.web.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {

	private final ClientRepository clientRepository;

	@Autowired
	ClientController(ClientRepository clientRepository){
			this.clientRepository = clientRepository;
	}

	
	@RequestMapping(method = RequestMethod.GET, path = "/")
	Iterable<Client> getClientList(
		@RequestParam(value="lastName", required=false) String lastName,
		@RequestParam(value="telephone", required=false) String phone,
		@RequestParam(value="email", required=false) String email,
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
	
		return (List<Client>) clientRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	Optional<Client> getClient(@PathVariable int id){
			return clientRepository.findById(id);
	}


	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody String input){
			Client client = new Client();
			//client.setName(input);
			clientRepository.save(client);

			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand().toUri();

			return ResponseEntity.created(location).build();
	}
}
