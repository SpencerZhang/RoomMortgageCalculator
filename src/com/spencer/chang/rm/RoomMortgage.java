package com.spencer.chang.rm;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 房屋抵押贷款
 * 
 * @author Spencer
 *
 */
public class RoomMortgage implements Serializable {
	public static final long serialVersionUID = 7552211986366592536L;

	public BigDecimal mortgagePrincipal; // 抵押贷款额
	public BigDecimal rate;// 贷款利率
	/**
	 * 贷款利率折扣/贷款利率上浮[5%,10%,15%,20%]，二选一
	 */
	public BigDecimal discountRate; // 贷款利率,折扣
	public BigDecimal floatRate; // 贷款利率,上浮
	public int year; // 贷款年限
	public String dueDate;// 还款日期

	public RoomMortgage(BigDecimal mortgagePrincipal, BigDecimal rate, BigDecimal discountRate, BigDecimal floatRate,
			int year, String dueDate) {
		super();
		this.mortgagePrincipal = mortgagePrincipal;
		this.rate = rate;
		this.discountRate = discountRate;
		this.floatRate = floatRate;
		this.year = year;
		this.dueDate = dueDate;
	}

	public BigDecimal getMortgagePrincipal() {
		return mortgagePrincipal;
	}

	public void setMortgagePrincipal(BigDecimal mortgagePrincipal) {
		this.mortgagePrincipal = mortgagePrincipal;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	public BigDecimal getFloatRate() {
		return floatRate;
	}

	public void setFloatRate(BigDecimal floatRate) {
		this.floatRate = floatRate;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discountRate == null) ? 0 : discountRate.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((floatRate == null) ? 0 : floatRate.hashCode());
		result = prime * result + ((mortgagePrincipal == null) ? 0 : mortgagePrincipal.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomMortgage other = (RoomMortgage) obj;
		if (discountRate == null) {
			if (other.discountRate != null)
				return false;
		} else if (!discountRate.equals(other.discountRate))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (floatRate == null) {
			if (other.floatRate != null)
				return false;
		} else if (!floatRate.equals(other.floatRate))
			return false;
		if (mortgagePrincipal == null) {
			if (other.mortgagePrincipal != null)
				return false;
		} else if (!mortgagePrincipal.equals(other.mortgagePrincipal))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoomMortgage [mortgagePrincipal=" + mortgagePrincipal + ", rate=" + rate + ", discountRate="
				+ discountRate + ", floatRate=" + floatRate + ", year=" + year + ", dueDate=" + dueDate + "]";
	}

}
