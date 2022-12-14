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
	    //t???o m???i 1 cookie
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; ++i) {//sd vl for ????? duy???t qua cookie
			if (cookies[i].getName().equals("accountuser")) {
			    //so s??nh ph???n t??? i trong cookie v???i accountuser
				User user = this.userService.findByPhone(cookies[i].getValue()).get();
				//s??? d???ng c??u l???nh findbyphone ????? t??m s??? ??t
				//????a c??c gi?? tr??? v??o model
				model.addAttribute("fullname", user.getFullname());
				model.addAttribute("image", user.getImageBase64());
				break;
			}
		}
	}

	
	@GetMapping(value = "/manager")//kich hoat action pt get
	public String manager(ModelMap model, @CookieValue(value = "accountuser", required = false) String username,
			MultipartFile image, HttpServletRequest request, HttpServletResponse response) {
	    //t???o m???i 1 cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {//ki???m tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for ????? duy???t qua cookie
			    //sd length l???y tt ph???n t??? cookies
				if (cookies[i].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					//????a c??c gi?? tr??? v??o model
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

		Cookie[] cookies = request.getCookies();//s??? d???ng rqck tr??? v??? danh s??ch c??c cookie
		if (cookies != null) {//ki???m tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for ????? duy???t qua cookie
			            //sd length l???y tt ph???n t??? cookies
				if (cookies[i].getName().equals("accountuser")) {//ki???m tra cookie
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					if (model.asMap().get("success") != null)
						redirect.addFlashAttribute("success", model.asMap().get("success").toString());
					    //s??? d???ng addFlashAttribute tr??nh submit l???i form 
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
			HttpServletRequest request) {//l???y th??ng tin entity

		Cookie[] cookies = request.getCookies();//s??? d???ng rqck tr??? v??? danh s??ch c??c cookie
		if (cookies != null) {//ki???m tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for ????? duy???t qua cookie
			            //sd length l???y tt ph???n t??? cookies
				if (cookies[i].getName().equals("accountuser")) {
					this.userService.findByPhone(cookies[i].getValue()).get();//s??? d???ng serviceimpl ????? l???y th??ng tin entity
					model.addAttribute("category", new Category());//s??? d???ng pt addtribute ????? l???y ?????i t?????ng entity
					getName(request, model);
					return "/manager/category/addCategory";//tr??? v??? view addcategory
				}
			}
		}
		return "redirect:/login";
	}

	@PostMapping(value = "/manager/addCategory")//kich hoat action pt post
	public String addCategory(@ModelAttribute(value = "category") @Valid Category category,
			RedirectAttributes redirect) {//chinh sua tt entity

		this.categoryService.save(category);//goi thuc hien pt luu 
		redirect.addFlashAttribute("success", "Th??m m???i danh m???c th??nh c??ng!");//dua ra tb da luu entity

		return "redirect:/manager/listCategory";//action chuyen huong den list
	}

	@GetMapping(value = "/manager/updateCategory/{idCategory}")
	public String updateCategory(ModelMap model, @PathVariable(name = "idCategory") int idCategory,
			@CookieValue(value = "accountuser", required = false) String username, 
			HttpServletRequest request) {//chinh sua tt entity
		Cookie[] cookies = request.getCookies();//s??? d???ng rqck tr??? v??? 1 m???ng ng?????i d??ng y??u c???u
		if (cookies != null) {//ki???m tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for ????? duy???t qua cookie
			  //sd length l???y tt ph???n t??? cookies
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
		redirect.addFlashAttribute("success", "C???p nh???p danh m???c th??nh c??ng!");//dua ra tb da c???p nh???t entity
		return "redirect:/manager/listCategory";//action chuyen huong den list
	}

	@GetMapping(value = "/manager/deleteCategory/{idCategory}")//truy???n v??o idcategory 
	public String deleteCategory(@PathVariable(name = "idCategory") int idCategory,//khai b??o PathVariable
			@CookieValue(value = "accountuser", required = false) String username, HttpServletRequest request,
			RedirectAttributes redirect) {
		Cookie[] cookies = request.getCookies();//s??? d???ng rqck tr??? v??? 1 m???ng ng?????i d??ng y??u c???u
		if (cookies != null) {//ki???m tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for ????? duy???t qua cookie
			    //sd length l???y tt ph???n t??? cookies
				if (cookies[i].getName().equals("accountuser")) {
					this.userService.findByPhone(cookies[i].getValue()).get();//goi th???c hi???n pt find byphone ????? l???y user

					this.categoryService.deleteById(idCategory);//g???i th???c hi???n deletedbyid truy???n v??o id x??a theo category id
					redirect.addFlashAttribute("success", "X??a danh m???c th??nh c??ng!");//????a ra tb ???? x??a th??nh c??ng
					return "redirect:/manager/listCategory";//s??? d???ng k??? thu???t redirect tr??? v??? trang list category
				}

			}
		}
		return "redirect:/login";
	}

	// table product
	//pt trung giang cho listProductpage
	@GetMapping(value = "/manager/listProduct")
	public String listProduct(Model model, HttpServletRequest request, RedirectAttributes redirect) {
	    //kh???i t???o ss product
		request.getSession().setAttribute("product", null);
		
		if (model.asMap().get("success") != null)
			redirect.addFlashAttribute("success", model.asMap().get("success").toString());
        //l???y gt ??t FlashAttribute ????a ra tb t???t c??? action
		return "redirect:/listProduct/page/1";
	}

	@GetMapping(value = "/listProduct/page/{pageNumber}")//ph??n trang
	public String showProduct(@CookieValue(value = "accountuser") String username,
	        HttpServletRequest request,
			HttpServletResponse response, @PathVariable int pageNumber, Model model) {
	    //t???o m???i 1 cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {//ki???m tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for ????? duy???t qua cookie
				if (cookies[i].getName().equals("accountuser")) {
				    //l???y s??? ??t ng?????i d??ng
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					//kh???i t???o PagedListHolder t??n pages
					PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("product");
					//??p session sang ??t PagedListHolder
					int pagesize = 5;
					//gi?? tr??? 5 ph???n t??? tr??n 1 trang
					List<Product> list = productService.listProduct();
					//l???y ds product
					if (pages == null) {//ki???m tra PagedListHolder ???? c?? d??? li???u ch??a
					    //n???u ?????i t?????ng page null th?? s??? kh???i t???o v?? thi???t l???p pagesize
						pages = new PagedListHolder<>(list);//d??ng pt PagedListHolder ph??n trang theo danh s??ch
						pages.setPageSize(pagesize);//?????t k??ch th?????c trang hi???n t???i.
					} else {//ng?????c l???i
						final int goToPage = pageNumber - 1;
						if (goToPage <= pages.getPageCount() && goToPage >= 0) {
						    //Tr??? l???i s??? trang cho danh s??ch ngu???n hi???n t???i.
							pages.setPage(goToPage);
						}
					}
					
					request.getSession().setAttribute("product", pages);
					int current = pages.getPage() + 1;//trang hi???n t???i
					int begin = Math.max(1, current - list.size()); //b???t ?????u
					//th???c hi???n t??nh to??n k??ch th?????c c???a trang
					//Thi???t l???p trang m?? ch??ng ta mu???n hi???n l??n View
					int end = Math.min(begin + 5, pages.getPageCount());//k???t th??c
					int totalPageCount = pages.getPageCount();//t??nh to??n s??? trang hi???n th??? tr??n view
					String baseUrl = "/listProduct/page/";//
					//hi???n th??? d??? li???u tr??? l???i tr??n view
					
					//chuy???n c??c bi???n sang view
					model.addAttribute("beginIndex", begin);
					model.addAttribute("endIndex", end);
					model.addAttribute("currentIndex", current);
					model.addAttribute("totalPageCount", totalPageCount);
					model.addAttribute("baseUrl", baseUrl);
					model.addAttribute("product", pages);//????a plhd sang view 
					model.addAttribute("username", username);
					model.addAttribute("fullname", user.getFullname());
					model.addAttribute("image", user.getImageBase64());
					//tr??? v??? trang list product
					return "/manager/product/listProduct";
				}

			}
		}
		return "redirect:/login";
	}

	@GetMapping(value = "/manager/addProduct")
	public String addProduct(ModelMap model, @CookieValue(value = "accountuser", required = false) String username,
			HttpServletRequest request) {
	    //t???o 1 m???i cookie 
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {//ki???m tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for ????? duy???t qua cookie
			        //sd length l???y tt ph???n t??? cookies
				if (cookies[i].getName().equals("accountuser")) {//ki???m tra n???u i kh??c entity
					this.userService.findByPhone(cookies[i].getValue()).get();//s??? d???ng userService l???y tt ng?????i ????ng nh???p
					//l???y t??n v?? h??nh ???nh
					getName(request, model);
					//t???o form tr???ng
					model.addAttribute("product", new Product());
					//????a gi?? tr??? v??o model
					model.addAttribute("listCategory", categoryService.findAll());
					//tr??? v??? trang th??m s???n ph???m
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
		if (result.hasErrors()) {//ki???m tra n???u c?? l???i
		    //tr??? v??? view 
		  return "/manager/addProduct";
		} else {
			this.productService.save(product);//s??? d???ng productService
			//????a ra tb l??u th??nh c??ng
			redirect.addFlashAttribute("success", "Th??m m???i th??ng tin s???n ph???m th??nh c??ng!");
		}
		//tr??? v??? trang listproduct
		return "redirect:/manager/listProduct";
	}

	@InitBinder//????nh d???u 1 ph????ng th???c t??y bi???n r??ng bu???c tham s??? y??u c???u
	//?????nh d???ng bi???u m???u
    //Chuy???n ?????i c??c gi?? tr??? y??u c???u d???a tr??n chu???i 
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	
	
	@GetMapping(value = "/manager/updateProduct/{idProduct}")
	public String updateProduct(ModelMap model, @PathVariable(name = "idProduct") int id,
			@CookieValue(value = "accountuser", required = false) String username, HttpServletRequest request) {
	    //t???o 1 cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {//ki???m tra cookie
			for (int i = 0; i < cookies.length; ++i) {//s??? d???ng v??ng l???p for
				if (cookies[i].getName().equals("accountuser")) {//ki???m tra n???u kh??c entity
					this.userService.findByPhone(cookies[i].getValue()).get();
					//s??? d???ng entityphone ????? l???y t??n ng?????i ????ng nh???p
					//????a c??c gi?? tr??? v??o model
					model.addAttribute("listCategory", this.categoryService.findAll());//s??? d???ng c??u l???nh t??m t???t c??? s???n ph???m
					model.addAttribute("product", this.productService.findById(id).isPresent() ? this.productService.findById(id).get(): null);
					//????a c??c gi?? tr??? v??o model
					getName(request, model);
					//tr??? v??? trang update
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
		if (result.hasErrors()) {//ki???m tra n???u c?? l???i
		    //tr??? v??? trang view update
			return "/manager/updateProduct";
		} else {//ng?????c l???i
			this.productService.save(product);//sd c??u l???nh l??u tt product
			//????a ra tb ???? l??u th??nh c??ng
			redirect.addFlashAttribute("success", "C???p nh???p th??ng tin s???n ph???m th??nh c??ng!");
		}

		if (!image.isEmpty()) {//ki???m tra n???u h??nh ???nh b??? tr???ng
			try {
				product.setImage(image.getBytes());//ki???m tra m???c l??u KB ha
			} catch (Exception e) {
				e.printStackTrace();//????a ra tb l???i trong console
			}
		} else {
			product.setImage(productService.findById(product.getIdProduct()).get().getImage());
			//sd c??u l???nh set t??m theo id

		}
		return "redirect:/manager/listProduct";
	}

	@GetMapping(value = "/manager/deleteProduct/{idProduct}")
	public String deleteProduct(@PathVariable(name = "idProduct") int id,
			@CookieValue(value = "accountuser", required = false) String username, HttpServletRequest request,
			RedirectAttributes redirect) {
	    //t???o m???i 1 cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {//ki???m tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for ????? duy???t qua c??c cookie
				if (cookies[i].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					//t??m  s??? ??t user
					this.productService.deleteById(id);//s??? d???ng c??u l???nh x??a theo id
					//????a ra tb x??a th??nh c??ng
					redirect.addFlashAttribute("success", "X??a s???n ph???m th??nh c??ng!");
					//tr??? v??? trang list product
					return "redirect:/manager/listProduct";
				}
			}
		}
		//tr??? v??? trang login
		return "redirect:/login";
	}

	// feedback
	@GetMapping(value = "/manager/feedback")
	public String listFeedBack(ModelMap model, @CookieValue(value = "accountuser") String username,
			HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();//s??? d???ng rqck tr??? v??? danh s??ch c??c cookie 
		if (cookies != null) {//ki???m tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for ????? duy???t qua c??c cookie
				if (cookies[i].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					//????a c??c gi?? tr??? v??o model
					model.addAttribute("username", username);
					model.addAttribute("fullname", user.getFullname());
					model.addAttribute("image", user.getImageBase64());
					//s??? d???ng c??u l???nh t??m t???t c???
					this.feedBackService.findAll();
					//tr??? v??? trang feedback
					return "/manager/feedback/feedback";
				}

			}
		}
		return "redirect:/login";
	}

	@PostMapping(value = "index/contact")
	public String addFeedBack(@ModelAttribute(name = "feedback") @Valid FeedBack feedBack, BindingResult result) {
		if (result.hasErrors()) {//n???u c?? l???i x???y ra 
		    //tr??? v??? trag contact
			return "shop/contact";
		}
		this.feedBackService.save(feedBack);//s??? d???ng c??u l???nh save
		return "shop/contact";
	}

	// product Detail
	@GetMapping("/manager/order")
	public String listOrder(ModelMap model, @CookieValue(value = "accountuser", required = false) String username,
			HttpServletRequest request, HttpServletResponse response) {
	    //taoj moi 1 cookie
		Cookie[] cookies = request.getCookies(); 
		if (cookies != null) {//ki???m tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for ????? duy???t qua c??c cookie
				if (cookies[i].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					//s??? d???ng userService l???y tt ng?????i ????ng nh???p
					//????a c??c gi?? tr??? v??o model
					model.addAttribute("username", username);
					model.addAttribute("fullname", user.getFullname());
					model.addAttribute("image", user.getImageBase64());
					//hi???n th??? danh s??ch h??a ????n
					List<Invoice> list = this.oders.listInvoice();
					//????a c??c gi?? tr??? v??o model
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

		Cookie[] cookies = request.getCookies();//s??? d???ng rqck tr??? v??? danh s??ch c??c cookie 
		if (cookies != null) {//ki???m tra cookie
			for (int j = 0; j < cookies.length; ++j) {//sd vl for ????? duy???t qua c??c cookie
				if (cookies[j].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[j].getValue()).get();
					//s??? d???ng userService l???y tt ng?????i ????ng nh???p
					//????a c??c gi?? tr??? v??o model
					model.addAttribute("username", username);
					model.addAttribute("fullname", user.getFullname());
					model.addAttribute("image", user.getImageBase64());
					//hi???n th??? danh s??ch h??a ????n chi ti???t
					List<InvoiceDetail> list = this.orderDetailsService.findDetailByInvoiceId(id);
					//hi??n th??? danh s??ch product
					List<Product> productorder = new ArrayList<>();
					for (int i = 0; i < list.size(); i++) {//sd vl for ????? duy???t qua c??c product,order
						Product odrProduct = productService.findByIdProduct(list.get(i).getProduct().getIdProduct());
						odrProduct.setAmount(list.get(i).getAmount());
						productorder.add(odrProduct);
					}
					//????a c??c gi?? tr??? v??o model
					model.addAttribute("listOrderDetail", productorder);
					return "manager/order/orderDetail";
				}
			}
		}
		return "redirect:/login";
	}

}
