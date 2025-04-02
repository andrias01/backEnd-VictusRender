package co.edu.uco.victusresidencias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/api/v1/home")
public final class HomeController {

    @GetMapping("/login")
    public String iniciarSesion() {
        return "prueba";
    }
	
	@PostMapping("/login")
	public String validateLogin(@RequestParam String username, @RequestParam String password, Model model) {
		CountryController countryController = new  CountryController();
	    var loginResponse = countryController.validateLogin(username, password);

	    if (loginResponse.getStatusCode() == HttpStatus.OK) {
	        // Si el login es exitoso, redirige a la vista de sesión iniciada
	        return "indexSesion";
	    } else {
	    
	        return "prueba";
	    }
	}


}
