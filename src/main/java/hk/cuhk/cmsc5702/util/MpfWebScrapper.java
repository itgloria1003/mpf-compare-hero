package hk.cuhk.cmsc5702.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableHeader;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import hk.cuhk.cmsc5702.FundTypeDetail;
import hk.cuhk.cmsc5702.ProxySetting;
import hk.cuhk.cmsc5702.SponsorDetail;


public class MpfWebScrapper {

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


	public ArrayList<FundTypeDetail> scrapFundTypeDetail(boolean useProxy, ProxySetting setting) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		ArrayList<FundTypeDetail> fundtypeDetails = new ArrayList<>();
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
        
        
       return fundtypeDetails; 
        
		
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