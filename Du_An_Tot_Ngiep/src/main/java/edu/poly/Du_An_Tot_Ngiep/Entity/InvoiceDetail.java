package edu.poly.Du_An_Tot_Ngiep.Entity;

import javax.persistence.*;

@Entity
@Table(name = "InvoiceDetail")
public class InvoiceDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int detailId;

	@ManyToOne
	@JoinColumn(name = "invoiceId", insertable = true, updatable = true)
	private Invoice invoiceId;

	@OneToOne
	@JoinColumn(name = "idProduct", insertable = true, updatable = true)
	private Product product;

	@Column(name = "amount")
	private int amount;

	// add price for orderDetail
	@Column(name = "price")
	private double price;

	public InvoiceDetail(int detailId, Invoice invoiceId, Product product, int amount, double price) {
		super();
		this.detailId = detailId;
		this.invoiceId = invoiceId;
		this.product = product;
		this.amount = amount;
		this.price = price;
	}

	public InvoiceDetail() {
		super();
	}

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public Invoice getInvoiceId() {
		return invoiceId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setInvoiceId(Invoice invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
