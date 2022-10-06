package edu.poly.Du_An_Tot_Ngiep.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.Du_An_Tot_Ngiep.Repository.InvoiceRepository;
import edu.poly.Du_An_Tot_Ngiep.Service.StatisticalService;
import edu.poly.Du_An_Tot_Ngiep.models.StatisticalForMonthProjections;
import edu.poly.Du_An_Tot_Ngiep.models.StatisticalForProductProjections;
import edu.poly.Du_An_Tot_Ngiep.models.StatisticalForYearProjections;

@Service
public class StatisticalServiceImpl implements StatisticalService {
	
	private @Autowired InvoiceRepository invoiceRepository;

	@Override
	public List<StatisticalForMonthProjections> statisticalForMonth() {
		return invoiceRepository.statisticalForMonth();
	}

	@Override
	public List<StatisticalForYearProjections> statisticalForYear() {
		return invoiceRepository.statisticalForYear();
	}

	@Override
	public List<StatisticalForProductProjections> statisticalForProduct() {
		return invoiceRepository.statisticalForProduct();
	}

}
