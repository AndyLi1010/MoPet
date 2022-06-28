package tw.com.MoPet.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import tw.com.MoPet.model.member;
import tw.com.MoPet.service.memberService;

@Controller
public class Member {

	@Autowired
	private memberService mService;

	@GetMapping(path = "/login")
	public String processMainAction() {
		return "login";
	}

	@PostMapping(path = "checklogin.controller")
	public String login(@RequestParam("userEmail") String user, @RequestParam("userPwd") String pwd, Model m,
			HttpSession session) {
		Map<String, String> errors = new HashMap<String, String>();
		m.addAttribute("errors", errors);

		if (user == null || user.length() == 0) {
			errors.put("userEmail", "Email is required");
		}
		if (pwd == null || pwd.length() == 0) {
			errors.put("userPwd", "Password is required");
		}
		if (errors != null && !errors.isEmpty()) {
			return "login";
		}

		member temp = mService.checkLogin(user, pwd);
		member temp2 = mService.findByAccount(user);
		System.out.println(temp2.getId());


		if (temp != null) {
			m.addAttribute("user", temp.getMemberEmail());
			session.setAttribute("loginOK", temp2);
			session.setAttribute("cart_ID", temp2.getId());
			return "redirect:/members/all";
		}
		errors.put("msg", "UserEmail or UserPwd is not correct.");
		return "login";
	}

	@GetMapping(path = "member/verification")
	public String verifyEmail(@RequestParam("id") Integer id, Model m) {
		member mem = mService.findById(id);
		m.addAttribute("member", mem);
		return "setPassword";
	}

	@PostMapping(path = "member/setPassword")
	public String setPassword(@RequestParam("id") Integer id, @RequestParam("password") String pwd) {
		member temp = mService.findById(id);
		temp.setMemberPwd(pwd);
		mService.setPassword(temp);
		return "redirect:/login";
	}

	@GetMapping(path = "member")
	public String member() {
		return "Register";
	}

	@PostMapping(path = "member/insert")
	public String insertMem(@RequestParam("email") String user, @RequestParam("nickName") String name,
			@RequestParam("phonenNumber") String phone, @RequestParam("shippingAddress") String address,
			@RequestParam("profile") MultipartFile file, @RequestParam("gender") String gender,
			@RequestParam("birth") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birth) throws IOException {
		String temp = new String(Base64.getEncoder().encode(file.getBytes()));
		String profile = "data:image/png;base64," + temp;
		member mem = new member();
		mem.setMemberEmail(user);
		mem.setMemberPwd("MoPetisTheBest");
		mem.setMemberName(name);
		mem.setMemberTel(phone);
		mem.setMemberAddress(address);
		mem.setMemberProfile(profile);
		mem.setMemberGender(gender);
		mem.setMemberBirth(birth);
		member tmem = mService.insert(mem);
		String toEmail = tmem.getMemberEmail();
		try {
			mService.sendEmail(toEmail, "Please verify your email", tmem);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "registerSuccess";
	}

	@GetMapping(path = "member/{id}")
	public ModelAndView findById(ModelAndView mav, @PathVariable Integer id) {
		member mem = mService.findById(id);
		mav.getModel().put("member", mem);
		mav.setViewName("editMember");
		return mav;

	}

	@PostMapping(path = "member/edit")
	public String editMember(@RequestParam("id") Integer id, @RequestParam("email") String user,
			@RequestParam("nickName") String name, @RequestParam("phonenNumber") String phone,
			@RequestParam("shippingAddress") String address, @RequestParam("profile") MultipartFile file, @RequestParam("gender") String gender,@RequestParam("birth")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birth)
			throws IOException {

		member temp = mService.findById(id);
		if (file.isEmpty()) {
			temp.setMemberProfile(temp.getMemberProfile());
		} else {
			String temp2 = new String(Base64.getEncoder().encode(file.getBytes()));
			String profile = "data:image/png;base64," + temp2;
			temp.setMemberProfile(profile);
		}
		temp.setMemberEmail(user);
		temp.setMemberName(name);
		temp.setMemberTel(phone);
		temp.setMemberAddress(address);
		temp.setMemberGender(gender);
		temp.setMemberBirth(birth);
		mService.insert(temp);
		return "redirect:/members/all";
	}

	@GetMapping(path = "member/delete/{id}")
	public String deleteById(@PathVariable Integer id) {
		mService.deleteById(id);
		return "redirect:/members/all";
	}

	@GetMapping("members/all")
	public ModelAndView viewMessages(ModelAndView mav,
			@RequestParam(name = "p", defaultValue = "1") Integer pageNumber, HttpSession session) {
		Page<member> page = mService.findByPage(pageNumber);
		Object m = mav.getModel().get("LoginOK");
		if (m == null) {
			mav.getModel().put("page", page);
			mav.setViewName("viewMembers");
			return mav;
		} else {
			mav.setViewName("login");
			return mav;
		}
	}

	@GetMapping("/logout")
	public String toLogout(HttpSession session) {
		session.removeAttribute("loginOK");
		session.removeAttribute("cart_ID");
		return "login";
	}
}
