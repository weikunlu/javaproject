package idv.weikun.main.serialized;

import java.io.Serializable;

public class GPN_CHECK_ITEM implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7090202459164112380L;

	private String checkId;
	private String checkName;
	private String checkYear;
	private String checkCategory;
	private String checkType;
	
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public String getCheckYear() {
		return checkYear;
	}
	public void setCheckYear(String checkYear) {
		this.checkYear = checkYear;
	}
	public String getCheckCategory() {
		return checkCategory;
	}
	public void setCheckCategory(String checkCategory) {
		this.checkCategory = checkCategory;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

}
