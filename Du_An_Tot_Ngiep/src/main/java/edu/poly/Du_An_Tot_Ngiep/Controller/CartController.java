package edu.poly.Du_An_Tot_Ngiep.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.Du_An_Tot_Ngiep.Entity.Customer;
import edu.poly.Du_An_Tot_Ngiep.Entity.InvoiceDetail;
import edu.poly.Du_An_Tot_Ngiep.Entity.Product;
import edu.poly.Du_An_Tot_Ngiep.Service.CartService;
import edu.poly.Du_An_Tot_Ngiep.Service.CategoryService;
import edu.poly.Du_An_Tot_Ngiep.Service.CustomerService;
import edu.poly.Du_An_Tot_Ngiep.Service.OrderDetailsService;
import edu.poly.Du_An_Tot_Ngiep.Service.OrdersService;
import edu.poly.Du_An_Tot_Ngiep.Service.ProductService;
import edu.poly.Du_An_Tot_Ngiep.Service.UserService;

@Controller
public class CartController {

	@Autowired
	private UserService userService;

	@Autowired
	CartService cart;

	@Autowired
	OrdersService oders;

	@Autowired
	OrderDetailsService orderDetailsService;

	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/cart/add/{id}")//action cho phép nhập lưu giỏ hàng
	public String add(@PathVariable("id") Integer id) {//them hang vao gio hang
		return "index";//tra ve trang index
	}

	@RequestMapping("/cart/remove/{id}")//xóa giỏ hàng
	public String remove(@PathVariable("id") Integer id) {// xoa hang ra khoi gio hang
		return "redirect:/product/list";//tra ve trang prodcut
	}

	@GetMapping("/cart")//action hướng đến giỏ hàng 
	public String viewCart(ModelMap model, HttpServletRequest request, HttpSession session) {
	    //sử dụng pt findadll đưa giá trị vào model
		model.addAttribute("product", this.productService.findAll());
		//sử dụng pt findadll đưa giá trị vào model
		model.addAttribute("category", this.categoryService.findAll());
		int id = -1;
		//đọc cookie từ trình duyệt
		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về danh sách các cookie
		for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
		  //so sánh phần tử i trong cookie với accountuser
			if (cookies[i].getName().equals("accountcustomer")) {
			    //sử dụng pt findbyphone 
				Customer customer = this.customerService.findByPhoneCus(cookies[i].getValue()).get();
				//đưa giá trị vào model
				model.addAttribute("fullname", customer.getFullname());
				if (customer != null) {//kiểm tra nếu cs khac null
					id = customer.getCustomerId();
				}
				break;
				//kết thúc vòng lặp 
			}
		}

		if (session.getAttribute("cart") == null) {
			session.setAttribute("cart", new ArrayList<>());
		}
		if (this.oders.listInvoiceByUser(id).size() == 0) {
			model.addAttribute("orders", new ArrayList<>());
		} else {
			model.addAttribute("orders", this.oders.listInvoiceByUser(id));
		}
		return "shop/cart";
	}

	@GetMapping(value = "/orderdetails/{id}")//action hóa đơn chi tiết
	public String viewOrderdetails(@PathVariable("id") int id, ModelMap model, HttpServletRequest request) {
	    //sử dụng pt tìm ct hóa đơn đưa giá trị vào list
		List<InvoiceDetail> list = this.orderDetailsService.findDetailByInvoiceId(id);
		List<Product> productorder = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {//sử dụng vòng lặp for
			Product odrProduct = productService.findByIdProduct(list.get(i).getProduct().getIdProduct());
			odrProduct.setAmount(list.get(i).getAmount());
			productorder.add(odrProduct);
		}
		model.addAttribute("oldorders", productorder);
		return "shop/oderdetail";
	}
	

}
