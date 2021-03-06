package hk.cuhk.cmsc5702.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableHeader;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

import ch.qos.logback.core.status.StatusUtil;
import hk.cuhk.cmsc5702.FundTypeDetail;
import hk.cuhk.cmsc5702.MpfFundDetail;
import hk.cuhk.cmsc5702.MpfFundTypeStat;
import hk.cuhk.cmsc5702.MpfNews;
import hk.cuhk.cmsc5702.ProxySetting;
import hk.cuhk.cmsc5702.SponsorDetail;


public class MpfWebScrapper {

	
	
	private static final String FUND_RISK_INDICATOR = "Fund Risk Indicator";
	private static final String ANNUALISED_RETURN_10_YEAR = "Annualised Return 10 year";
	private static final String ANNUALISED_RETURN_5_YEAR = "Annualised Return 5 year";
	private static final String OCI_5_YEAR = "OCI 5 Year";
	private static final String OCI_1_YEAR = "OCI 1 Year";
	private static final String LATEST_FER = "Latest FER";
	private static final String FUND_TYPE = "Fund Type";
	private static final String FUND = "Fund";
	private static final String CONSTITUENT = "Constituent";
	private static final String SCHEME = "Scheme";
	private static final String MPF_TRUSTEE = "MPF Trustee";

	
	private static final String HKIFA_FUND_TYPE_STAT = "http://www.hkifa.org.hk/eng/MPF-Statistics.aspx";
	private static final String FUND_TYPE_DETAIL_URL = "http://minisite.mpfa.org.hk/mpfie/en/jjfive/detailed-profile.html";
	private static final String DIV_CLASS_DRAGGER_TABLE = "//div[@class='dashed-box pad15 clearfix dragger-table']";
	private static final String TABLE_FUND_TYPE = DIV_CLASS_DRAGGER_TABLE + "/div[@class='fixed-col']/table";
	private static final String TABLE_ATTRIBUTES = DIV_CLASS_DRAGGER_TABLE + "//table[@class='scroll-content']";
	private static final String ATTR_INVESTMENT_OBJECTIVE = "Investment Objective";
	private static final String ATTR_INVESTMENT_INSTRUMENTS = "Investment Instruments";
	private static final String ATTR_RISK_LEVEL = "Risk Level";
	private static final String ATTR_MAJOR_RISKS = "Major Risks";
	private static final String ATTR_FEATURE = "Features/ Points to Note";
	private static final String [] ATTR_LIST = {ATTR_INVESTMENT_OBJECTIVE,ATTR_INVESTMENT_INSTRUMENTS,ATTR_RISK_LEVEL,ATTR_MAJOR_RISKS,ATTR_FEATURE}; 
	
	private static final String SPONSOR_LIST_URL = "http://www.mpfexpress.com/en-US/MPFSchemes" ;
	private static final String DIV_CLASS_SPONSOR_TABLE = "//div[@class='provider-list']";
	private static final String SPONSOR_TABLE_ATTRIBUTES = DIV_CLASS_SPONSOR_TABLE+ "//table [@class='table-style-1']";
	private static final String ATTR_SPONSOR = "Sponsor";
	private static final String ATTR_TRUSTEE = "Trustee";
	private static final String ATTR_SCHEME = "Scheme";
	private static final String ATTR_CONTACT_INFO = "Contact Info";
	private static final String [] ATTR_SPONSOR_LIST = {ATTR_SPONSOR,ATTR_TRUSTEE,ATTR_SCHEME,ATTR_CONTACT_INFO}; 
	
	
	
