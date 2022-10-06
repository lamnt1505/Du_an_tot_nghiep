package edu.poly.Du_An_Tot_Ngiep.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.Du_An_Tot_Ngiep.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query(value = "select top 8 * from product where id_category = ?", nativeQuery = true)
	List<Product> showListProductByIdCategory(int idCategory);

	@Query(value = "select * from product where id_product = ?", nativeQuery = true)
	List<Product> showProductDetaiList(int productId);

	@Query(value = "select * from product where  name like %?% ", nativeQuery = true)
	List<Product> searchListProductByIdCategory(String name);

	@Query(value = "select * from product where id_category like %?% ", nativeQuery = true)
	List<Product> showListCategoryByIdCategory(int idCateogry);

	@Query(value = " select top 8 * from product order by id_product desc ", nativeQuery = true)
	List<Product> showListProductForIndex();

	@Query(value = "select * from product", nativeQuery = true)
	List<Product> listProduct();

	@Query(value = " select * from product order by id_product desc", nativeQuery = true)
	List<Product> listProductNewBest();

	@Query(value = "select * from product order by price desc", nativeQuery = true)
	List<Product> listProductPriceDesc();

	@Query(value = "select * from product order by price asc", nativeQuery = true)
	List<Product> listProductPriceAsc();

	@Query(value = "select * from product where id_category = ?", nativeQuery = true)
	List<Product> showListProductByIdCategoryFilter(int idCategory);

	@Query(value = "select * from product where id_product = ?", nativeQuery = true)
	List<Product> findIdProduct(int idProduct);
	
	@Query(value = "select * from product where id_product = ?", nativeQuery = true)
	public Product findByIdProduct(int idProduct);
	
	@Query(value = "select top 4 * from product where id_category = ?", nativeQuery = true)
	public List<Product> findByIdCategory(int id);
}
