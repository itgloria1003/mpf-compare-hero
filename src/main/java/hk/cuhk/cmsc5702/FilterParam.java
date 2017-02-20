package hk.cuhk.cmsc5702;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"filterType" , "code"})})
public class FilterParam{

	public FilterParam(String filterType, String code, String value) {
		super();
		this.filterType = filterType;
		this.code = code;
		this.value = value;
	}
	public String getFilterType() {
		return filterType;
	}
	public FilterParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public static final String FUND_TYPE = "fundtype";
	public static final String TRUSTEE = "trustee";
	public static final String SCHEME = "scheme";
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
	@NotNull
	private String filterType;
	@NotNull
	private String code; 
	private String value;
}