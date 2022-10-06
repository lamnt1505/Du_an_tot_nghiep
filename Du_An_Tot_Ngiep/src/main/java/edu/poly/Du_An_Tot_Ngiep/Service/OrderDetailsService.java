package edu.poly.Du_An_Tot_Ngiep.Service;

import edu.poly.Du_An_Tot_Ngiep.Entity.InvoiceDetail;

import java.util.List;

public interface OrderDetailsService {

    <S extends InvoiceDetail> S save(S entity);

    List<InvoiceDetail> findDetailByInvoiceId(int id);

    InvoiceDetail findInvoiceDetail(int id);

	List<InvoiceDetail> findAll();
}
