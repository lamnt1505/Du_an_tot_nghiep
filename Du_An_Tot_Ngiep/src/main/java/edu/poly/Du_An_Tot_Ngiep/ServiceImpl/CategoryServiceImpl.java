package edu.poly.Du_An_Tot_Ngiep.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.Du_An_Tot_Ngiep.Entity.Category;
import edu.poly.Du_An_Tot_Ngiep.Repository.CategoryRepository;
import edu.poly.Du_An_Tot_Ngiep.Service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public <S extends Category> S save(S entity) {

		return categoryRepository.save(entity);
	}

	@Override
	public Category findCateById(int id) {
		return categoryRepository.findCateById(id);
	}

	@Override
	public List<Category> listCategory() {
		return categoryRepository.listCategory();
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public void updateCategory(Category category) {
		categoryRepository.updateCategory(category);
	}

	@Override
	public Optional<Category> findById(Integer id) {
		return categoryRepository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		categoryRepository.deleteById(id);
	}

}
