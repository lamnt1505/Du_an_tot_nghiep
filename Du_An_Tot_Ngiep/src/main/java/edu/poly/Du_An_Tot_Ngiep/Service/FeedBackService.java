package edu.poly.Du_An_Tot_Ngiep.Service;

import java.util.List;
import java.util.Optional;

import edu.poly.Du_An_Tot_Ngiep.Entity.FeedBack;

public interface FeedBackService {

	void deleteById(Integer id);

	long count();

	Optional<FeedBack> findById(Integer id);

	List<FeedBack> findAll();

	<S extends FeedBack> S save(S entity);

	List<FeedBack> listFeedBack();
}
