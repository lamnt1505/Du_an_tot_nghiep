package edu.poly.Du_An_Tot_Ngiep.Entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCategory")
	private int idCategory;

	@Column(name = "name", columnDefinition = "nvarchar(150)")
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	private Set<Product> product;

	public Category() {
		super();
	}

	public Category(int idCategory, String name, Set<Product> product) {
		super();
		this.idCategory = idCategory;
		this.name = name;
		this.product = product;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Product> getProduct() {
		return product;
	}

	public void setProduct(Set<Product> product) {
		this.product = product;
	}

}
