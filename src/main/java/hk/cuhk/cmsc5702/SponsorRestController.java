package hk.cuhk.cmsc5702;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import hk.cuhk.cmsc5702.util.MpfWebScrapper;

@RestController
@RequestMapping("/sponsor")
public class SponsorRestController {

	private SponsorDetailRepository repository;
	
	private MpfWebScrapper scrapper;
	
	@Autowired
	public SponsorRestController(SponsorDetailRepository repository) {
		this.repository = repository;
	}
	
	@RequestMapping("/scrapeSponsor")
	public String scrape() {
		String message = "";
		ArrayList<SponsorDetail> sponsorDetail = new ArrayList<>();
	
		try {
			scrapper = new MpfWebScrapper();
			sponsorDetail = scrapper.scrapSponsorDetail(false, null);
			repository.save(sponsorDetail);
				message = "Scrape successfully! No of Sponsors: " + sponsorDetail.size();
			} catch (FailingHttpStatusCodeException | IOException e) {
				message = "Unable to scrape the targe page";
			}
			return message;
	}
	@RequestMapping("/sponsorList")
	public Iterable<SponsorDetail> getSponsorDetail(
			@RequestParam(value = "sort", defaultValue = "sponsor") String sort,
			@RequestParam(value = "dir", defaultValue = "asc") String dir) {
		Direction direction = "desc".equals(dir) ? Direction.DESC : Direction.ASC;
		Iterable<SponsorDetail> records = repository.findAll(new Sort(direction, sort));
		return records;

	}
}
