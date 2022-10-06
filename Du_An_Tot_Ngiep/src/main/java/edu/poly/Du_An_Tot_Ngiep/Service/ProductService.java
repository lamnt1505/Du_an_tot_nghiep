package edu.poly.Du_An_Tot_Ngiep.Service;

import java.util.List;
import java.util.Optional;

import edu.poly.Du_An_Tot_Ngiep.Entity.Product;

public interface ProductService {

	void deleteById(Integer id);

	Optional<Product> findById(Integer id);

	List<Product> findAll();

	<S extends Product> S save(S entity);

	List<Product> showListProductByIdCategory(int idCategory);

	List<Product> showProductDetaiList(int productId);

	List<Product> searchListProductByIdCategory(String name);

	List<Product> showListCategoryByIdCategory(int idCateogry);

	List<Product> showListProductForIndex();

	List<Product> listProduct();

	List<Product> findIdProduct(int idProduct);

	List<Product> listProductPriceAsc();

	List<Product> listProductPriceDesc();

	List<Product> listProductNewBest();

	List<Product> showListProductByIdCategoryFilter(int idCategory);

	Product findByIdProduct(int idProduct);

	List<Product> findByIdCategory(int id);

}
