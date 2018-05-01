package pt.hospetall.web.product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Product")
public class Product {

	@Id
	private String code;
	private String name;
	private String type;
	private float quantity;
	private String quantityMeasurement;
	private int stock;
}
