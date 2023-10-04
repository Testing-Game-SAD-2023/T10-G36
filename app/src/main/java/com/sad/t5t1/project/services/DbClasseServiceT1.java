package com.sad.t5t1.project.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sad.t5t1.project.model.ClasseT1;
import com.sad.t5t1.project.model.ClasseT1.Level;
import com.sad.t5t1.project.model.ClasseT1.Opponent;
import com.sad.t5t1.project.repository.IClassRepository;

@Service("mainClasseServiceT1")
public class DbClasseServiceT1 implements iClasseServiceT1 {
    @Autowired
	private IClassRepository classeRepository;

	@Autowired
	private DatabaseFileServiceT1 dbFileService;
	
	@Override
	public Iterable<ClasseT1> getAll() {
		return classeRepository.findAll();
	}
	
	@Override
	public Optional<ClasseT1> getById( int id) {
		return classeRepository.findById(id);
	}
	
	@Override
	public ClasseT1 create(ClasseT1 classej) {
		return classeRepository.save(classej);
	}
	
	public List<ClasseT1> ricercaComplexity(Level complexity) {
		return	classeRepository.findByComplexity(complexity);
	}
	
	
	public List<ClasseT1> ricercaLastUpdate(Date lastUpdate) {	
		return	classeRepository.findByLastupdateGreaterThanEqual(lastUpdate);
	}
	
	public List<ClasseT1> ricercaLoc(Integer loc) {
		return	classeRepository.findByLocGreaterThanEqual(loc);
	}
	
	public List<ClasseT1> ricercaRecommended(Opponent recomended) {
		return	classeRepository.findByRecommended(recomended);
	}
	
	public List<ClasseT1> ricercaComplexityLastUpdate(Level complexity,Date lastUpdate) {
		return	classeRepository.findByComplexityAndLastupdateGreaterThanEqual(complexity,lastUpdate);
	}
	
	public List<ClasseT1> ricercaComplexityLastUpdateRecommended(Level complexity,Date lastUpdate, Opponent recommended) {
		return	classeRepository.findByComplexityAndRecommendedAndLastupdateGreaterThanEqual(complexity,recommended, lastUpdate);
	}
	
	public List<ClasseT1> ricercaComplexityRecommended(Level complexity,Opponent recommended) {
		return	classeRepository.findByComplexityAndRecommended(complexity, recommended);
	}
	
	public List<ClasseT1> ricercaComplexityLoc(Level complexity,Integer loc){
		return classeRepository.findByComplexityAndLocGreaterThanEqual(complexity, loc);
	}
	
	public List<ClasseT1> ricercaRecommendedLastUpdate(Opponent recommended,Date lastUpdate){
		return classeRepository.findByRecommendedAndLastupdateGreaterThanEqual(recommended, lastUpdate);
	}
	
	public List<ClasseT1> ricercaRecommendedLoc(Opponent recommended,Integer loc){
		return classeRepository.findByRecommendedAndLocGreaterThanEqual(recommended, loc);
	}
	
	public List<ClasseT1> ricercaLastUpdateLoc(Date lastupdate,Integer loc){
		return classeRepository.findByLastupdateGreaterThanEqualAndLocGreaterThanEqual(lastupdate, loc);
	}
	
	public List<ClasseT1> ricercaComplexityRecommendedLoc(Level complexity, Opponent recommended, Integer loc){
		return classeRepository.findByComplexityAndRecommendedAndLocGreaterThanEqual(complexity, recommended, loc);
	}
	
	public List<ClasseT1> ricercaRecommendedLastUpdateLoc(Opponent recommended, Date lastUpdate, Integer loc){
		return classeRepository.findByRecommendedAndLastupdateGreaterThanEqualAndLocGreaterThanEqual(recommended, lastUpdate, loc);
	}
	
	public List<ClasseT1> ricercaComplexityLastUpdateLoc(Level complexity, Date lastupdate, Integer loc){
		return classeRepository.findByComplexityAndLastupdateGreaterThanEqualAndLocGreaterThanEqual(complexity, lastupdate, loc);
	}

	public List<ClasseT1> ricercaComplete(Level complexity, Opponent recommended,Integer loc, Date lastUpdate){
		return	classeRepository.findByComplexityAndRecommendedAndLocGreaterThanEqualAndLastupdateGreaterThanEqual(complexity,recommended,loc,lastUpdate);
	}
	
	public List<ClasseT1> ricercaUrl(String url){
		return classeRepository.findByUrl(url);
	}
	
	@Override
	public Optional<ClasseT1> update(int id, ClasseT1 classej) {
		Optional<ClasseT1> foundclassej=classeRepository.findById(id);
		
		if(foundclassej.isEmpty()==true) {
			return Optional.empty();
		}
		
		if(classej.getComplexity() != null) {
			foundclassej.get().setComplexity(classej.getComplexity());
		}
		
		if(classej.getLastupdate() != null) {
			foundclassej.get().setLastupdate(classej.getLastupdate());
		}
		
		if((Integer)classej.getLoc() != 0) {
			foundclassej.get().setLoc(classej.getLoc());
		}
		
		if(classej.getRecommended() != null) {
			foundclassej.get().setRecommended(classej.getRecommended());
		}
		
		classeRepository.save(foundclassej.get());
		return foundclassej;
	}
	
	@Override
	public Boolean delete(int id) {

		Optional<ClasseT1> foundclassej= classeRepository.findById(id);
		
		if(foundclassej.isEmpty()==true) {
			return false;
		}
		
		String idFile=foundclassej.get().getFileId();
		classeRepository.delete(foundclassej.get());
		dbFileService.delete(idFile);
		return true;
	}
}
