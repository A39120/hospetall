package pt.hospetall.web.model;

public abstract class NameBaseEntity extends BaseEntity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
