package model;
import java.io.Serializable;

public class NetworkPackage implements Serializable{
	private String  dealerClassName;
	private Object param1;
	private Object param2;
	private Object param3;
	
	public String getDealerClassName() {
		return dealerClassName;
	}
	public void setDealerClassName(String dealerClassName) {
		this.dealerClassName = dealerClassName;
	}
	public Object getParam1() {
		return param1;
	}
	public void setParam1(Object param1) {
		this.param1 = param1;
	}
	public Object getParam2() {
		return param2;
	}
	public void setParam2(Object param2) {
		this.param2 = param2;
	}
	public Object getParam3() {
		return param3;
	}
	public void setParam3(Object param3) {
		this.param3 = param3;
	}
}