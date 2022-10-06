package edu.poly.Du_An_Tot_Ngiep.Repository;

import edu.poly.Du_An_Tot_Ngiep.Entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {

    @Query(value = "select * from Invoice_detail where invoice_id = ?", nativeQuery = true)
    List<InvoiceDetail> findDetailByInvoiceId(int id);

    @Query(value = "select * from Invoice_detail where detail_id = ? ", nativeQuery = true)
    public InvoiceDetail findById(int idInvoice);

}
