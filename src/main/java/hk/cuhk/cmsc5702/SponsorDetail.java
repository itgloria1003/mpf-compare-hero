package hk.cuhk.cmsc5702;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SponsorDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String sponsor;
	private String trustee;
	private String scheme;
	private String contactInfo;
	
	
	public String getSponsorName(){
		return sponsor;
	}
	
	public void setSponsorName(String sponsor){
		this.sponsor = sponsor;
	}
	
	public String getTrustee(){
		return trustee;
	}
	
	public void setTrustee(String trustee){
		this.trustee = trustee;
	}
	
	public String getScheme(){
		return scheme;
	}
	
	public void setScheme(String scheme){
		this.scheme = scheme;
	}
	
	public String getContactInfo (){
		return contactInfo;
	}
	
	public void setContactInfo(String contactInfo){
		this.contactInfo = contactInfo;
	}
	
}
