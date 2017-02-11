
package hk.cuhk.cmsc5702;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import hk.cuhk.cmsc5702.util.MpfWebScrapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/fundtype")
public class FundTypeRestController {

	private FundTypeDetailRepository repository;

	private MpfWebScrapper scrapper;

	@Autowired
	public FundTypeRestController(FundTypeDetailRepository repository) {
		this.repository = repository;
	}

	@RequestMapping(method=RequestMethod.POST,path="/action")
	public OperationResult performAction(@RequestParam(name="requestAction") String requestAction) {
		OperationResult result = new OperationResult();
		ArrayList<FundTypeDetail> fundTypeDetails = new ArrayList<>();
		try {
			scrapper = new MpfWebScrapper();
			
			if ("delete".equals(requestAction)){
				repository.deleteAll();
			}
			else if ("scrape".equals(requestAction)){
				
				fundTypeDetails = scrapper.scrapFundTypeDetail(false, null);
				repository.deleteAll();
				repository.save(fundTypeDetails);
			}
			
			
			result.setMessage(requestAction + " successfully! No. of Fund Types: " + fundTypeDetails.size());
		} catch (FailingHttpStatusCodeException | IOException e) {
			result.setMessage("Unable to perform the action "+ requestAction);
		}

		return result;
	}

	
	
	 @GetMapping("/list")
	public Iterable<FundTypeDetail> getFundTypeDetails(
			@RequestParam(value = "sort", defaultValue = "fundType") String sort,
			@RequestParam(value = "dir", defaultValue = "asc") String dir) {
		Direction direction = "desc".equals(dir) ? Direction.DESC : Direction.ASC;
		Iterable<FundTypeDetail> records = repository.findAll(new Sort(direction, sort));
		return records;

	}

  

}
