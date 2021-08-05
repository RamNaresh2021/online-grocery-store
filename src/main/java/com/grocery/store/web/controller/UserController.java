package com.grocery.store.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.grocery.store.dto.UserDTO;
import com.grocery.store.models.binding.UserRegisterBindingModel;
import com.grocery.store.service.UserService;
import com.grocery.store.web.annotations.PageTitle;

import static com.grocery.store.util.constants.AppConstants.*;

import javax.validation.Valid;
@Controller
public class UserController extends BaseController{
		
	@Autowired
	private UserService userService;
	
	private final ModelMapper modelMapper=new ModelMapper();
	

	@GetMapping("/register")
	@PageTitle(REGISTER)
	public ModelAndView renderRegister(@ModelAttribute(name=MODEL) UserRegisterBindingModel model, ModelAndView modelAndView) {
		modelAndView.addObject(MODEL, model);
		return view("register", modelAndView);
	}
	
	@PostMapping("/register")
	public ModelAndView register(@Valid @ModelAttribute(name=MODEL) UserRegisterBindingModel model,
			  BindingResult result, ModelAndView modelAndView) {
		
		if(!result.hasErrors()) {
			if(model.getPassword().equals(model.getConfirmPassword())) {
				this.userService.register(this.modelMapper.map(model, UserDTO.class));
				
			
			} else {
				modelAndView.addObject(MODEL, model);
				return view("register", modelAndView);
			}
		}
	
//		if(!model.getPassword().equals(model.getConfirmPassword()) && result.hasErrors() &&
//				 this.userService.register(this.modelMapper.map(model, UserDTO.class)) == null) {
//			modelAndView.addObject(MODEL, model);
//			return view("register", modelAndView);
//		}
		return redirect("/login");
		
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
