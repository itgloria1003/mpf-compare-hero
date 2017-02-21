package hk.cuhk.cmsc5702.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import hk.cuhk.cmsc5702.MpfFundDetail;

public class MpfFundAnalyst{
	

	public static void main (String args[]) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
//		MpfWebScrapper s = new MpfWebScrapper();
//		SummaryStatistics ferStat = new SummaryStatistics();
//		
//		ArrayList<MpfFundDetail> scrapFundTypeDetail = s.scrapMpfFundDetails(false, null);
//		
//		
//		
//		ArrayList<MpfFundDetail> withFerDetails = new ArrayList<MpfFundDetail>();
//		ArrayList <Double>ferSeries = new ArrayList<Double>();
//		
//		ArrayList<MpfFundDetail> withAnn5yearDetails = new ArrayList<MpfFundDetail>();
//		ArrayList <Double>ann5YearSeries = new ArrayList<Double>();
//		
//	
//		
//		ArrayList<MpfFundDetail> withAnn5yearDetailsEntity = new ArrayList<MpfFundDetail>();
//		ArrayList <Double>ann5YearSeries = new ArrayList<Double>();
//		
//		
//		for (MpfFundDetail d :scrapFundTypeDetail){
//			
//			if (d.getLatestFER()!=null){
//				ferStat.addValue(d.getLatestFER().doubleValue());
//				withFerDetails.add(d);
//				ferSeries.add(d.getLatestFER().doubleValue());				
//			}
//			
//		}
//		System.out.println("Summary:"+ferStat.getSummary().getN()  + "mean" + ferStat.getStandardDeviation());
//		double dfer [] = new double[ferSeries.size()];
//		int index = 0 ;
//		for (Double d :ferSeries){
//			dfer[index] = d.doubleValue();
//			index ++ ;
//		}
//		double[] normalize = StatUtils.normalize(dfer);
//		for (index=0 ; index < normalize.length; index++){
//			System.out.println("fund:" + scrapFundTypeDetail.get(index).getConstituentFund() + ",fer" + dfer[index] + ",normalized " + normalize[index]);
//		}
		
	}
	
	
	
	
	
	
}
