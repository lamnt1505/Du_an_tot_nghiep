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
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; ++i) {
			if (cookies[i].getName().equals("accountuser")) {
				User user = this.userService.findByPhone(cookies[i].getValue()).get();
				model.addAttribute("fullname", user.getFullname());
				model.addAttribute("image", user.getImageBase64());
				break;
			}
		}
	}

	@GetMapping(value = "/manager/import")
	public String listImport(ModelMap model, @CookieValue(value = "accountuser", required = false) String username,
			HttpServletRequest request, HttpServletResponse response) {
		List<Imports> list = (List<Imports>) importService.findAll();
		model.addAttribute("import", list);
		model.addAttribute("username", username);
		getName(request, model);
		return "/manager/import/import";
	}

	@GetMapping(value = "/manager/addImport")
	public String addImport(ModelMap model, @CookieValue(value = "accountuser", required = false) String username,
			HttpServletRequest request) {
		model.addAttribute("import", new Imports());
		model.addAttribute("username", username);
		model.addAttribute("listProduct", productService.findAll());
		getName(request, model);
		return "/manager/import/addImport";
	}

	@PostMapping(value = "/manager/addImport")
	public String addImport(@ModelAttribute(value = "import") Imports import1, HttpServletRequest request,
			ModelMap model, RedirectAttributes redirect) {

		Imports impl = new Imports();
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; ++i) {
			if (cookies[i].getName().equals("accountuser")) {
				User user = this.userService.findByPhone(cookies[i].getValue()).get();
				model.addAttribute("fullname", user.getFullname());
				impl.setUsers(user.getFullname());
				model.addAttribute("image", user.getImageBase64());
				break;
			}
		}
		if(!this.importService.findById(import1.getProduct().getIdProduct()).isPresent()) {
			impl.setProduct(import1.getProduct());
			impl.setQuantity(import1.getQuantity());
			this.importService.save(impl);
			redirect.addFlashAttribute("success", "Nhập kho thành công!");
		}else {
		redirect.addFlashAttribute("success", "Sản phẩm đang tồn tại trong kho!");
		}
		return "redirect:/manager/import";
	}

	@GetMapping(value = "/manager/updateImport/{idImport}")
	public String updateImport(ModelMap model, @PathVariable(name = "idImport") int id,
			@CookieValue(value = "accountuser", required = false) String username, HttpServletRequest request) {
		model.addAttribute("username", username);
		getName(request, model);
		model.addAttribute("listProduct", productService.findAll());
		model.addAttribute("import",
				this.importService.findById(id).isPresent() ? this.importService.findById(id).get() : null);
		return "/manager/import/updateImport";
	}

	@PostMapping(value = "/manager/updateImport")
	public String updateImport(@ModelAttribute(value = "import") Imports import1, @RequestParam int idImport,
			RedirectAttributes redirect, HttpServletRequest request, ModelMap model) {
		Imports impl = new Imports();
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; ++i) {
			if (cookies[i].getName().equals("accountuser")) {
				User user = this.userService.findByPhone(cookies[i].getValue()).get();
				model.addAttribute("fullname", user.getFullname());
				import1.setUsers(user.getFullname());
				model.addAttribute("image", user.getImageBase64());
				break;
			}
		}
		int a = this.importService.findByIdImport(idImport).getQuantity();
		impl.setProduct(import1.getProduct());
		import1.setQuantity((a + import1.getQuantity()));
		this.importService.save(import1);
		redirect.addFlashAttribute("success", "Cập nhập kho hàng thành công!");

		return "redirect:/manager/import";
	}

	@GetMapping(value = "/manager/deleteImport/{idImport}")
	public String deleteImport(@PathVariable(name = "idImport") int idImport, RedirectAttributes redirect) {
		this.importService.deleteById(idImport);
		redirect.addFlashAttribute("success", "Xoá kho hàng thành công!");
		return "redirect:/manager/import";
	}
}
