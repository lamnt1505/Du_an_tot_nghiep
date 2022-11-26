package edu.poly.Du_An_Tot_Ngiep.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.Du_An_Tot_Ngiep.Entity.Category;
import edu.poly.Du_An_Tot_Ngiep.Entity.FeedBack;
import edu.poly.Du_An_Tot_Ngiep.Entity.Invoice;
import edu.poly.Du_An_Tot_Ngiep.Entity.InvoiceDetail;
import edu.poly.Du_An_Tot_Ngiep.Entity.Product;
import edu.poly.Du_An_Tot_Ngiep.Entity.User;
import edu.poly.Du_An_Tot_Ngiep.Service.CategoryService;
import edu.poly.Du_An_Tot_Ngiep.Service.FeedBackService;
import edu.poly.Du_An_Tot_Ngiep.Service.OrderDetailsService;
import edu.poly.Du_An_Tot_Ngiep.Service.OrdersService;
import edu.poly.Du_An_Tot_Ngiep.Service.ProductService;
import edu.poly.Du_An_Tot_Ngiep.Service.StatisticalService;
import edu.poly.Du_An_Tot_Ngiep.Service.UserService;

@Controller
public class ManagerController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private FeedBackService feedBackService;

	@Autowired
	OrdersService oders;

	@Autowired
	OrderDetailsService orderDetailsService;

	@Autowired
	StatisticalService statisticalService;
	
	//show user
	void getName(HttpServletRequest request, ModelMap model) {
	    //tạo mới 1 cookie
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
			if (cookies[i].getName().equals("accountuser")) {
			    //so sánh phần tử i trong cookie với accountuser
				User user = this.userService.findByPhone(cookies[i].getValue()).get();
				//sử dụng câu lệnh findbyphone để tìm số đt
				//đưa các giá trị vào model
				model.addAttribute("fullname", user.getFullname());
				model.addAttribute("image", user.getImageBase64());
				break;
			}
		}
	}

	
	@GetMapping(value = "/manager")//kich hoat action pt get
	public String manager(ModelMap model, @CookieValue(value = "accountuser", required = false) String username,
			MultipartFile image, HttpServletRequest request, HttpServletResponse response) {
	    //tạo mới 1 cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
			    //sd length lấy tt phần tử cookies
				if (cookies[i].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					//đưa các giá trị vào model
					model.addAttribute("username", username);
					model.addAttribute("fullname", user.getFullname());
					model.addAttribute("image", user.getImageBase64());
					
					return "redirect:/manager/listCategory";
				}
			}

		}
		return "redirect:/login";
	}

	@GetMapping(value = "/manager/listCategory")//kich hoat action pt get
	public String listCategory(Model model, @CookieValue(value = "accountuser", required = false) String username,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirect) {

		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về danh sách các cookie
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
			            //sd length lấy tt phần tử cookies
				if (cookies[i].getName().equals("accountuser")) {//kiểm tra cookie
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					if (model.asMap().get("success") != null)
						redirect.addFlashAttribute("success", model.asMap().get("success").toString());
					    //sử dụng addFlashAttribute tránh submit lại form 
					List<Category> list = categoryService.listCategory();//goi thuc hien pt findall tra ve ds 
					model.addAttribute("category", list);
					model.addAttribute("username", username);
					model.addAttribute("fullname", user.getFullname());
					model.addAttribute("image", user.getImageBase64());
					return "/manager/category/listCategory";//action chuyen huong den list
				}

			}
		}
		return "redirect:/login";

	}

	@GetMapping(value = "/manager/addCategory")//kich hoat action pt get
	public String addCategory(ModelMap model, @CookieValue(value = "accountuser", required = false) String username,
			HttpServletRequest request) {//lấy thông tin entity

		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về danh sách các cookie
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
			            //sd length lấy tt phần tử cookies
				if (cookies[i].getName().equals("accountuser")) {
					this.userService.findByPhone(cookies[i].getValue()).get();//sử dụng serviceimpl để lấy thông tin entity
					model.addAttribute("category", new Category());//sử dụng pt addtribute để lấy đối tượng entity
					getName(request, model);
					return "/manager/category/addCategory";//trả về view addcategory
				}
			}
		}
		return "redirect:/login";
	}

	@PostMapping(value = "/manager/addCategory")//kich hoat action pt post
	public String addCategory(@ModelAttribute(value = "category") @Valid Category category,
			RedirectAttributes redirect) {//chinh sua tt entity

		this.categoryService.save(category);//goi thuc hien pt luu 
		redirect.addFlashAttribute("success", "Thêm mới danh mục thành công!");//dua ra tb da luu entity

		return "redirect:/manager/listCategory";//action chuyen huong den list
	}

	@GetMapping(value = "/manager/updateCategory/{idCategory}")
	public String updateCategory(ModelMap model, @PathVariable(name = "idCategory") int idCategory,
			@CookieValue(value = "accountuser", required = false) String username, 
			HttpServletRequest request) {//chinh sua tt entity
		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về 1 mảng người dùng yêu cầu
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
			  //sd length lấy tt phần tử cookies
				if (cookies[i].getName().equals("accountuser")) {
					this.userService.findByPhone(cookies[i].getValue()).get();//import cts goi thuc hien pt finbyphone
					getName(request, model);
					model.addAttribute("category", categoryService.findById(idCategory));//import cts goi thuc hien pt findbyid
					return "/manager/category/updateCategory";
				}
			}
		}
		return "redirect:/login";//action chuyen huong den login
	}
	
	@PostMapping(value = "/manager/updateCategory")//kich hoat action pt post
	public String updateCategory(@ModelAttribute(value = "category") @Valid Category category,
			@RequestParam("idCategory") int idCategory, RedirectAttributes redirect) {
		this.categoryService.save(category);//goi thuc hien pt luu 
		redirect.addFlashAttribute("success", "Cập nhập danh mục thành công!");//dua ra tb da cập nhật entity
		return "redirect:/manager/listCategory";//action chuyen huong den list
	}

	@GetMapping(value = "/manager/deleteCategory/{idCategory}")//truyền vào idcategory 
	public String deleteCategory(@PathVariable(name = "idCategory") int idCategory,//khai báo PathVariable
			@CookieValue(value = "accountuser", required = false) String username, HttpServletRequest request,
			RedirectAttributes redirect) {
		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về 1 mảng người dùng yêu cầu
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
			    //sd length lấy tt phần tử cookies
				if (cookies[i].getName().equals("accountuser")) {
					this.userService.findByPhone(cookies[i].getValue()).get();//goi thực hiện pt find byphone để lấy user

					this.categoryService.deleteById(idCategory);//gọi thực hiện deletedbyid truyền vào id xóa theo category id
					redirect.addFlashAttribute("success", "Xóa danh mục thành công!");//đưa ra tb đã xóa thành công
					return "redirect:/manager/listCategory";//sử dụng kỹ thuật redirect trả về trang list category
				}

			}
		}
		return "redirect:/login";
	}

	// table product
	//pt trung giang cho listProductpage
	@GetMapping(value = "/manager/listProduct")
	public String listProduct(Model model, HttpServletRequest request, RedirectAttributes redirect) {
	    //khởi tạo ss product
		request.getSession().setAttribute("product", null);
		
		if (model.asMap().get("success") != null)
			redirect.addFlashAttribute("success", model.asMap().get("success").toString());
        //lấy gt đt FlashAttribute đưa ra tb tất cả action
		return "redirect:/listProduct/page/1";
	}

	@GetMapping(value = "/listProduct/page/{pageNumber}")//phân trang
	public String showProduct(@CookieValue(value = "accountuser") String username,
	        HttpServletRequest request,
			HttpServletResponse response, @PathVariable int pageNumber, Model model) {
	    //tạo mới 1 cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
				if (cookies[i].getName().equals("accountuser")) {
				    //lấy số đt người dùng
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					//khởi tạo PagedListHolder tên pages
					PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("product");
					//ép session sang đt PagedListHolder
					int pagesize = 5;
					//giá trị 5 phần từ trên 1 trang
					List<Product> list = productService.listProduct();
					//lấy ds product
					if (pages == null) {//kiểm tra PagedListHolder đã có dữ liệu chưa
					    //nếu đối tượng page null thì sẽ khởi tạo và thiết lập pagesize
						pages = new PagedListHolder<>(list);//dùng pt PagedListHolder phân trang theo danh sách
						pages.setPageSize(pagesize);//Đặt kích thước trang hiện tại.
					} else {//ngược lại
						final int goToPage = pageNumber - 1;
						if (goToPage <= pages.getPageCount() && goToPage >= 0) {
						    //Trả lại số trang cho danh sách nguồn hiện tại.
							pages.setPage(goToPage);
						}
					}
					
					request.getSession().setAttribute("product", pages);
					int current = pages.getPage() + 1;//trang hiện tại
					int begin = Math.max(1, current - list.size()); //bắt đầu
					//thực hiện tính toán kích thước của trang
					//Thiết lập trang mà chúng ta muốn hiện lên View
					int end = Math.min(begin + 5, pages.getPageCount());//kết thúc
					int totalPageCount = pages.getPageCount();//tính toán số trang hiển thị trên view
					String baseUrl = "/listProduct/page/";//
					//hiển thị dữ liệu trở lại trên view
					
					//chuyển các biến sang view
					model.addAttribute("beginIndex", begin);
					model.addAttribute("endIndex", end);
					model.addAttribute("currentIndex", current);
					model.addAttribute("totalPageCount", totalPageCount);
					model.addAttribute("baseUrl", baseUrl);
					model.addAttribute("product", pages);//đưa plhd sang view 
					model.addAttribute("username", username);
					model.addAttribute("fullname", user.getFullname());
					model.addAttribute("image", user.getImageBase64());
					//trả về trang list product
					return "/manager/product/listProduct";
				}

			}
		}
		return "redirect:/login";
	}

	@GetMapping(value = "/manager/addProduct")
	public String addProduct(ModelMap model, @CookieValue(value = "accountuser", required = false) String username,
			HttpServletRequest request) {
	    //tạo 1 mới cookie 
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
			        //sd length lấy tt phần tử cookies
				if (cookies[i].getName().equals("accountuser")) {//kiểm tra nếu i khác entity
					this.userService.findByPhone(cookies[i].getValue()).get();//sử dụng userService lấy tt người đăng nhập
					//lấy tên và hình ảnh
					getName(request, model);
					//tạo form trống
					model.addAttribute("product", new Product());
					//đưa giá trị vào model
					model.addAttribute("listCategory", categoryService.findAll());
					//trả về trang thêm sản phẩm
					return "/manager/product/addProduct";
				}

			}
		}
		return "redirect:/login";
	}
	
	
	@PostMapping(value = "/manager/addProduct") 
	public String addProduct(@RequestParam(value = "image") MultipartFile image,
			@ModelAttribute(name = "product") @Valid Product product, BindingResult result,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {//kiểm tra nếu có lỗi
		    //trả về view 
		  return "/manager/addProduct";
		} else {
			this.productService.save(product);//sử dụng productService
			//đưa ra tb lưu thành công
			redirect.addFlashAttribute("success", "Thêm mới thông tin sản phẩm thành công!");
		}
		//trả về trang listproduct
		return "redirect:/manager/listProduct";
	}

	@InitBinder//đánh dấu 1 phương thức tùy biến ràng buộc tham số yêu cầu
	//định dạng biểu mẫu
    //Chuyển đổi các giá trị yêu cầu dựa trên chuỗi 
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	
	
	@GetMapping(value = "/manager/updateProduct/{idProduct}")
	public String updateProduct(ModelMap model, @PathVariable(name = "idProduct") int id,
			@CookieValue(value = "accountuser", required = false) String username, HttpServletRequest request) {
	    //tạo 1 cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sử dụng vòng lặp for
				if (cookies[i].getName().equals("accountuser")) {//kiểm tra nếu khác entity
					this.userService.findByPhone(cookies[i].getValue()).get();
					//sử dụng entityphone để lấy tên người đăng nhập
					//đưa các giá trị vào model
					model.addAttribute("listCategory", this.categoryService.findAll());//sử dụng câu lệnh tìm tất cả sản phẩm
					model.addAttribute("product", this.productService.findById(id).isPresent() ? this.productService.findById(id).get(): null);
					//đưa các giá trị vào model
					getName(request, model);
					//trả về trang update
					return "/manager/product/updateProduct";
				}

			}
		}
		return "redirect:/login";
	}
	
	//action update
	@PostMapping(value = "/manager/updateProduct")//action update
	public String updateProduct(@RequestParam(value = "image") MultipartFile image,
			@ModelAttribute(name = "product") @Valid Product product, BindingResult result,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {//kiểm tra nếu có lỗi
		    //trả về trang view update
			return "/manager/updateProduct";
		} else {//ngược lại
			this.productService.save(product);//sd câu lệnh lưu tt product
			//đưa ra tb đã lưu thành công
			redirect.addFlashAttribute("success", "Cập nhập thông tin sản phẩm thành công!");
		}

		if (!image.isEmpty()) {//kiểm tra nếu hình ảnh bị trống
			try {
				product.setImage(image.getBytes());//kiểm tra mức lưu KB ha
			} catch (Exception e) {
				e.printStackTrace();//đưa ra tb lỗi trong console
			}
		} else {
			product.setImage(productService.findById(product.getIdProduct()).get().getImage());
			//sd câu lệnh set tìm theo id

		}
		return "redirect:/manager/listProduct";
	}

	@GetMapping(value = "/manager/deleteProduct/{idProduct}")
	public String deleteProduct(@PathVariable(name = "idProduct") int id,
			@CookieValue(value = "accountuser", required = false) String username, HttpServletRequest request,
			RedirectAttributes redirect) {
	    //tạo mới 1 cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua các cookie
				if (cookies[i].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					//tìm  số đt user
					this.productService.deleteById(id);//sử dụng câu lệnh xóa theo id
					//đưa ra tb xóa thành công
					redirect.addFlashAttribute("success", "Xóa sản phẩm thành công!");
					//trả về trang list product
					return "redirect:/manager/listProduct";
				}
			}
		}
		//trả về trang login
		return "redirect:/login";
	}

	// feedback
	@GetMapping(value = "/manager/feedback")
	public String listFeedBack(ModelMap model, @CookieValue(value = "accountuser") String username,
			HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về danh sách các cookie 
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua các cookie
				if (cookies[i].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					//đưa các giá trị vào model
					model.addAttribute("username", username);
					model.addAttribute("fullname", user.getFullname());
					model.addAttribute("image", user.getImageBase64());
					//sử dụng câu lệnh tìm tất cả
					this.feedBackService.findAll();
					//trả về trang feedback
					return "/manager/feedback/feedback";
				}

			}
		}
		return "redirect:/login";
	}

	@PostMapping(value = "index/contact")
	public String addFeedBack(@ModelAttribute(name = "feedback") @Valid FeedBack feedBack, BindingResult result) {
		if (result.hasErrors()) {//nếu có lỗi xảy ra 
		    //trả về trag contact
			return "shop/contact";
		}
		this.feedBackService.save(feedBack);//sử dụng câu lệnh save
		return "shop/contact";
	}

	// product Detail
	@GetMapping("/manager/order")
	public String listOrder(ModelMap model, @CookieValue(value = "accountuser", required = false) String username,
			HttpServletRequest request, HttpServletResponse response) {
	    //taoj moi 1 cookie
		Cookie[] cookies = request.getCookies(); 
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua các cookie
				if (cookies[i].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					//sử dụng userService lấy tt người đăng nhập
					//đưa các giá trị vào model
					model.addAttribute("username", username);
					model.addAttribute("fullname", user.getFullname());
					model.addAttribute("image", user.getImageBase64());
					//hiển thị danh sách hóa đơn
					List<Invoice> list = this.oders.listInvoice();
					//đưa các giá trị vào model
					model.addAttribute("listOrder", list);
					return "manager/order/order";
				}
			}
		}
		return "redirect:/login";
	}

	@GetMapping(value = "/manager/orderDetail/{id}")
	public String viewOrderdetailsForManager(@PathVariable("id") int id, ModelMap model,
			@CookieValue(value = "accountuser", required = false) String username, HttpServletRequest request,
			HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về danh sách các cookie 
		if (cookies != null) {//kiểm tra cookie
			for (int j = 0; j < cookies.length; ++j) {//sd vl for để duyệt qua các cookie
				if (cookies[j].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[j].getValue()).get();
					//sử dụng userService lấy tt người đăng nhập
					//đưa các giá trị vào model
					model.addAttribute("username", username);
					model.addAttribute("fullname", user.getFullname());
					model.addAttribute("image", user.getImageBase64());
					//hiển thị danh sách hóa đơn chi tiết
					List<InvoiceDetail> list = this.orderDetailsService.findDetailByInvoiceId(id);
					//hiên thị danh sách product
					List<Product> productorder = new ArrayList<>();
					for (int i = 0; i < list.size(); i++) {//sd vl for để duyệt qua các product,order
						Product odrProduct = productService.findByIdProduct(list.get(i).getProduct().getIdProduct());
						odrProduct.setAmount(list.get(i).getAmount());
						productorder.add(odrProduct);
					}
					//đưa các giá trị vào model
					model.addAttribute("listOrderDetail", productorder);
					return "manager/order/orderDetail";
				}
			}
		}
		return "redirect:/login";
	}

}
