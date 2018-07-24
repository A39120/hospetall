package pt.hospetall.web.controller.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pt.hospetall.web.error.ApiError;
import pt.hospetall.web.model.Client;
import pt.hospetall.web.model.base.BaseEntity;
import pt.hospetall.web.resource.ClientResource;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@CrossOrigin
public abstract class AbstractGenericController<T extends BaseEntity,
		R extends JpaRepository<T, Integer>,
		U extends ResourceSupport> {

	protected final R repo;
	private final Class<?> klass;

	protected AbstractGenericController(R repo, Class<?> klass) {
		this.repo = repo;
		this.klass = klass;
	}

	protected U getResource(T obj) {
		Link self = linkTo(klass).slash(obj.getId()).withSelfRel();
		U resource = (U) new Resource<>(obj, self);
		return resource;

	}

	protected Resources<U> getResources(List<T> obj, Link self) {
		List<U> resource = obj.stream()
				.map(this::getResource)
				.collect(Collectors.toList());

		return new Resources<>(resource, self);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public U get(@PathVariable int id) {
		Optional<T> res = repo.findById(id);

		if (res.isPresent()) {
			T obj = res.get();
			return getResource(obj);
		}
		throw new RuntimeException();
	}

	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public Resources<U> getAll() {
		List<T> list = repo.findAll();
		Link self = linkTo(klass).withSelfRel();

		return getResources(list, self);
	}


	@PostMapping
	//public ResponseEntity<?> add(@RequestBody T entity) {
	public ResponseEntity<?> add(@Valid T entity, BindingResult bindingResult) {

		return ResponseEntity.created(
				URI.create(
						getResource(repo.save(entity)).getLink("self").getHref())).build();





	/*	if(checkIfExists(entity).isPresent()) return ResponseEntity.badRequest().build();

		if (bindingResult.hasErrors()) {
		//	System.out.print(bindingResult.getGlobalError());
		//	return ResponseEntity.badRequest().body(entity);
			for (Object object : bindingResult.getAllErrors()) {
				if(object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;

					System.out.println(fieldError.getCode());
				}

				if(object instanceof ObjectError) {
					ObjectError objectError = (ObjectError) object;

					System.out.println(objectError.getCode());
				}
			}
			return ResponseEntity.badRequest().body(entity);
		}*/
		//	if(!bindingResult.hasErrors())
		//if(checkIfExists(entity).isPresent()) return ResponseEntity.badRequest().build();
		//	return ResponseEntity.created(
		//			URI.create(
		//					getResource(repo.save(entity)).getLink("self").getHref())).build();
	}


	public abstract Optional<T> checkIfExists(T entity);

}

