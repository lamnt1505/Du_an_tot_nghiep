package edu.poly.Du_An_Tot_Ngiep.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.Du_An_Tot_Ngiep.Entity.FeedBack;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {
	
	@Query(value = "select * from feedback", nativeQuery = true)
	List<FeedBack> listFeedBack();
}
