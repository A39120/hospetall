package pt.hospetall.web.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NameBaseEntity extends BaseEntity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
