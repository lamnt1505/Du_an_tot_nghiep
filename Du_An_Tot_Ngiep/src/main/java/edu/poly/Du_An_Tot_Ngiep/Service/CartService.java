package edu.poly.Du_An_Tot_Ngiep.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import edu.poly.Du_An_Tot_Ngiep.Entity.Product;
import edu.poly.Du_An_Tot_Ngiep.Repository.ProductRepository;

@SessionScope
@Service
public class CartService {

	// get from DB
	@Autowired
	ProductRepository dao;
	List<Product> items = new ArrayList<Product>();

	public void add(Integer id) {
		for (Product item : items) {
			if (item.getIdProduct() == id) {
//				item.setQuantity(item.getQuantity() + 1);
				return;
			}
		}
		Product p = dao.findByIdProduct(id);
//		p.setQuantity(1);
		items.add(p);

	}

	public void remove(Integer id) {
		for (Product item : items) {
			if (item.getIdProduct() == id) {
				items.remove(item);
				return;
			}
		}
	}

	public void update(Integer id, int qty) {
		for (Product item : items) {
			if (item.getIdProduct() == id) {
//				item.setQuantity(qty);
				return;
			}
		}
	}

	public void clear() {
		items.clear();
	}

	// lay cac san pham ra
	public List<Product> getItems() {
		return items;
	}

	// lay tong
	public int getCount() {
		int count = 0;
		for (Product item : items) {
//			count += item.getQuantity();
		}
		return count;
	}

	public double getAmount() {
		double amount = 0;
		for (Product item : items) {
//			amount += item.getQuantity() * item.getPrice();
		}
		return amount;
	}
}
