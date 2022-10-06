package edu.poly.Du_An_Tot_Ngiep.Repository;

import edu.poly.Du_An_Tot_Ngiep.Entity.Invoice;
import edu.poly.Du_An_Tot_Ngiep.Entity.InvoiceDetail;
import edu.poly.Du_An_Tot_Ngiep.models.StatisticalForMonthProjections;
import edu.poly.Du_An_Tot_Ngiep.models.StatisticalForProductProjections;
import edu.poly.Du_An_Tot_Ngiep.models.StatisticalForYearProjections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	
	String STATISCAL_FOR_MONTH_QUERY = "SELECT MONTH(dateorders) AS orderMonth, " + 
			" YEAR(dateorders) AS orderYear, " + 
			" COUNT(i.invoice_id) as orderCount," + 
			" SUM(total) AS total," + 
			" MIN(total) AS minTotal," + 
			" MAX(total) AS maxTotal FROM invoice i" + 
			" Where i.status='hoàn thành' "+
			" GROUP BY MONTH(dateorders), YEAR(dateorders)";
	
	String STATISCAL_FOR_YEAR_QUERY = "SELECT" + 
			" YEAR(dateorders) AS orderYear, " + 
			" COUNT(i.invoice_id) as orderCount," + 
			" SUM(total) AS total," + 
			" MIN(total) AS minTotal," + 
			" MAX(total) AS maxTotal" + 
			" FROM invoice i" + 
			" Where i.status='hoàn thành' "+
			" GROUP BY YEAR(dateorders)";
	
	String STATISCAL_FOR_PRODUCT_QUERY = "SELECT p.id_product AS id, p.name AS name,  " + 
			"	(CASE  " + 
			"		WHEN SUM(detail.amount) IS NULL THEN 0 " + 
			"		ELSE SUM(detail.amount) " + 
			"		END " + 
			"	) as soLuongBanDuoc, " + 
			"	(CASE  " + 
			"		WHEN SUM(detail.price * detail.amount) IS NULL THEN 0 " + 
			"		ELSE SUM(detail.price * detail.amount) " + 
			"		END " + 
			"	) as tongTienThuDuoc " + 
			"	FROM product p " + 
			"	LEFT JOIN invoice_detail detail " + 
			"		ON p.id_product = detail.id_product " + 
			"	FULL JOIN invoice " + 
			"		ON invoice.invoice_id = detail.invoice_id " + 
			"	Where invoice.status = 'hoàn thành'" +
			"	GROUP BY p.id_product, p.name ";
	
    
    
    @Query(value = STATISCAL_FOR_MONTH_QUERY, nativeQuery = true)
    List<StatisticalForMonthProjections> statisticalForMonth();
    
    @Query(value = STATISCAL_FOR_YEAR_QUERY, nativeQuery = true)
    List<StatisticalForYearProjections> statisticalForYear();
    
    @Query(value = STATISCAL_FOR_PRODUCT_QUERY, nativeQuery = true)
    List<StatisticalForProductProjections> statisticalForProduct();
    
    
    @Query(value = "select * from Invoice where invoice_id = ?", nativeQuery = true)
    List<Invoice> findListInvoiceById(int idInvoice);

    @Query(value = "select * from InvoiceDetail where invoice_id = ?", nativeQuery = true)
    List<InvoiceDetail> findListDetailByInvoiceId(int idInvoice);

    @Query(value = "select * from Invoice where invoice_id = ?", nativeQuery = true)
    public Invoice findByIdInvoice(int idInvoice);

    @Query(value = "select * from Invoice where customer_id = ?", nativeQuery = true)
    List<Invoice>  findInvoiceByUser(int iduser);

}
