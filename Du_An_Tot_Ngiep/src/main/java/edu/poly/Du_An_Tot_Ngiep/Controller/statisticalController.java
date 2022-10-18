package edu.poly.Du_An_Tot_Ngiep.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.Du_An_Tot_Ngiep.Entity.Customer;
import edu.poly.Du_An_Tot_Ngiep.Entity.User;
import edu.poly.Du_An_Tot_Ngiep.Service.CustomerService;
import edu.poly.Du_An_Tot_Ngiep.Service.StatisticalService;
import edu.poly.Du_An_Tot_Ngiep.Service.UserService;

@Controller
public class statisticalController {

	@Autowired
	StatisticalService statisticalService;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	void getName(HttpServletRequest request, ModelMap model) {
	  //đọc cookie từ trình duyệt
		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về danh sách các cookie
		for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
		  //so sánh phần tử i trong cookie với accountuser
			if (cookies[i].getName().equals("accountuser")) {
			    //so sánh phần tử i trong cookie với accountuser
				Customer customer = this.customerService.findByPhoneCus(cookies[i].getValue()).get();
				//sử dụng câu lệnh findbyphone để tìm số đt
				//đưa các giá trị vào model
				model.addAttribute("fullname", customer.getFullname());
				//kt vòng lap
				break;
			}
		}
	}

	@GetMapping(value = "/manager/statistical")
	public String manager(ModelMap model, @CookieValue(value = "accountuser", required = false) String phone,
			HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirect) {
		Cookie[] cookies = request.getCookies();//sử dụng rqck trả về danh sách các cookie
		if (cookies != null) {//kiểm tra cookie
			for (int i = 0; i < cookies.length; ++i) {//sd vl for để duyệt qua cookie
				if (cookies[i].getName().equals("accountuser")) {
					User user = this.userService.findByPhone(cookies[i].getValue()).get();
					//sử dụng câu lệnh findbyphone để tìm số đt
					if (user.isRole() == false) {//kiểm tra cookie
					    //đưa các giá trị vào model
						model.addAttribute("username", phone);
						model.addAttribute("fullname", user.getFullname());
						model.addAttribute("image", user.getImageBase64());
						//đưa giá trị vào model
						//đưa danh sách thống kê vào model 
						model.addAttribute("months", statisticalService.statisticalForMonth());
						model.addAttribute("years", statisticalService.statisticalForYear());
						model.addAttribute("products", statisticalService.statisticalForProduct());
						//trả về trang quản lý thống kê
						return "/manager/statistical/statistical";
					}else {//ngược lại
					    //nếu đăng nhập với tài khoản nhân viên
					    //đưa ra tb
						redirect.addFlashAttribute("fail", "Vui lòng sử dụng tài khoản admin!");
						return "redirect:/manager/listCategory";
					}
				}

			}
		}
		return "redirect:/login";
	}

}
