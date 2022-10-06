package edu.poly.Du_An_Tot_Ngiep.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "imports")
public class Imports {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idImport;

	@Column(columnDefinition = "nvarchar(150)")
	private String users;

	private int quantity;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "createDate", updatable = false)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "updateDate", updatable = true)
	private Date updateDate;

	@OneToOne
	@JoinColumn(name = "idProduct", insertable = true, updatable = true)
	private Product product;

	public Imports() {
		super();
	}

	public Imports(int idImport, String users, Product product, int quantity, Date createDate, Date updateDate) {
		super();
		this.idImport = idImport;
		this.users = users;
		this.product = product;
		this.quantity = quantity;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public int getIdImport() {
		return idImport;
	}

	public void setIdImport(int idImport) {
		this.idImport = idImport;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
