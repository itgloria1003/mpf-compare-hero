
package hk.cuhk.cmsc5702;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
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
@RequestMapping("/rest")
public class MpfRestController {

	private FundTypeDetailRepository repository;
	private MpfFundTypeStatRepository statRepository;
	private MpfFundDetailRepository fundRepository; 
	private FilterRepository filterParamRepository; 

	
	
	
	
	private MpfWebScrapper scrapper;

	@Autowired
	public MpfRestController(FundTypeDetailRepository repository,MpfFundTypeStatRepository statRepository, MpfFundDetailRepository fundRepository
			,FilterRepository filterParamRepository
			) {
		this.repository = repository;
		this.statRepository = statRepository;
		this.fundRepository = fundRepository; 
		this.filterParamRepository = filterParamRepository; 
	}

	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET},path="/action")
	public OperationResult performAction(@RequestParam(name="requestAction") String requestAction) {
		OperationResult result = new OperationResult();
		
		try {
			scrapper = new MpfWebScrapper();
			
			if ("delete".equals(requestAction)){
				repository.deleteAll();
				statRepository.deleteAll();
				fundRepository.deleteAll();
				
			}
			else if ("scrape".equals(requestAction)){
				
			 scrapAndSave();
				
				
			}
			
			
			result.setMessage(requestAction + " successfully! ");
		} catch (FailingHttpStatusCodeException | IOException e) {
			result.setMessage("Unable to perform the action "+ requestAction);
		}

		return result;
	}

	private void scrapAndSave() throws MalformedURLException, IOException {
		ArrayList<FundTypeDetail> fundTypeDetails;
		ArrayList<MpfFundTypeStat> statDetails;
		ArrayList<MpfFundDetail> fundDetails;
//		fundTypeDetails = scrapper.scrapFundTypeDetail(false, null);
//		repository.deleteAll();
//		repository.save(fundTypeDetails);
//		
//		statDetails = scrapper.scrapFundTypeStat(false, null);
//		statRepository.deleteAll();
//		statRepository.save(statDetails);
		
		fundDetails = scrapper.scrapMpfFundDetails(false, null);
		fundRepository.deleteAll();
		fundRepository.save(fundDetails);
		
		MpfFilterLists lists = evalFilterParamList(fundDetails);
		filterParamRepository.save(lists.getFundtypeList());
		filterParamRepository.save(lists.getTrusteeList());
		
		
		
	}

	private MpfFilterLists evalFilterParamList(Iterable<MpfFundDetail> fundDetails) {
		HashMap <String, FilterParam> fundtypemap= new HashMap<String, FilterParam>();
		HashMap <String, FilterParam> trusteemap= new HashMap<String, FilterParam>();
		
		MpfFilterLists list = new MpfFilterLists();
		for (MpfFundDetail d: fundDetails){
			if (fundtypemap.get(d.getFundType())==null){
				fundtypemap.put(d.getFundType(), new FilterParam(FilterParam.FUND_TYPE,d.getFundType(), d.getFundType()));
			}
			if (trusteemap.get(d.getTrustee())==null){
				trusteemap.put(d.getTrustee(), new FilterParam(FilterParam.TRUSTEE,d.getTrustee(), d.getTrustee()));
			}
		}
		list.setFundtypeList(fundtypemap.values());
		list.setTrusteeList(trusteemap.values());
		return list;
	}

	
	
	 @GetMapping("/list")
	public Iterable<FundTypeDetail> getFundTypeDetails(
			@RequestParam(value = "sort", defaultValue = "fundType") String sort,
			@RequestParam(value = "dir", defaultValue = "asc") String dir) {
		Direction direction = "desc".equals(dir) ? Direction.DESC : Direction.ASC;
		Iterable<FundTypeDetail> records = repository.findAll(new Sort(direction, sort));
		return records;

	}
	 
	 @GetMapping("/stat")
		public Iterable<MpfFundTypeStat> getFundTypeStats(
				@RequestParam(value = "asOfDate") String asOfDate) {
		 	return statRepository.findByAsOfDate(asOfDate);

		}

	
		
	 @GetMapping("/details")
	public Iterable<MpfFundDetail> getMpfFundDetails(
			@RequestParam(value = "sort", defaultValue = "scheme") String sort,
			@RequestParam(value = "dir", defaultValue = "asc") String dir) {
		Direction direction = "desc".equals(dir) ? Direction.DESC : Direction.ASC;
		Iterable<MpfFundDetail> records = fundRepository.findAll(new Sort(direction, sort));
		return records;

	}
	 
	 @GetMapping("/mpffilter")
		public MpfFilterLists getFilterLists(@RequestParam(required=false, value="filterFundType") String filterFundType,
				@RequestParam(required=false,value="filterTrustee") String filterTrustee){
		 Iterable<MpfFundDetail> records = new ArrayList<MpfFundDetail>(); 
		 
		 if (!StringUtils.isEmpty(filterFundType) && !StringUtils.isEmpty(filterTrustee)){
			  records = fundRepository.findByFundTypeAndTrusteeOrderBySchemeAscConstituentFundAsc(filterFundType, filterTrustee);
		 } else if (!StringUtils.isEmpty(filterFundType)){
			 records = fundRepository.findByFundTypeOrderBySchemeAscConstituentFundAsc(filterFundType);
		 } else if (!StringUtils.isEmpty(filterTrustee)){
			 records = fundRepository.findByTrusteeOrderBySchemeAscConstituentFundAsc(filterTrustee);
		 } else {
			 records = fundRepository.findByOrderBySchemeAscConstituentFundAsc();
			 
		 }
		 MpfFilterLists list = new MpfFilterLists();
		 list.setFundtypeList(filterParamRepository.findAllByFilterType(FilterParam.FUND_TYPE));
		 list.setTrusteeList(filterParamRepository.findAllByFilterType(FilterParam.TRUSTEE));
		 ArrayList<FilterParam> paramList = new ArrayList<FilterParam>();
		 for (MpfFundDetail d: records){
			 paramList.add(new FilterParam(FilterParam.SCHEME,d.getId() +"", d.getScheme() + " - " + d.getConstituentFund()));
			}
			list.setMpfFundIdList(paramList);		 
			return list;
			

		}
		 
		 
	 

}
