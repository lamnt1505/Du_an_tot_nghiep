package edu.poly.Du_An_Tot_Ngiep.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.Du_An_Tot_Ngiep.Entity.FeedBack;
import edu.poly.Du_An_Tot_Ngiep.Repository.FeedBackRepository;
import edu.poly.Du_An_Tot_Ngiep.Service.FeedBackService;

@Service
public class FeedBackServiceImpl implements FeedBackService {

	@Autowired
	private FeedBackRepository feedBackRepository;

	@Override
	public <S extends FeedBack> S save(S entity) {
		return feedBackRepository.save(entity);
	}

	@Override
	public List<FeedBack> listFeedBack() {
		return feedBackRepository.listFeedBack();
	}

	@Override
	public List<FeedBack> findAll() {
		return feedBackRepository.findAll();
	}

	@Override
	public Optional<FeedBack> findById(Integer id) {
		return feedBackRepository.findById(id);
	}

	@Override
	public long count() {
		return feedBackRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		feedBackRepository.deleteById(id);
	}
}
