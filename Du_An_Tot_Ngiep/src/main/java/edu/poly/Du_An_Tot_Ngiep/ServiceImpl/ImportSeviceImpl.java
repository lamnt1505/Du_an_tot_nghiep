package edu.poly.Du_An_Tot_Ngiep.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.Du_An_Tot_Ngiep.Entity.Imports;
import edu.poly.Du_An_Tot_Ngiep.Repository.ImportRepository;
import edu.poly.Du_An_Tot_Ngiep.Service.ImportService;

@Service
public class ImportSeviceImpl implements ImportService {

	@Autowired
	private ImportRepository importRepository;

	@Override
	public void deleteById(Integer id) {
		importRepository.deleteById(id);
	}

	@Override
	public List<Imports> listImport() {
		return importRepository.listImport();
	}

	@Override
	public Imports findByIdImport(int idImport) {
		return importRepository.findByIdImport(idImport);
	}

	@Override
	public Imports findQuatityProduct(int productid) {
		return importRepository.findQuatityProduct(productid);
	}

	@Override
	public Iterable<Imports> findAll() {
		return importRepository.findAll();
	}

	@Override
	public Optional<Imports> findById(Integer id) {
		return importRepository.findById(id);
	}

	@Override
	public <S extends Imports> S save(S entity) {
		return importRepository.save(entity);
	}

}
