package com.sad.t5t1.project.controller;

/*	IMPORT PROGETTO	 */
import com.sad.t5t1.project.model.ClasseT1;
import com.sad.t5t1.project.model.ClasseT1.Level;
import com.sad.t5t1.project.model.ClasseT1.Opponent;
import com.sad.t5t1.project.model.DatabaseFileT1;
import com.sad.t5t1.project.model.Match_Result;
import com.sad.t5t1.project.services.iClasseServiceT1;
import com.sad.t5t1.project.services.iDatabaseFileServiceT1;
import com.sad.t5t1.project.services.iMatchService;

/*	IMPORT LIBRERIE JAVA USATE	 */
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

/*	IMPORT LIBRERIE SPRINGBOOT USATE	 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class AdminClassController {
	@Autowired
	@Qualifier("mainClasseServiceT1")
	private iClasseServiceT1 classService;

	@Autowired
	@Qualifier("mainDatabaseFileServiceT1")
    private iDatabaseFileServiceT1 fileStorageService;

	@Autowired
	@Qualifier("mainMatchService")
	private iMatchService matchService;
    
	/*	Costruttore del Controller	 */
    public AdminClassController() {}

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
	
	/*	API responsabile dell'upload di un file nel Database	*/
	ObjectMapper objectMapper= new ObjectMapper();
	@RequestMapping(value="/admin/api/upload",method= RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam(required=true, value="file") MultipartFile file, @RequestParam(required=true, value="jsondata")String jsondata) throws IOException  {
		String namefile= file.getOriginalFilename();
	    String namefolder= namefile.substring(0, namefile.length() - 5);
	    String url= "./"+namefolder+"/"+namefile;

	    if(classService.ricercaUrl(url).isEmpty()==false) {	
	    	System.out.println("problema URL vuoto");
	    	return ResponseEntity.badRequest().body("Url already exist");
	    }

        ClasseT1 classej= objectMapper.readValue(jsondata,ClasseT1.class);
        if(classej.getLoc()== 0) {
            System.out.println("problema Loc vuoto");
	    	return ResponseEntity.badRequest().body("Insert Loc greater than zero!");
	    }

        if(classej.getLastupdate()== null) {
            System.out.println("problema Data vuoto");
	    	return ResponseEntity.badRequest().body("Insert a valid Last Update");
	    }

        DatabaseFileT1 fileName = fileStorageService.storeFile(file);
	    String idFile=fileName.getId();
   
	    classej.setUrl("./"+namefolder+"/"+namefile);
	    classej.setFileId(idFile);
	    classService.create(classej);

	    return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
	}

	/*	API responsabile della visualizzazione delle classi presenti nel Database	*/
	@RequestMapping("admin/api/VisualizzaClassi")
	public Iterable<ClasseT1> getAll(){
		return classService.getAll();
	}

	/*	API responsabile della Ricerca Avanzata, sia Admin che User, all'interno del Database	 */
	@RequestMapping("admin/api/RicercaAvanzata")
	public List<ClasseT1> getListClassesComplete(
		@RequestParam(value = "loc", defaultValue = "")Integer loc,
		@RequestParam(value = "complexity", required=false) String complexity,
		@RequestParam(value = "lastUpdate", defaultValue = "") String lastUpdate,
		@RequestParam(value = "recommended", required=false) String recommended) {
			
			Boolean condRecommended=true;
			if((recommended == null) || (recommended == "")) {
				condRecommended=false;
			}

			Opponent esempio_opponent=null;
			if(condRecommended) {
				esempio_opponent=Opponent.valueOf(recommended.toUpperCase());
			}

			Boolean condComplexity=true;
			if((complexity == null) || (complexity == "")) {
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
						}else {
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

	/*	API responsabile della modifica di una classe specifica presente nel Database	*/
	@PutMapping(value="/admin/api/ModificaClasse/{id}")
	public ClasseT1 update(@PathVariable int id, @RequestBody ClasseT1 classej) {
		System.out.println("-----------id:"+id);
		System.out.println("Classe: "+ classej);
		Optional<ClasseT1> updatephoto= classService.update(id, classej);
	
		if(updatephoto.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"item not found");
		}

		return updatephoto.get();
	}
	
	
	/*	API responsabile dell'eliminazione di una classe specifica presente nel Database	*/
	@RequestMapping(value="admin/api/EliminaClasse/{id}", method= RequestMethod.DELETE)
	public int delete(@PathVariable int id) {
		Boolean isDeleted= classService.delete(id);  
		if(isDeleted==false) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"item not found");	
		} else {
			return 0;
		}
	}

	@RequestMapping(value="admin/api/MatchStart")
	public ResponseEntity<Object> MatchStart(
		@RequestParam(required=true, value="fileID")String fileID, 
		@RequestParam(required=true, value="Opponent")String Opponent) throws IOException  {

			Match_Result Match = new Match_Result();
			Match.setFileId(fileID);
			Match.setOpponent(Opponent);

			matchService.create(Match);

			System.out.println(fileID);
			System.out.println(Opponent);
			return new ResponseEntity<>("Match created!", HttpStatus.OK);
	}
}