	public static final void main(String argsp[]){
		MpfWebScrapper s = new MpfWebScrapper();
		try {
		s.scrapMpfFundDetails(false, null);
			//s.scrapeNews(false, null);
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public ArrayList<MpfFundDetail> scrapMpfFundDetails(boolean useProxy, ProxySetting setting) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		ArrayList<MpfFundDetail> list = new ArrayList<MpfFundDetail>();
		WebClient webClient = createWebClient(useProxy, setting);
		final HtmlPage page = webClient.getPage("http://cplatform.mpfa.org.hk/MPFA/eng/cf_list.jsp");
		 final HtmlTable table = (HtmlTable) page.getByXPath("//div[@class='tableContainer']/table[@class='txt']").get(0);
		 
		 
		 List<HtmlTableRow> rows = table.getBodies().get(0).getRows();
		 
		 
		 HashMap<String, Integer> attributeColumnMap = new HashMap<String, Integer>() ;
			attributeColumnMap.put(MPF_TRUSTEE, 0);
			attributeColumnMap.put(SCHEME, 1);
			attributeColumnMap.put(CONSTITUENT, 2);
			attributeColumnMap.put(FUND_TYPE, 3);
			attributeColumnMap.put(LATEST_FER, 4);
			attributeColumnMap.put(OCI_1_YEAR, 5);
			attributeColumnMap.put(OCI_5_YEAR, 6);
			attributeColumnMap.put(FUND_RISK_INDICATOR, 7);
			attributeColumnMap.put(ANNUALISED_RETURN_5_YEAR, 8);
			attributeColumnMap.put(ANNUALISED_RETURN_10_YEAR, 9);
			
			for (int rowIdx = 0 ; rowIdx< rows.size(); rowIdx++) {
	        	MpfFundDetail d = new MpfFundDetail();
	        	for (String key : attributeColumnMap.keySet() ){
	        		
					int colIdx = attributeColumnMap.get(key);
					String valueStr = rows.get(rowIdx).getCell(colIdx).asText(); 
					if (MPF_TRUSTEE.equals(key)){
						d.setTrustee(valueStr);
					} else if (SCHEME.equals(key)){
						d.setScheme(valueStr);
					} else if (CONSTITUENT.equals(key)){
						d.setConstituentFund(valueStr);
					} else if (FUND_TYPE.equals(key)){
						d.setFundType(valueStr);
					} else if (LATEST_FER.equals(key)){
						try{ 
							d.setLatestFER(new BigDecimal(valueStr.replaceAll("%", "")).movePointLeft(2));
						} catch (Exception e){
							d.setLatestFER(null);
						}
					} else if (OCI_1_YEAR.equals(key)){
						try { 
							d.setOci1yr(new BigDecimal(valueStr.replaceAll("$", "")));
						} catch (Exception e){
							d.setOci1yr(null);
						}
					} else if (OCI_5_YEAR.equals(key)){
						try { 
							d.setOci5yr(new BigDecimal(valueStr.replaceAll("$", "")));
						} catch (Exception e){
							d.setOci5yr(null);
						}
					} else if (FUND_RISK_INDICATOR.equals(key)){
						try{ 
							d.setFundRiskIndicator(new BigDecimal(valueStr.replaceAll("%", "")).movePointLeft(2));
							if (d.getFundRiskIndicator()!=null){
								if (d.getFundRiskIndicator().doubleValue()>=0.18){
									d.setRiskCat(3);
								} else if (d.getFundRiskIndicator().doubleValue()>=0.08){
									d.setRiskCat(2);
								} else {
									d.setRiskCat(1);
								}
							}
						} catch (Exception e){
							d.setFundRiskIndicator(null);
						}
						
					} else if (ANNUALISED_RETURN_5_YEAR.equals(key)){
						try { 
							d.setAnnualReturn5yr(new BigDecimal(valueStr.replaceAll("%", "")).movePointLeft(2));
						} catch (Exception e){
							d.setAnnualReturn5yr(null);
						}
					} else if (ANNUALISED_RETURN_10_YEAR.equals(key)){
						try { 
							d.setAnnualReturn10yr(new BigDecimal(valueStr.replaceAll("%", "")).movePointLeft(2));
						} catch (Exception e){
							d.setAnnualReturn10yr(null);
						}
					}
			
					
	        	}
	        	
	        	list.add(d);
			}
	        	
		// for each group type, find the mean 
		
		return list;
	}
	
	
	public ArrayList<MpfFundTypeStat> scrapFundTypeStat(boolean useProxy, ProxySetting setting) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		ArrayList<MpfFundTypeStat> statList= new ArrayList<>();
		WebClient webClient = createWebClient(useProxy, setting);
		final HtmlPage page = webClient.getPage(HKIFA_FUND_TYPE_STAT);
		
		final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='border fullwidth normal']").get(0);
		
