package edu.poly.Du_An_Tot_Ngiep.ServiceImpl;

import edu.poly.Du_An_Tot_Ngiep.Entity.InvoiceDetail;
import edu.poly.Du_An_Tot_Ngiep.Repository.InvoiceDetailRepository;
import edu.poly.Du_An_Tot_Ngiep.Service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Override
    public <S extends InvoiceDetail> S save(S entity) {
        return invoiceDetailRepository.save(entity);
    }

    @Override
    public List<InvoiceDetail> findDetailByInvoiceId(int id) {
        return invoiceDetailRepository.findDetailByInvoiceId(id);
    }

    @Override
    public InvoiceDetail findInvoiceDetail(int id) {
        return invoiceDetailRepository.findById(id);
    }

	@Override
	public List<InvoiceDetail> findAll() {
		return invoiceDetailRepository.findAll();
	}
    
}
