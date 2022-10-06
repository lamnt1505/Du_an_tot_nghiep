package edu.poly.Du_An_Tot_Ngiep.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.Du_An_Tot_Ngiep.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	@Query(value = "select * from category", nativeQuery = true)
	List<Category> listCategory();
	
	@Modifying
	@Query(value = "update category  set name = 1 where id_category = 1 ", nativeQuery = true)
	public void  updateCategory(Category category);
	
	@Query(value = "select * from category where id_category = ?" , nativeQuery =  true)
	List<Category> findCategoryById(int  id);
	
	@Query(value = "select * from category where id_category = ?" , nativeQuery =  true)
	public Category findCateById(int  id);
}
