package edu.poly.Du_An_Tot_Ngiep.Controller;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.poly.Du_An_Tot_Ngiep.Entity.Customer;
import edu.poly.Du_An_Tot_Ngiep.Entity.User;
import edu.poly.Du_An_Tot_Ngiep.Service.CustomerService;
import edu.poly.Du_An_Tot_Ngiep.Service.UserService;

@Controller
@RequestMapping("")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/login")
	public String login(ModelMap model) {
		return "/login/login1";
	}

	void getName(HttpServletRequest request, ModelMap model) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; ++i) {
			if (cookies[i].getName().equals("accountuser")) {
				User user = this.userService.findByPhone(cookies[i].getValue()).get();
				model.addAttribute("fullname", user.getFullname());
				model.addAttribute("image", user.getImageBase64());
				model.addAttribute("ma", user.getUserId());
				model.addAttribute("address", user.getAddress());
				model.addAttribute("birthday", user.getBirthday());
				model.addAttribute("phone", user.getPhone());
				model.addAttribute("gender", user.isGender());
				model.addAttribute("role", user.isRole());
				break;
			}
		}
	}

	@PostMapping("/login")
	public String login(@RequestParam("phone") String phone, @RequestParam("password") String password, ModelMap model,
			HttpServletResponse response) {
		if (userService.findByPhone(phone).isPresent()) {
			User users = userService.findByPhone(phone).get();
			Cookie cookie = new Cookie("accountuser", users.getPhone());

			if (users.getPassword().equals(password)) {
				response.addCookie(cookie);
				cookie.setMaxAge(7 * 24 * 60 * 60);
				return "redirect:/manager";
			} else {
				model.addAttribute("errorpass", "Mật khẩu không chính xác");
				return "login/login1";
			}
		} else if (customerService.findByPhoneCus(phone).isPresent()) {
			Customer customer = customerService.findByPhoneCus(phone).get();
			Cookie cookie = new Cookie("accountcustomer", customer.getPhone());

			if (customer.getPassword().equals(password)) {
				response.addCookie(cookie);
				cookie.setMaxAge(7 * 24 * 60 * 60);
				return "redirect:/index";
			} else {
				model.addAttribute("errorpass", "Mật khẩu không chính xác");
				return "login/login1";
			}
		}
		model.addAttribute("error", "Tài khoản không tồn tại");
		return "login/login1";

	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; ++i) {
			if (cookies[i].getName().equals("accountuser")) {
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
				break;
			}
		}
		return "redirect:/login";
	}

	@GetMapping(value = "/manager/listUser")
	public String listProduct(ModelMap model, @CookieValue(value = "accountuser") String phone,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirect) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; ++i) {
				if (cookies[i].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					if (user.isRole() == false) {
						model.addAttribute("listuser", this.userService.findAll());
						model.addAttribute("username", phone);
						getName(request, model);
						return "/manager/users/listUser";
					} else {
						redirect.addFlashAttribute("fail", "Vui lòng sử dụng tài khoản admin!");
						return "redirect:/manager/listCategory";
					}
				}
			}
		}
		return "redirect:/login";
	}

	@GetMapping(value = "/manager/addUser")
	public String addCategory(ModelMap model, @CookieValue(value = "accountuser", required = false) String phone,
			HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; ++i) {
				if (cookies[i].getName().equals("accountuser")) {
					model.addAttribute("userId", new User());
					getName(request, model);
					return "/manager/users/addUser";

				}

			}
		}
		return "redirect:/login";

	}

	@PostMapping(value = "/manager/addUser")
	public String addCategory(@ModelAttribute(value = "userId") @Validated User userId, BindingResult result,
			RedirectAttributes redirect, @RequestParam("phone") String phone, ModelMap model,
			@RequestParam(value = "image") MultipartFile image) {
		if (userService.findByPhone(phone).isPresent() || customerService.findByPhoneCus(phone).isPresent()) {
			model.addAttribute("error", "Số điện thoại đã tồn tại");
			return "/manager/users/addUser";
		} else {
			User us = new User();
			us.setFullname(userId.getFullname());
			us.setImage(userId.getImage());
			us.setRole(true);
			us.setPhone(userId.getPhone());
			us.setBirthday(userId.getBirthday());
			us.setPassword(userId.getPassword());
			us.setGender(userId.isGender());
			us.setAddress(userId.getAddress());
			userService.save(us);
			redirect.addFlashAttribute("success", "Tạo tài khoản mới thành công!");
			return "redirect:/manager/listUser";
		}
	}

	@GetMapping(value = "/manager/updateUser/{userId}")
	public String updateProduct(ModelMap model, @PathVariable(name = "userId") int id, HttpServletRequest request) {
		model.addAttribute("listuser", this.userService.findAll());
		model.addAttribute("usernameID",
				this.userService.findById(id).isPresent() ? this.userService.findById(id).get() : null);
		getName(request, model);
		return "/manager/users/updateUser";
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	@PostMapping(value = "/manager/updateUser")
	public String updateProduct(@ModelAttribute(name = "usernameID") @Validated User usernameID,
			@CookieValue(value = "accountuser", required = false) String username, HttpServletRequest request,
			@RequestParam(value = "image") MultipartFile image, ModelMap model) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; ++i) {
				if (cookies[i].getName().equals("accountuser")) {
					User us = this.userService.findByPhone(cookies[i].getValue()).get();
					if (us.isRole() == true) {
						usernameID.setFullname(usernameID.getFullname());
						usernameID.setImage(userService.findById(usernameID.getUserId()).get().getImage());
						usernameID.setRole(true);
						usernameID.setPhone(usernameID.getPhone());
						usernameID.setBirthday(usernameID.getBirthday());
						usernameID.setPassword(usernameID.getPassword());
						usernameID.setGender(usernameID.isGender());
						usernameID.setAddress(usernameID.getAddress());
						this.userService.save(usernameID);
						return "redirect:/manager/listUser";
					} else {
						userService.save(usernameID);
						return "redirect:/manager/listUser";

					}
				}
			}
		}
		return "redirect:/login";
	}

	@GetMapping(value = "/manager/deleteUser/{userId}")
	public String deleteProduct(ModelMap model, @CookieValue(value = "accountuser", required = false) String phone,
			@PathVariable(name = "userId") int id, RedirectAttributes redirect, HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; ++i) {
				if (cookies[i].getName().equals("accountuser")) {
					userService.deleteById(id);
					redirect.addFlashAttribute("success", "Tài khoản đã được xóa!");
					return "redirect:/manager/listUser";
				}

			}
		}
		return "redirect:/login";
	}

	@GetMapping(value = "/manager/info")
	public String infoUser(ModelMap model, @CookieValue(value = "accountuser") String phone, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirect) {
		getName(request, model);
		return "manager/users/info";
	}

}
