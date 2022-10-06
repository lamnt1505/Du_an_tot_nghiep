package edu.poly.Du_An_Tot_Ngiep.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Invoice")
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int invoiceId;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dateorders")
	private Date dateorders;

	@Column(name = "status", columnDefinition = "nvarchar(50)")
	private String status;

	@ManyToOne
	@JoinColumn(name = "customerId", insertable = true, updatable = true)
	private Customer customerId;

	@Column(name = "total")
	private double total;

	@JsonIgnore
	@OneToMany(mappedBy = "invoiceId")
	private Set<InvoiceDetail> details;

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Date getDateorders() {
		return dateorders;
	}

	public void setDateorders(Date dateorders) {
		this.dateorders = dateorders;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Customer getVendor() {
		return customerId;
	}

	public void setVendor(Customer user) {
		this.customerId = user;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Invoice() {
		super();
	}

	public void setDetails(Set<InvoiceDetail> details) {
		this.details = details;
	}

	public Set<InvoiceDetail> getDetails() {
		return details;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}


	public Invoice(@NotNull Date dateorders, String status, @NotNull Customer customerId, double total,
			String description) {
		this.dateorders = dateorders;
		this.status = status;
		this.customerId = customerId;
		this.total = total;
	}
}
