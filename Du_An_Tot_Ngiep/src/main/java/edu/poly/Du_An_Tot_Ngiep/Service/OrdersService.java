package edu.poly.Du_An_Tot_Ngiep.Service;

import edu.poly.Du_An_Tot_Ngiep.Entity.Invoice;
import edu.poly.Du_An_Tot_Ngiep.Entity.InvoiceDetail;

import java.util.List;

public interface OrdersService {

	Invoice findInvoiceByDetail(int id);


	List<Invoice> listInvoiceByUser(int iduser);

	List<Invoice> listInvoice();

	List<InvoiceDetail> listDetailByInvoiceId(int id);

	<S extends Invoice> S save(S entity);


	Invoice findByIdInvoice(int idInvoice);


}
