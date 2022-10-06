package edu.poly.Du_An_Tot_Ngiep.Service;

import java.util.List;
import java.util.Optional;

import edu.poly.Du_An_Tot_Ngiep.Entity.Imports;

public interface ImportService {

	void deleteById(Integer id);

	Iterable<Imports> findAll();

	Optional<Imports> findById(Integer id);

	<S extends Imports> S save(S entity);

	List<Imports> listImport();

	Imports findByIdImport(int idImport);

	Imports findQuatityProduct(int productid);
}
