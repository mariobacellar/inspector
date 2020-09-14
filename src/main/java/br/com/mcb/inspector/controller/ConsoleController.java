package br.com.mcb.inspector.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
/**
 * https://spring.io/guides/gs/serving-web-content/
 * https://github.com/spring-projects/greenhouse
 * @author mario.bacellar
 *
 */
@Controller
public class ConsoleController {

	private static final Logger log = Logger.getLogger(ConsoleController.class);
		
	@GetMapping("/console")
    public String console(@RequestParam(name="name", required=false, defaultValue="Usu�rio") String name, Model model) {
        model.addAttribute("name", name);
        return "console";
    }

}
