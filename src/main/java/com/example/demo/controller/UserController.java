package com.example.demo.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;





@Controller
@RequestMapping("/users")
public class UserController {

	private UserRepository userRepository;
	
	@Autowired
	public UserController(UserRepository userRepository) {
	super();
	this.userRepository = userRepository;
}

	@GetMapping("/info/{id}")
	public String getInfo(@PathVariable("id") int id, Model model) {

		model.addAttribute("user",userRepository.findById(id));
		return "info";
	}

	@GetMapping("/create")
	public String createUser() {
		return "user_form";
	}

	@PostMapping("/add")
	public String addUser(@ModelAttribute(name = "user") User user, Model model) {

		userRepository.save(user);
		return "redirect:/users/all";
	}

	@GetMapping("/all")
	public String users(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "users";
	}
	

	
	//HomeTask1
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id")int id) {
//		users.removeIf(u->u.getId()==id);
		userRepository.deleteById(id);
		return "redirect:/users/all";
	}
	
	@GetMapping("/set/{id}")
	public String set(@PathVariable("id")int id, Model model) {

//		User user = users.stream().filter(u->u.getId()==id).findFirst().get();
		model.addAttribute("user", userRepository.findById(id).get());
		return "setUser_form";
	}
	
	@PostMapping("/change")
	public String changeUser(@ModelAttribute(name = "user") User user,Model model) {
				
//        User setUser = users.stream().filter(u->u.getId()==user.getId()).findFirst().get();
		int id = user.getId();
		User setUser = userRepository.findById(id).get();
		setUser.setName(user.getName());
		setUser.setAge(user.getAge());
		setUser.setGender(user.getGender());
		userRepository.save(setUser);
		
		return "redirect:/users/all";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam(name = "name") String name, Model model) {
		
		List<User> users = userRepository.findByNameContaining(name);
		model.addAttribute("users", users);
		return "users";
	}
	

}

