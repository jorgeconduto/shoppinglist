package pt.jcbox.shoppinglist.shoppinglist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String login() {
		return "login";
	}
}
