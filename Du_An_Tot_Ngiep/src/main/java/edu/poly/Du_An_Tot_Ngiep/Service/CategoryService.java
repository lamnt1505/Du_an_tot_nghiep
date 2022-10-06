package edu.poly.Du_An_Tot_Ngiep.Service;

import java.util.List;
import java.util.Optional;

import edu.poly.Du_An_Tot_Ngiep.Entity.Category;

public interface CategoryService {

	void deleteById(Integer id);

	Optional<Category> findById(Integer id);

	List<Category> findAll();

	<S extends Category> S save(S entity);

	List<Category> listCategory();

	void updateCategory(Category category);

	Category findCateById(int id);

}
