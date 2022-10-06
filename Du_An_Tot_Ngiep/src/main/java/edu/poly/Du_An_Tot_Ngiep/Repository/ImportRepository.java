package edu.poly.Du_An_Tot_Ngiep.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.Du_An_Tot_Ngiep.Entity.Imports;

@Repository
public interface ImportRepository extends JpaRepository<Imports, Integer> {

	@Query(value = "select * from imports", nativeQuery = true)
	List<Imports> listImport();

	@Query(value = "select * from imports where id_import = ?", nativeQuery = true)
	public Imports findByIdImport(int idImport);
	
	@Query(value = "select imports.* from imports where imports.id_product=? ", nativeQuery = true)
	Imports findQuatityProduct(int idProduct);

	@Query(value = "select * from imports where id_product = ?", nativeQuery = true)
	List<Imports> showListImportByIdProduct(int idProduct);

	@Query(value = "select * from imports where id_user = ?", nativeQuery = true)
	List<Imports> showListImportByIdUser(String IdUser);

	@Query(value = "select * from imports where  name like %?% ", nativeQuery = true)
	List<Imports> searchListImportByIdUser(String IdUser);

	@Query(value = "select * from imports where  name like %?% ", nativeQuery = true)
	List<Imports> searchListImportByIdProduct(String idProduct);
}
	