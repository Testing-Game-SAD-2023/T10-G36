package com.sad.t5t1.project.services;

import java.sql.Date; 
import java.util.List;
import java.util.Optional;

import com.sad.t5t1.project.model.ClasseT1;
import com.sad.t5t1.project.model.ClasseT1.Level;
import com.sad.t5t1.project.model.ClasseT1.Opponent;

public interface iClasseServiceT1 {
    public Iterable<ClasseT1> getAll();
	public Optional<ClasseT1> getById( int id);
	public ClasseT1 create(ClasseT1 photo);
	public Optional<ClasseT1> update(int id, ClasseT1 photo);
	public Boolean delete(int id);
	
	public List<ClasseT1> ricercaComplexity(Level complexity);
	
	public List<ClasseT1> ricercaLastUpdate(Date lastUpdate);
	
	public List<ClasseT1> ricercaLoc(Integer loc);
	
	public List<ClasseT1> ricercaRecommended(Opponent recomended);
	
	public List<ClasseT1> ricercaComplexityLastUpdate(Level complexity,Date lastUpdate);
	
	public List<ClasseT1> ricercaComplexityRecommended(Level complexity,Opponent recommended);
	
	public List<ClasseT1> ricercaComplexityLoc(Level complexity,Integer loc);
	
	public List<ClasseT1> ricercaRecommendedLastUpdate(Opponent recommended,Date lastUpdate);
	
	public List<ClasseT1> ricercaRecommendedLoc(Opponent recommended,Integer loc);
	
	public List<ClasseT1> ricercaLastUpdateLoc(Date lastupdate,Integer loc);
	
	public List<ClasseT1> ricercaComplexityLastUpdateRecommended(Level complexity, Date lastUpdate,Opponent recommended);
	
	public List<ClasseT1> ricercaComplexityRecommendedLoc(Level complexity, Opponent recommended, Integer loc);
	
	public List<ClasseT1> ricercaComplexityLastUpdateLoc(Level complexity, Date lastupdate, Integer loc);
	
	public List<ClasseT1> ricercaRecommendedLastUpdateLoc(Opponent recommended, Date lastUpdate, Integer loc);
	
	public List<ClasseT1> ricercaComplete(Level complexity, Opponent recommended,Integer loc, Date lastUpdate);
	
	public List<ClasseT1> ricercaUrl(String url);
}
