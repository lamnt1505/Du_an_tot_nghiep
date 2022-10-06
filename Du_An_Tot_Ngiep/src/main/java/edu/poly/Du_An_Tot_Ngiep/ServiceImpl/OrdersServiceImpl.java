	package edu.poly.Du_An_Tot_Ngiep.ServiceImpl;

import edu.poly.Du_An_Tot_Ngiep.Entity.Invoice;
import edu.poly.Du_An_Tot_Ngiep.Entity.InvoiceDetail;
import edu.poly.Du_An_Tot_Ngiep.Repository.InvoiceRepository;
import edu.poly.Du_An_Tot_Ngiep.Service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private InvoiceRepository invoiceRepository;


    @Override
    public <S extends Invoice> S save(S entity) {
        return invoiceRepository.save(entity);
    }

	@Override
	public Invoice findByIdInvoice(int idInvoice) {
		return invoiceRepository.findByIdInvoice(idInvoice);
	}

	@Override
    public List<InvoiceDetail> listDetailByInvoiceId(int id) {
        return invoiceRepository.findListDetailByInvoiceId(id);
    }

    @Override
    public List<Invoice> listInvoice() {
        return invoiceRepository.findAll();
    }

    @Override
    public List<Invoice> listInvoiceByUser(int iduser) {
        return invoiceRepository.findInvoiceByUser(iduser);
    }

  
    @Override
    public Invoice findInvoiceByDetail(int id) {
        return invoiceRepository.findByIdInvoice(id);
    }

}
