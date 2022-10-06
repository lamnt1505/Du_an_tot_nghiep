package edu.poly.Du_An_Tot_Ngiep.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.Du_An_Tot_Ngiep.Entity.Product;
import edu.poly.Du_An_Tot_Ngiep.Repository.ProductRepository;
import edu.poly.Du_An_Tot_Ngiep.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public <S extends Product> S save(S entity) {
		return productRepository.save(entity);
	}

	@Override
	public List<Product> listProductNewBest() {
		return productRepository.listProductNewBest();
	}

	@Override
	public List<Product> showListProductByIdCategoryFilter(int idCategory) {
		return productRepository.showListProductByIdCategoryFilter(idCategory);
	}

	@Override
	public Product findByIdProduct(int idProduct) {
		return productRepository.findByIdProduct(idProduct);
	}

	@Override
	public List<Product> listProductPriceDesc() {
		return productRepository.listProductPriceDesc();
	}

	@Override
	public List<Product> findByIdCategory(int id) {
		return productRepository.findByIdCategory(id);
	}

	@Override
	public List<Product> listProductPriceAsc() {
		return productRepository.listProductPriceAsc();
	}

	@Override
	public List<Product> findIdProduct(int idProduct) {
		return productRepository.findIdProduct(idProduct);
	}

	@Override
	public List<Product> listProduct() {
		return productRepository.listProduct();
	}

	@Override
	public List<Product> showListCategoryByIdCategory(int idCateogry) {
		return productRepository.showListCategoryByIdCategory(idCateogry);
	}

	@Override
	public List<Product> showListProductForIndex() {
		return productRepository.showListProductForIndex();
	}

	@Override
	public List<Product> showProductDetaiList(int productId) {
		return productRepository.showProductDetaiList(productId);
	}

	@Override
	public List<Product> searchListProductByIdCategory(String name) {
		return productRepository.searchListProductByIdCategory(name);
	}

	@Override
	public List<Product> showListProductByIdCategory(int idCategory) {
		return productRepository.showListProductByIdCategory(idCategory);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		productRepository.deleteById(id);
	}

}
