package edu.poly.Du_An_Tot_Ngiep.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.Du_An_Tot_Ngiep.Entity.Customer;
import edu.poly.Du_An_Tot_Ngiep.Entity.FeedBack;
import edu.poly.Du_An_Tot_Ngiep.Entity.Product;
import edu.poly.Du_An_Tot_Ngiep.Service.CategoryService;
import edu.poly.Du_An_Tot_Ngiep.Service.CustomerService;
import edu.poly.Du_An_Tot_Ngiep.Service.FeedBackService;
import edu.poly.Du_An_Tot_Ngiep.Service.ProductService;
import edu.poly.Du_An_Tot_Ngiep.Service.UserService;

@Controller
@RequestMapping(value = "/index")
public class HomeController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FeedBackService feedBackService;

	void getName(HttpServletRequest request, ModelMap model) {
		// show user
		Cookie[] cookies = request.getCookies();
		//đọc cookie từ trình duyệt
		for (int i = 0; i < cookies.length; ++i) {//sử dụng rqck trả về danh sách các cookie
			if (cookies[i].getName().equals("accountcustomer")) {
			    //so sánh phần tử i trong cookie với accountuser
				Customer customer = this.customerService.findByPhoneCus(cookies[i].getValue()).get();
				//sử dụng câu lệnh findbyphone để tìm số đt
				model.addAttribute("fullname", customer.getFullname());
				model.addAttribute("customerId", customer.getCustomerId());
				//đưa các giá trị vào model
				break;
			}
		}
	}
	//action pt truyền dữ liệu
	void initHomeResponse(ModelMap model) {
		model.addAttribute("prods", this.productService.findAll());
		model.addAttribute("category", this.categoryService.findAll());
		model.addAttribute("showProduct", this.productService.showListProductForIndex());
		model.addAttribute("feedback", this.feedBackService.listFeedBack());
	}

	@GetMapping()
	public String Home(ModelMap model, HttpServletRequest request,
			@CookieValue(value = "accountcustomer", required = false) String phone, HttpServletResponse response,
			HttpSession session) {
		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về 1 mảng người dùng yêu cầu
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
				if (cookies[i].getName().equals("accountcustomer")) {
					Customer customer = this.customerService.findByPhoneCus(cookies[i].getValue()).get();
					//goi thực hiện pt find byphone để lấy customer
					if (session.getAttribute("cart") == null) {
						session.setAttribute("cart", new ArrayList<>());
					}
					//đưa các giá trị vào model
					model.addAttribute("fullname", customer.getFullname());
					model.addAttribute("customerId", customer.getCustomerId());
				}
			}
		} else {
			if (session.getAttribute("cart") == null) {
				session.setAttribute("cart", new ArrayList<>());
			}
		}
		//gọi pt home trả về model
		this.initHomeResponse(model);
		return "home/index";
	}

	@GetMapping("/product")
	public String ShowListProduct(ModelMap model, RedirectAttributes redirect, HttpServletRequest request,
			HttpServletResponse response) {
		//sử dụng pt findall đưa các giá trị vào model		
		model.addAttribute("product", this.productService.findAll());
		model.addAttribute("category", this.categoryService.findAll());
		// show user
		getName(request, model);
		//gọi pt home trả về model
		initHomeResponse(model);
		//sử dụng pt listProduct đưa các giá trị vào model
		model.addAttribute("showProduct", this.productService.listProduct());
		return "shop/product";
	}

	@GetMapping("/about")
	public String ShowAbout(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		//sử dụng pt findall đưa các giá trị vào model	
		model.addAttribute("product", this.productService.findAll());
		model.addAttribute("category", this.categoryService.findAll());
		// show user
		getName(request, model);
		//gọi pt home trả về model
		initHomeResponse(model);
		return "shop/about";
	}

	@GetMapping("/feedback")
	public String ShowContact(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		//sử dụng pt findall đưa các giá trị vào model
		model.addAttribute("product", this.productService.findAll());
		model.addAttribute("category", this.categoryService.findAll());
		model.addAttribute("feedback", new FeedBack());
		// show user
		getName(request, model);
		//gọi pt home trả về model
		initHomeResponse(model);
		return "shop/feedback";
	}

	// showCategoryById
	@GetMapping(value = "/showProductByIdCategory/{idCategory}")
	public String ShowProductByIdCategory(ModelMap model, @PathVariable("idCategory") int idCategory,
			HttpServletRequest request, HttpServletResponse response) {
		//sử dụng pt findall đưa các giá trị vào model
		model.addAttribute("product", this.productService.findAll());
		model.addAttribute("category", this.categoryService.findAll());
		Optional<Product> p = this.productService.findById(idCategory);
		if (p == null) {
			return "shop/productByIdCategory";
		}
		// show user
		getName(request, model);
		initHomeResponse(model);
		//sử dụng pt showProductByIdCategory đưa các giá trị vào model
		model.addAttribute("showProductByIdCategory", this.productService.showListProductByIdCategory(idCategory));

		return "shop/productByIdCategory";
	}

	@GetMapping(value = "/showProductSingle/{idProduct}")
	public String ShowProductByIdProductDetail(ModelMap model, @PathVariable("idProduct") int id,
			HttpServletRequest request, HttpServletResponse response) {
		//sử dụng pt findall đưa các giá trị vào model
		model.addAttribute("product", this.productService.findAll());
		model.addAttribute("category", this.categoryService.findAll());

		model.addAttribute("showProductSingle", this.productService.findById(id).get());
		// show user
		getName(request, model);
		initHomeResponse(model);
		Product product = this.productService.findById(id).get();
		Product p = this.productService.findByIdProduct(product.getIdProduct());
		p.setName(product.getName());
		p.setPrice(product.getPrice());
		p.setImage(product.getImage());
		List<Product> list = this.productService.findByIdCategory(p.getCategory().getIdCategory());

		for (int i = 0; i < list.size(); i++) {
			p = list.get(i);
			if (p.getIdProduct() == product.getIdProduct()) {
				list.remove(list.get(i));
				break;
			}
		}
		model.addAttribute("showProductByCategory", list);

		return "shop/productSingle";
	}

	@GetMapping("/searchProduct")
	public String searchProductByIdCategory(ModelMap model, @RequestParam("key") String key, Product product,
			RedirectAttributes redirect, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("product", this.productService.findAll());
		model.addAttribute("category", this.categoryService.findAll());
		List<Product> products = this.productService.searchListProductByIdCategory(key);
		
		getName(request, model);
		initHomeResponse(model);

		if (products.isEmpty() || products.contains(product)) {
			return "shop/searchProduct";
		}

		model.addAttribute("searchProduct", this.productService.searchListProductByIdCategory(key));
		return "shop/searchProduct";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		initHomeResponse(model);
		Cookie cookie = new Cookie("accountcustomer", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/index";
	}

}
