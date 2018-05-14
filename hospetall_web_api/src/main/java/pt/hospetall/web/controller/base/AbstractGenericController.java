package pt.hospetall.web.controller.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.hospetall.web.model.base.BaseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public abstract class AbstractGenericController<T extends BaseEntity, R extends JpaRepository<T, Integer>> {

	private final R repo;
	private final Class<?> klass;

	protected AbstractGenericController(R repo, Class<?> klass) {
		this.repo = repo;
		this.klass = klass;
	}

	protected abstract Link[] getLinks(int id);

	@RequestMapping(method = RequestMethod.GET, path="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public ResponseEntity<Resource<T>> get(@PathVariable int id) throws Exception{
		Optional<T> res = repo.findById(id);
		Link self = linkTo(klass).slash(id).withSelfRel();

		if(res.isPresent()) {
			Resource<T> resource = new Resource<T>(res.get(), self);
			Link[] links = getLinks(id);
			if(links.length > 0)
				resource.add(links);

			return ResponseEntity.ok(resource);
		}
		throw new RuntimeException();
	}

	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public ResponseEntity<Resources<Resource<T>>> getAll(){
		List<Resource<T>> list = repo
				.findAll()
				.stream()
				.map((c) -> {
					Link self = linkTo(klass).slash(c.getId()).withSelfRel();
					return new Resource<T>(c, self);
				})
				.collect(Collectors.toList());

		Link self = linkTo(klass).withSelfRel();
		Resources<Resource<T>> resources = new Resources<>(list, self);
		return ResponseEntity.ok(resources);
	}
}