		List<HtmlTableRow> rows = table.getBodies().get(0).getRows();
		
		// the second row is the header 
		HtmlTableRow headerRow = rows.get(1);
		HashMap<String, Integer> attributeColumnMap = new HashMap<String, Integer>() ;
		attributeColumnMap.put("MPF Conservative Fund ", 1);
		attributeColumnMap.put("Money Market Fund and others (3)", 2);
		attributeColumnMap.put("Guaranteed Fund", 3);
		attributeColumnMap.put("Bond Fund", 4);
		attributeColumnMap.put("Mixed Assets Fund", 5);
		attributeColumnMap.put("Equity Fund", 6);
		// detail 
		for (int detailIdx = 2 ; detailIdx <rows.size(); detailIdx++){
			
			for (String key:attributeColumnMap.keySet()){
				MpfFundTypeStat stat = new MpfFundTypeStat();
				stat.setAsOfDate(rows.get(detailIdx).getCell(0).asText());
				int colIdx = attributeColumnMap.get(key);
				stat.setFundType(key);
				String str = rows.get(detailIdx).getCell(colIdx).asText(); 
		
				stat.setNetAssetValue(new BigDecimal(str.replaceAll(" ", "")));
				statList.add(stat);
			}
			
		}
		
		webClient.close();
		
		
		return statList;
	}
	public ArrayList<FundTypeDetail> scrapFundTypeDetail(boolean useProxy, ProxySetting setting) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		ArrayList<FundTypeDetail> fundtypeDetails = new ArrayList<>();
		WebClient webClient = createWebClient(useProxy, setting);
		final HtmlPage page = webClient.getPage(FUND_TYPE_DETAIL_URL);
		
		
		
		// first table is the fixed-col table 
        final HtmlTable fixColTable = (HtmlTable) page.getByXPath(TABLE_FUND_TYPE).get(0); 
        HtmlTableBody htmlTableBody = fixColTable.getBodies().get(0);
        List<HtmlTableRow> rows = htmlTableBody.getRows();
        for (int i = 0 ; i< rows.size(); i++) {
        	String fundType = rows.get(i).asText();
        	FundTypeDetail detail = new FundTypeDetail();
        	detail.setFundType(fundType);
        	fundtypeDetails.add(detail);
        }

        //		second table is the scrollable table
        final HtmlTable scrollableTable = (HtmlTable) page.getByXPath(TABLE_ATTRIBUTES).get(0);
        
        HtmlTableHeader scrollableTableHeader = scrollableTable.getHeader();
        HtmlTableBody scrollableTableBody = scrollableTable.getBodies().get(0);
        HashMap<String, Integer> attributeColumnMap = new HashMap<String, Integer>() ;
        // attribute lists
        List<HtmlTableCell> cells = scrollableTableHeader.getRows().get(0).getCells();
        for (int colIdx = 0 ; colIdx <cells.size() ; colIdx ++){
        	String attrName = cells.get(colIdx).asText() ;
        	for (String ATTR_NAME: ATTR_LIST){
        		if (ATTR_NAME.equals(attrName)){
        			attributeColumnMap.put(attrName,  colIdx);	
        		}
        	}
        }
        		
        		
        List<HtmlTableRow> scrollableTableRows = scrollableTableBody.getRows();
        for (int rowIdx = 0 ; rowIdx< scrollableTableRows.size(); rowIdx++) {
        	FundTypeDetail detail = fundtypeDetails.get(rowIdx);
        	for (String attrName: ATTR_LIST){
        			int colIdx = attributeColumnMap.get(attrName);
        			String attributeValue = scrollableTableRows.get(rowIdx).getCell(colIdx).asText();
        			if (ATTR_INVESTMENT_OBJECTIVE.equals(attrName)){
        				detail.setInvestObjective(attributeValue);	
        			} else if (ATTR_FEATURE.equals(attrName)){
        				detail.setFeature(attributeValue);
        			} else if ( ATTR_INVESTMENT_INSTRUMENTS.equals(attrName)){
        				detail.setInvestInstruments(attributeValue);
        			} else if (ATTR_MAJOR_RISKS.equals(attrName)){
        				detail.setMajorRisk(attributeValue);
        			} else if (ATTR_RISK_LEVEL.equals(attrName)){
        				detail.setRiskLevel(attributeValue);
        			}
        			
        	}
        	
        	        	
        }
        
        webClient.close();
       return fundtypeDetails; 
        
		
	}
	private WebClient createWebClient(boolean useProxy, ProxySetting setting) {
		WebClient webClient = null;
		if (useProxy && setting !=null) {
			webClient = new WebClient(BrowserVersion.CHROME, setting.getProxy(), setting.getPort());
			if (setting.getLogin() != null) {
				// set proxy username and password
				final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient
						.getCredentialsProvider();
				credentialsProvider.addCredentials(setting.getLogin(), setting.getPassword());
			}
		} else {
			webClient = new WebClient(BrowserVersion.CHROME);
		}
		return webClient;
	}

	
	public ArrayList<SponsorDetail> scrapSponsorDetail (boolean useProxy, ProxySetting setting) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		ArrayList<SponsorDetail> sponsorDetail = new ArrayList<>();
		WebClient webClient = null;
		if (useProxy && setting !=null) {
			webClient = new WebClient(BrowserVersion.CHROME, setting.getProxy(), setting.getPort());
			if (setting.getLogin() != null) {
				// set proxy username and password
				final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient
						.getCredentialsProvider();
				credentialsProvider.addCredentials(setting.getLogin(), setting.getPassword());
			}
		} else {
			webClient = new WebClient(BrowserVersion.CHROME);
		}
		final HtmlPage page = webClient.getPage(SPONSOR_LIST_URL);

		 final HtmlTable scrollableTable = (HtmlTable) page.getByXPath(SPONSOR_TABLE_ATTRIBUTES).get(0);

	        HtmlTableHeader scrollableTableHeader = scrollableTable.getHeader();
	        HtmlTableBody scrollableTableBody = scrollableTable.getBodies().get(0);
	        HashMap<String, Integer> attributeColumnMap = new HashMap<String, Integer>() ;
	        // attribute lists
	        List<HtmlTableCell> cells = scrollableTableHeader.getRows().get(0).getCells();
	        for (int colIdx = 0 ; colIdx <cells.size() ; colIdx ++){
	        	String attrName = cells.get(colIdx).asText() ;
	        	for (String ATTR_NAME: ATTR_SPONSOR_LIST){
	        		if (ATTR_NAME.equals(attrName)){
	        			attributeColumnMap.put(attrName,  colIdx);	
	        		}
	        	}
	        }
	        List<HtmlTableRow> scrollableTableRows = scrollableTableBody.getRows();
	        for (int rowIdx = 0 ; rowIdx< scrollableTableRows.size(); rowIdx++) {
	        	SponsorDetail detail = sponsorDetail.get(rowIdx);
	        	for (String attrName: ATTR_SPONSOR_LIST){
	        			int colIdx = attributeColumnMap.get(attrName);
	        			String attributeValue = scrollableTableRows.get(rowIdx).getCell(colIdx).asText();
	        			if (ATTR_SPONSOR.equals(attrName)){
	        				detail.setSponsorName(attributeValue);	
	        			} else if (ATTR_TRUSTEE.equals(attrName)){
	        				detail.setTrustee(attributeValue);
	        			} else if ( ATTR_SCHEME.equals(attrName)){
	        				detail.setScheme(attributeValue);
	        			} else if (ATTR_CONTACT_INFO.equals(attrName)){
	        				detail.setContactInfo(attributeValue);
	        			}
	        			
	        	}
	        	
	        	        	
	        }
		
		return sponsorDetail;
		
	}

	
}