package edu.poly.Du_An_Tot_Ngiep.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.Du_An_Tot_Ngiep.Entity.FeedBack;
import edu.poly.Du_An_Tot_Ngiep.Service.FeedBackService;

@RestController
public class ManagerRestController {

	@Autowired
	private FeedBackService feedBackSerice;

//	tabel FeedBack
	@GetMapping("/manager/listFeedBack")
	public ResponseEntity<?> listFeedBack() {
		return ResponseEntity.ok(this.feedBackSerice.listFeedBack());
	}

	@PostMapping("/manager/addFeedBackAjax")
	public List<FeedBack> addFeedBackAjax(@RequestBody FeedBack feedBack) {
		this.feedBackSerice.save(feedBack);
		return this.feedBackSerice.listFeedBack();
	}

	@PostMapping("/manager/deleteFeedBack/{idFeedBack}")
	public List<FeedBack> deleteFeedBack(@PathVariable("idFeedBack") int id) {
		this.feedBackSerice.deleteById(id);
		return this.feedBackSerice.listFeedBack();
	}
}
