package hk.cuhk.cmsc5702;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MpfFundTypeStat {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	public MpfFundTypeStat() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private String asOfDate;
	
	public MpfFundTypeStat(String asOfDate, String fundType, BigDecimal netAssetValue) {
		super();
		this.asOfDate = asOfDate;
		this.fundType = fundType;
		this.netAssetValue = netAssetValue;
	}
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}
	
	private String fundType;
	private BigDecimal netAssetValue;
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public BigDecimal getNetAssetValue() {
		return netAssetValue;
	}
	public void setNetAssetValue(BigDecimal netAssetValue) {
		this.netAssetValue = netAssetValue;
	}
	
}
