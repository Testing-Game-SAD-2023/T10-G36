package com.sad.t5t1.project.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sad.t5t1.project.controller.AdminClassController;
import com.sad.t5t1.project.model.ClasseT1;

@Controller
public class AdminController {
    @Autowired
	private AdminClassController admin;

    /*  Permette la visualizzazione della pagina "localhost:8080/AdminMenu"  */
    @RequestMapping("/Admin_Menu")
    public String AdminMenu() {
        return "Admin_Menu";
    }

    /*  Permetta l'avvio della partita  */
    @RequestMapping("/Admin_Play")
    public String Admin_Play() {
        return "Admin_Play";
    }  

    @RequestMapping("/Admin_PlayGame")
    public String Admin_PlayGame() {
        return "Admin_PlayGame";
    }  

    /*  Permetta la visualizzazione dell'elenco di classi  */
    @RequestMapping("/Admin_ViewClasses")
    public ModelAndView ViewClasses() {
        Iterable<ClasseT1> list=admin.getAll();
        return new ModelAndView("Admin_ViewClasses","classet1",list);
    }

    /*  Permetta la Ricerca di una classe specifica  */
    @RequestMapping("/Admin_SearchClasses")
	public String SearchClasses() {
	    return "Admin_SearchClasses";
	}

    /*  Permetta la Ricerca Avanzata di una classe specifica  */
    @RequestMapping("/Admin_AdvancedSearchClasses")
    public String AdvancedSearchClasses() {
        return "Admin_AdvancedSearchClasses";
    }
    
    /*  Permette l'Upload di Classi nel Database  */
    @RequestMapping("/Admin_UploadClass")
    public String UploadClass() {
        return "Admin_UploadClass";
    }  

    /*  Permette la modifica di una Classe nel Database  */
    @RequestMapping("/Admin_ManageClass")
    public ModelAndView ManageClass() {
        Iterable<ClasseT1> list=admin.getAll();
        return new ModelAndView("Admin_ManageClass","classet1",list);
    }


    
    @RequestMapping("/Admin_UpdateClasses/{id}")
	public ModelAndView updateClasses(@PathVariable("id") int id) {
		
	    ModelAndView modelAndView = new ModelAndView("Admin_UpdateClasses");
	    modelAndView.addObject("id", id); // Passa l'ID come attributo del modello
	    return modelAndView;
	}

    @RequestMapping("/Admin_EliminaClasse/{id}")
	public String deleteBook(@PathVariable("id") int id) {
	    int response = admin.delete(id);
	    boolean deleted = response == 1;
	    return "redirect:/Admin_ManageClass?deleted=" + deleted;
	}
}
