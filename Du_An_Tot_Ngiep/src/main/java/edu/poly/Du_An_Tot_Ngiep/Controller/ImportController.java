package edu.poly.Du_An_Tot_Ngiep.Controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.Du_An_Tot_Ngiep.Entity.Imports;
import edu.poly.Du_An_Tot_Ngiep.Entity.Product;
import edu.poly.Du_An_Tot_Ngiep.Entity.User;
import edu.poly.Du_An_Tot_Ngiep.Service.ImportService;
import edu.poly.Du_An_Tot_Ngiep.Service.ProductService;
import edu.poly.Du_An_Tot_Ngiep.Service.UserService;

@Controller
public class ImportController {

	@Autowired
	private ImportService importService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	void getName(HttpServletRequest request, ModelMap model) {
	    //đọc cookie từ trình duyệt
		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về danh sách các cookie
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

	@GetMapping(value = "/manager/import")//action list
	public String listImport(ModelMap model, @CookieValue(value = "accountuser", required = false) String username,
			HttpServletRequest request, HttpServletResponse response) {
	    //gọi thực hiện pt findall trả về danh sách
		List<Imports> list = (List<Imports>) importService.findAll();
		//đưa giá trị vào model
		model.addAttribute("import", list);
		model.addAttribute("username", username);
		getName(request, model);
		//trả về trang manager import
		return "/manager/import/import";
	}

	@GetMapping(value = "/manager/addImport")//action thêm
	public String addImport(ModelMap model, @CookieValue(value = "accountuser", required = false) String username,
			HttpServletRequest request) {
	    //đưa các giá trị vào model
		model.addAttribute("import", new Imports());
		model.addAttribute("username", username);
		//gọi thực hiện pt findall đưa giá trị vào model
		model.addAttribute("listProduct", productService.findAll());
		getName(request, model);
		//trả về trang import
		return "/manager/import/addImport";
	}

	@PostMapping(value = "/manager/addImport")//action thêm mới 
	public String addImport(@ModelAttribute(value = "import") Imports import1, HttpServletRequest request,
			ModelMap model, RedirectAttributes redirect) {
	    
		Imports impl = new Imports();//sử dụng pt import
		//đọc cookie từ trình duyệt
		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về danh sách các cookie
		for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
			if (cookies[i].getName().equals("accountuser")) {//so sánh phần tử i trong cookie với accountuser
			    //sử dụng pt findbyphone ghi cookie
				User user = this.userService.findByPhone(cookies[i].getValue()).get();
				model.addAttribute("fullname", user.getFullname());
				//lấy tên và đưa giá trị vào model
				impl.setUsers(user.getFullname());
				model.addAttribute("image", user.getImageBase64());
				break;//kết thúc vòng lặp 
			}
		}
		if(!this.importService.findById(import1.getProduct().getIdProduct()).isPresent()) {//sử dụng pt findbyid lấy 1 product 
			impl.setProduct(import1.getProduct());
			//sử dụng pt improt lấy sản phẩm
			impl.setQuantity(import1.getQuantity());
			//sử dụng pt improt lấy số lượng
			this.importService.save(impl);
			//sử dụng pt save 
			redirect.addFlashAttribute("success", "Nhập kho thành công!");
			//đưa ra tb lưu thành công
		}else {//ngược lại 
		    //sản phẩm tồn tại đưa ra tb 
		    redirect.addFlashAttribute("success", "Sản phẩm đang tồn tại trong kho!");
		}
		    return "redirect:/manager/import";
	}

	@GetMapping(value = "/manager/updateImport/{idImport}")
	public String updateImport(ModelMap model, @PathVariable(name = "idImport") int id,
			@CookieValue(value = "accountuser", required = false) String username, HttpServletRequest request) {
	    //đưa giá trị username vào model
		model.addAttribute("username", username);
		getName(request, model);
		//sử dụng pt findadll và đưa giá trị vào model
		model.addAttribute("listProduct", productService.findAll());
		model.addAttribute("import",
				this.importService.findById(id).isPresent() ? this.importService.findById(id).get() : null);
		//trả về trang update
		return "/manager/import/updateImport";
	}

	@PostMapping(value = "/manager/updateImport")
	public String updateImport(@ModelAttribute(value = "import") Imports import1, @RequestParam int idImport,
			RedirectAttributes redirect, HttpServletRequest request, ModelMap model) {
		Imports impl = new Imports();//sử dụng pt import
		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về danh sách các cookie
		for (int i = 0; i < cookies.length; ++i) {//sử dụng vòng lặp for
			if (cookies[i].getName().equals("accountuser")) {//so sánh phần tử i trong cookie với accountuser
			    //sử dụng pt findbyphone để lấy tt đăng nhập
				User user = this.userService.findByPhone(cookies[i].getValue()).get();
				//sử dụng pt getfulname đưa giá trị vào model
				model.addAttribute("fullname", user.getFullname());
				import1.setUsers(user.getFullname());
				model.addAttribute("image", user.getImageBase64());
				break;
			}
		}
		
		int a = this.importService.findByIdImport(idImport).getQuantity();
		impl.setProduct(import1.getProduct());
		import1.setQuantity((a + import1.getQuantity()));
		//sd pt save product
		this.importService.save(import1);
		//đưa ra tb cập nhật thành công
		redirect.addFlashAttribute("success", "Cập nhập kho hàng thành công!");

		return "redirect:/manager/import";
	}

	@GetMapping(value = "/manager/deleteImport/{idImport}")//action xóa kho hàng
	public String deleteImport(@PathVariable(name = "idImport") int idImport, RedirectAttributes redirect) {
		this.importService.deleteById(idImport);//sử dụng pt xóa id import
		redirect.addFlashAttribute("success", "Xoá kho hàng thành công!");
		//đưa ra tb xóa thành công
		return "redirect:/manager/import";
	}
}
