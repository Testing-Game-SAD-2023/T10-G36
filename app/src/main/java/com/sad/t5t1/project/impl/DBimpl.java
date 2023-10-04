package com.sad.t5t1.project.impl;

import org.springframework.web.bind.annotation.RequestParam;
import com.sad.t5t1.project.services.DBInterface;

public class DBimpl implements DBInterface {
    /*	Funzione che si occupa del Login	*/
    @Override
	public Integer getID(@RequestParam("email") String email, @RequestParam("password") String password) {
		
		/*	A scopo dimostrativo vengono inizializzati 3 casi di base:
		 * 		1) EMAIL: admin		PASSWORD: any
		 * 			Login nel programma con accesso a tutte le funzioni di amministrazione oltre a quelle di utente
		 * 
		 * 		2) EMAIL: User	PASSWORD: any
		 * 			Tentativo di Login nel programma con credenziali presenti all'interno del Database
		 * 
		 * 		3) EMAIL: any		PASSWORD: any
		 * 			Tentativo di Login nel programma con credenziali non presenti nel Database
		 */

		if(email.equals("user")) return 123;
		else if(email.equals("admin")) return 0;
        else return -1;
	}
}
