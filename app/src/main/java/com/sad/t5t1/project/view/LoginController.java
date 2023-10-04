package com.sad.t5t1.project.view;

import com.sad.t5t1.project.services.*;
import com.sad.t5t1.project.impl.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    /*  Comando che imposta come pagina iniziale al collegamento su "localhost:8080" la pagina "login.html" */
    @RequestMapping("/")
    public String index() {
        return "loginPage";
    }



    
    /*  Funzione richiamata durante la fase di login.
     *      All'interno di questa funzione viene verificata la presenza delle credenziali all'interno del DB.
     *          [CASO 1]    Le credenziali sono presenti nel DB.
     *          Viene verificato il ruolo dell'utente e reindirizzato alla pagina a cui ha accesso.
     * 
     *          [CASO 2]    Le credenziali non sono presenti nel DB.
     *          L'utente viene reindirizzati ad una pagina che segnalerà l'errato accesso.                  */
    @PostMapping("/checklogin")
	public ModelAndView Login(@RequestParam("email") String email, @RequestParam("password") String password) {
		
        /*  Richiamo l'interfaccia per ottenere l'ID dell'utente che sta cercando di effettuare l'accesso.
         *      [CASO 1]    ID > 0
         *          Viene confermata l'operazione di accesso e reindirizzati sulla pagina "UserMenu.html".
         *
         *      [CASO 2]    ID = 0
         *          Viene confermata l'operazione di accesso e reindirizzati sulla pagina "AdminMenu.html".
         * 
         *      [CASO 3]    ID = -1
         *          Viene rifiutata l'operazione di accesso e reindirizzati sulla pagina "loginPageError.html"  */
		DBInterface DB = new DBimpl();
		
		Integer ID = DB.getID(email,password);
		
        if(ID < 0) {
            ModelAndView modelAndView = new ModelAndView("loginPageError");
			return modelAndView;
        } else if(ID > 0) {
            ModelAndView modelAndView = new ModelAndView("User_Menu","model",ID);	
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("Admin_Menu");	
		    return modelAndView;
        }	
	}
}
