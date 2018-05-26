package pt.hospetall.web.controller.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.hospetall.web.model.Client;
import pt.hospetall.web.model.base.BaseEntity;
import pt.hospetall.web.resource.ClientResource;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public abstract class AbstractGenericController<T extends BaseEntity,
		R extends JpaRepository<T, Integer>,
		U extends Resource<T>> {

	protected final R repo;
	private final Class<?> klass;

	protected AbstractGenericController(R repo, Class<?> klass) {
		this.repo = repo;
		this.klass = klass;
	}

	protected U getResource(T obj){
		Link self = linkTo(klass).slash(obj.getId()).withSelfRel();
		U resource = (U) new Resource<>(obj, self);
		return resource;

	}
	protected Resources<U> getResources(List<T> obj, Link self){
		List<U> resource = obj.stream()
				.map(this::getResource)
				.collect(Collectors.toList());

		return new Resources<>(resource, self);
	}

	@RequestMapping(method = RequestMethod.GET, path= "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public U get(@PathVariable int id) {
		Optional<T> res = repo.findById(id);

		if(res.isPresent()) {
			T obj = res.get();
			return getResource(obj);
		}
		throw new RuntimeException();
	}

	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public Resources<U> getAll(){
		List<T> list = repo.findAll();
		Link self = linkTo(klass).withSelfRel();

		return getResources(list, self);
	}
/*
	@PostMapping
	public ResponseEntity<?> add(@RequestBody T entity) {
		return checkConstraints(entity).map(
				res -> ResponseEntity.created(URI.create(getResource(repo.save(res)).getLink("self").getHref())).build())
				.orElse(ResponseEntity.noContent().build());
	}

	abstract Optional<T> checkConstraints(T entity);
*/
}
