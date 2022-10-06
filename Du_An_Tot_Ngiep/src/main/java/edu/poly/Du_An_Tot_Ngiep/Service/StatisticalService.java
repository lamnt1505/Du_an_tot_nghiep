package edu.poly.Du_An_Tot_Ngiep.Service;

import java.util.List;

import edu.poly.Du_An_Tot_Ngiep.models.StatisticalForMonthProjections;
import edu.poly.Du_An_Tot_Ngiep.models.StatisticalForProductProjections;
import edu.poly.Du_An_Tot_Ngiep.models.StatisticalForYearProjections;

public interface StatisticalService {

	List<StatisticalForMonthProjections> statisticalForMonth();

	List<StatisticalForYearProjections> statisticalForYear();

	List<StatisticalForProductProjections> statisticalForProduct();
}
