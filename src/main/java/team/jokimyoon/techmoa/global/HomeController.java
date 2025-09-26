package team.jokimyoon.techmoa.global;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value = {"/", "/home"})
	public String redirectToPosts() {
		return "redirect:/posts";
	}

}
