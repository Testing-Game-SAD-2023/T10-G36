package com.sad.t5t1.project.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sad.t5t1.project.controller.UserClassController;
import com.sad.t5t1.project.model.ClasseT1;


@Controller
public class UserController {
    @Autowired
	private UserClassController user;
    
    /*  Permette la visualizzazione della pagina "localhost:8080/AdminMenu"  */
    @RequestMapping("/User_Menu")
    public String User_Menu() {
        return "User_Menu";
    }

    /*  Permetta l'avvio della partita  */
    @RequestMapping("/User_Play")
    public String User_Play() {
        return "User_Play";
    }  

    @RequestMapping("/User_PlayGame")
    public String User_PlayGame() {
        return "User_PlayGame";
    }  

    /*  Permetta la visualizzazione dell'elenco di classi  */
    @RequestMapping("/User_ViewClasses")
    public ModelAndView ViewClasses() {
        Iterable<ClasseT1> list=user.getAll();
        return new ModelAndView("User_ViewClasses","classet1",list);
    }

    /*  Permetta la Ricerca di una classe specifica  */
    @RequestMapping("/User_SearchClasses")
	public String SearchClasses() {
	    return "User_SearchClasses";
	}
}
