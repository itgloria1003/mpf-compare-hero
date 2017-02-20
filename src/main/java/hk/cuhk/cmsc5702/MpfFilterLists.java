package hk.cuhk.cmsc5702;

import java.util.ArrayList;

public class MpfFilterLists{
	private Iterable<FilterParam> trusteeList = new ArrayList<FilterParam>() ;
	public Iterable<FilterParam> getTrusteeList() {
		return trusteeList;
	}
	public void setTrusteeList(Iterable<FilterParam> trusteeList) {
		this.trusteeList = trusteeList;
	}
	private Iterable<FilterParam> fundtypeList = new ArrayList<FilterParam>() ;
	private Iterable<FilterParam> mpfFundIdList = new ArrayList<FilterParam>() ;
	
	public Iterable<FilterParam> getFundtypeList() {
		return fundtypeList;
	}
	public void setFundtypeList(Iterable<FilterParam> fundtypeList) {
		this.fundtypeList = fundtypeList;
	}
	public Iterable<FilterParam> getMpfFundIdList() {
		return mpfFundIdList;
	}
	public void setMpfFundIdList(Iterable<FilterParam> mpfFundIdList) {
		this.mpfFundIdList = mpfFundIdList;
	}
	
	
}