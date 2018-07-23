package pt.hospetall.web.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Product {

	@Id
	private String code;
	private String name;
	private String type;
	private float quantity;
	private String quantityMeasurement;
	private int stock;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public String getQuantityMeasurement() {
		return quantityMeasurement;
	}

	public void setQuantityMeasurement(String quantityMeasurement) {
		this.quantityMeasurement = quantityMeasurement;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
