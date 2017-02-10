package hk.cuhk.cmsc5702;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FundTypeDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column( length = 100)
	private String fundType;
	
	@Column( length = 500)
	private String investObjective;
	
	@Column( length = 500)
	private String investInstruments;
	@Column( length = 200)
	private String riskLevel;
	private Integer riskLevelScore;
	@Column( length = 1000)
	private String majorRisk;
	@Column( length = 1000)
	private String feature;

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getInvestObjective() {
		return investObjective;
	}

	public void setInvestObjective(String investObjective) {
		this.investObjective = investObjective;
	}

	public String getInvestInstruments() {
		return investInstruments;
	}

	public void setInvestInstruments(String investInstruments) {
		this.investInstruments = investInstruments;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public Integer getRiskLevelScore() {
		return riskLevelScore;
	}

	public void setRiskLevelScore(Integer riskLevelScore) {
		this.riskLevelScore = riskLevelScore;
	}

	public String getMajorRisk() {
		return majorRisk;
	}

	public void setMajorRisk(String majorRisk) {
		this.majorRisk = majorRisk;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

}
