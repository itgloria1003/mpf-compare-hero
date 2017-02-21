package hk.cuhk.cmsc5702;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

	
public class MpfFundDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String scheme;
	
	private String trustee ; 
	private String constituentFund;
	private String fundType;
	private BigDecimal latestFER;
	private BigDecimal oci1yr;
	private BigDecimal oci5yr;
	private BigDecimal fundRiskIndicator; 
	private Integer riskCat;
	
	public Integer getRiskCat() {
		return riskCat;
	}
	public void setRiskCat(Integer riskCat) {
		this.riskCat = riskCat;
	}
	private BigDecimal annualReturn5yr; 
	private BigDecimal annualReturn10yr;
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getTrustee() {
		return trustee;
	}
	public void setTrustee(String trustee) {
		this.trustee = trustee;
	}
	public String getConstituentFund() {
		return constituentFund;
	}
	public void setConstituentFund(String constituentFund) {
		this.constituentFund = constituentFund;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public BigDecimal getLatestFER() {
		return latestFER;
	}
	public void setLatestFER(BigDecimal latestFER) {
		this.latestFER = latestFER;
	}
	public BigDecimal getOci1yr() {
		return oci1yr;
	}
	public void setOci1yr(BigDecimal oci1yr) {
		this.oci1yr = oci1yr;
	}
	public BigDecimal getOci5yr() {
		return oci5yr;
	}
	public void setOci5yr(BigDecimal oci5yr) {
		this.oci5yr = oci5yr;
	}
	public BigDecimal getFundRiskIndicator() {
		return fundRiskIndicator;
	}
	public void setFundRiskIndicator(BigDecimal fundRiskIndicator) {
		this.fundRiskIndicator = fundRiskIndicator;
	}
	public BigDecimal getAnnualReturn5yr() {
		return annualReturn5yr;
	}
	public void setAnnualReturn5yr(BigDecimal annualReturn5yr) {
		this.annualReturn5yr = annualReturn5yr;
	}
	public BigDecimal getAnnualReturn10yr() {
		return annualReturn10yr;
	}
	public void setAnnualReturn10yr(BigDecimal annualReturn10yr) {
		this.annualReturn10yr = annualReturn10yr;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "MpfFundDetail [id=" + id + ", scheme=" + scheme + ", trustee=" + trustee + ", constituentFund="
				+ constituentFund + ", fundType=" + fundType + ", latestFER=" + latestFER + ", oci1yr=" + oci1yr
				+ ", oci5yr=" + oci5yr + ", fundRiskIndicator=" + fundRiskIndicator + ", riskCat=" + riskCat
				+ ", annualReturn5yr=" + annualReturn5yr + ", annualReturn10yr=" + annualReturn10yr + "]";
	}
	
	

}
