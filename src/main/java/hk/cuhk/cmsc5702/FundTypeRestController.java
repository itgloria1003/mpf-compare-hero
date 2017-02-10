
package hk.cuhk.cmsc5702;

import java.io.IOException;
import java.net.MalformedURLException;
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

public class FundTypeRestController {

	private FundTypeDetailRepository repository;

	private MpfWebScrapper scrapper;

	@Autowired
	public FundTypeRestController(FundTypeDetailRepository repository) {
		this.repository = repository;
	}

	@RequestMapping("/scrape")
	public String scrape() {
		String message = "";
		ArrayList<FundTypeDetail> fundTypeDetails = new ArrayList<>();
		try {
			scrapper = new MpfWebScrapper();
			fundTypeDetails = scrapper.scrapFundTypeDetail(false, null);
			repository.save(fundTypeDetails);
			message = "Scrape successfully! No of Fund Types: " + fundTypeDetails.size();
		} catch (FailingHttpStatusCodeException | IOException e) {
			message = "Unable to scrape the targe page";
		}

		return message;
	}

	@RequestMapping("/list")
	public Iterable<FundTypeDetail> getFundTypeDetails(
			@RequestParam(value = "sort", defaultValue = "fundType") String sort,
			@RequestParam(value = "dir", defaultValue = "asc") String dir) {
		Direction direction = "desc".equals(dir) ? Direction.DESC : Direction.ASC;
		Iterable<FundTypeDetail> records = repository.findAll(new Sort(direction, sort));
		return records;

	}

}
