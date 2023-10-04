package com.sad.t5t1.project.controller;

/*	IMPORT PROGETTO	 */
import com.sad.t5t1.project.model.ClasseT1;
import com.sad.t5t1.project.model.DatabaseFileT1;
import com.sad.t5t1.project.model.ClasseT1.Level;
import com.sad.t5t1.project.model.ClasseT1.Opponent;
import com.sad.t5t1.project.services.iClasseServiceT1;
import com.sad.t5t1.project.services.iDatabaseFileServiceT1;

/*	IMPORT LIBRERIE JAVA USATE	 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.sql.Date;

/*	IMPORT LIBRERIE SPRINGBOOT USATE	 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;



@RestController
public class UserClassController {
	@Autowired
	@Qualifier("mainClasseServiceT1")
	private iClasseServiceT1 classService;

    @Autowired
	@Qualifier("mainDatabaseFileServiceT1")
    private iDatabaseFileServiceT1 fileStorageService;
	
	/*	Costruttore del Controller	 */
	public UserClassController() {}

	/*	Funzione di Utility che ha il compito di convertire una stringa di caratteri disposti in un dato pattern, in una variabile Date	 */
	public Date convertStringToDate(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date utilDate = format.parse(dateString);
			return new Date(utilDate.getTime());
		} catch (ParseException e) {
			/*	Gestione dell'eccezione in caso di formato di data non valido	 */
			e.printStackTrace();
			return null;
		}
	}
	
	/*	Funzione che permette il Download di una data Classe presente nel Database	 */
	@GetMapping("/user/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		DatabaseFileT1 databaseFile = fileStorageService.getFile(fileName);
		return ResponseEntity.ok()
							.contentType(MediaType.parseMediaType(databaseFile.getFileType()))
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.getFileName() + "\"")
							.body(new ByteArrayResource(databaseFile.getData()));
	}
	
	/*	API responsabile della visualizzazione delle classi presenti nel Database	*/
	@RequestMapping("user/api/VisualizzaClassi")
	public Iterable<ClasseT1> getAll(){
		return classService.getAll();
	}
	
	public Optional<ClasseT1> findById( int id) {
		return classService.getById(id);
	}
	
	/*	API responsabile della Ricerca Avanzata, sia Admin che User, all'interno del Database	 */
	@RequestMapping("user/api/RicercaAvanzata")
	public List<ClasseT1> getListClassesComplete(@RequestParam(value = "loc", defaultValue = "")Integer loc,
		@RequestParam(value = "complexity", required=false) String complexity,
		@RequestParam(value = "lastUpdate", defaultValue = "") String lastUpdate,
		@RequestParam(value = "recommended", required=false) String recommended){
			
			Boolean condRecommended=true;
			if((recommended == null)) {
				condRecommended=false;
			}

			if((recommended == "")) {
				condRecommended=false;
			}
			
			Opponent esempio_opponent=null;
			if(condRecommended) {
				esempio_opponent=Opponent.valueOf(recommended.toUpperCase());
			}
			
			Boolean condComplexity=true;
			if((complexity == null)) {
				condComplexity=false;
			}
			if((complexity == "")) {
				condComplexity=false;
			}
			
			Level esempio_level=null;
			if(condComplexity) {
				esempio_level=Level.valueOf(complexity.toUpperCase());
			}
			
			Date date=null;
			if(lastUpdate != "") {
				date= convertStringToDate(lastUpdate);
			}

			if(esempio_level!= null) {
				if(esempio_opponent != null) {
					if(date!= null) {
						if(loc!=null) {
							return classService.ricercaComplete(esempio_level, esempio_opponent, loc, date);
						} else {
							return classService.ricercaComplexityLastUpdateRecommended(esempio_level, date, esempio_opponent);
						}
					} else {
						if(loc!=null) {
							return classService.ricercaComplexityRecommendedLoc(esempio_level, esempio_opponent, loc);
						} else {
							return classService.ricercaComplexityRecommended(esempio_level, esempio_opponent);
						}
					}
				} else {
					if(date!= null) {
						if(loc!=null) {	
							return classService.ricercaComplexityLastUpdateLoc(esempio_level, date, loc);
						} else {
							return classService.ricercaComplexityLastUpdate(esempio_level, date);
						}
					} else {
						if(loc!=null) {
							return classService.ricercaComplexityLoc(esempio_level, loc);
						} else {
							return classService.ricercaComplexity(esempio_level);
						}
					}	
				}
			} else { 
				if(esempio_opponent != null) {
					if(date!= null) {
						if(loc!=null) {
							return classService.ricercaRecommendedLastUpdateLoc(esempio_opponent, date, loc);
						} else {
							return classService.ricercaRecommendedLastUpdate(esempio_opponent, date);
						}
					} else {
						if(loc!=null) {
							return classService.ricercaRecommendedLoc(esempio_opponent, loc);
						} else {
							return classService.ricercaRecommended(esempio_opponent);
						}
					}
				} else {
					if(date!= null) {
						if(loc!=null) {
							return classService.ricercaLastUpdateLoc(date, loc);
						} else {
							return classService.ricercaLastUpdate(date);
						}
					} else {
						if(loc!=null) {
							return classService.ricercaLoc(loc);
						} else {
							System.out.println("Tutti i campi sono null");
							return (List<ClasseT1>) classService.getAll();
						}
					}	
				}		
			}
		}
}
