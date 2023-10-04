package com.sad.t5t1.project.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sad.t5t1.project.model.ClasseT1;
import com.sad.t5t1.project.model.ClasseT1.Level;
import com.sad.t5t1.project.model.ClasseT1.Opponent;

@Repository
@EnableJpaRepositories
public interface IClassRepository extends CrudRepository<ClasseT1, Integer> {
	List<ClasseT1> findByComplexity(Level key1);
	
	List<ClasseT1> findByLastupdateGreaterThanEqual(Date key);
	
	List<ClasseT1> findByLocGreaterThanEqual(Integer key);
	
	List<ClasseT1> findByRecommended(Opponent key);
	
	List<ClasseT1> findByComplexityAndRecommended(Level key1, Opponent key2);
	
	List<ClasseT1> findByComplexityAndLastupdateGreaterThanEqual(Level key1,Date key2);
		
	List<ClasseT1> findByComplexityAndLocGreaterThanEqual(Level key1, Integer key2);
	
	List<ClasseT1> findByRecommendedAndLastupdateGreaterThanEqual(Opponent key1,Date key2);
	
	List<ClasseT1> findByRecommendedAndLocGreaterThanEqual(Opponent key1,Integer key2);
	
	List<ClasseT1> findByLastupdateGreaterThanEqualAndLocGreaterThanEqual(Date key1,Integer key2);
	
	List<ClasseT1> findByComplexityAndRecommendedAndLastupdateGreaterThanEqual(Level key1, Opponent key3, Date key2);
	
	List<ClasseT1> findByComplexityAndLastupdateGreaterThanEqualAndLocGreaterThanEqual(Level key1, Date key2, Integer key3);
	
	List<ClasseT1> findByComplexityAndRecommendedAndLocGreaterThanEqual(Level key1, Opponent key2, Integer key3);
	
	List<ClasseT1> findByRecommendedAndLastupdateGreaterThanEqualAndLocGreaterThanEqual(Opponent key1, Date key2,Integer key3);
	
	List<ClasseT1> findByComplexityAndRecommendedAndLocGreaterThanEqualAndLastupdateGreaterThanEqual(Level key1,Opponent key2, Integer key3, Date key4);
	
	List<ClasseT1> findByUrl(String url);
}
