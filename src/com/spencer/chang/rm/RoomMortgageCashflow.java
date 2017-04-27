package com.spencer.chang.rm;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 房屋抵押贷款，计划还款现金流
 * 
 * @author Spencer
 *
 */
public class RoomMortgageCashflow implements Serializable {
	public static final long serialVersionUID = -5662814436004991372L;

	public String dueDate;// 还款日期
	public BigDecimal dueMonthAmount; // 还款额
	public BigDecimal dueMonthPrincipal; // 还款本金
	public BigDecimal dueMonthInterset; // 还款利息
	public BigDecimal paidPrincipal; // 已还本金
	public BigDecimal paidInterset; // 已还利息
	public BigDecimal remainingPrincipal; // 剩余本金
	public BigDecimal paidAmount;// 已还款总额

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getDueMonthAmount() {
		return dueMonthAmount;
	}

	public void setDueMonthAmount(BigDecimal dueMonthAmount) {
		this.dueMonthAmount = dueMonthAmount;
	}

	public BigDecimal getDueMonthPrincipal() {
		return dueMonthPrincipal;
	}

	public void setDueMonthPrincipal(BigDecimal dueMonthPrincipal) {
		this.dueMonthPrincipal = dueMonthPrincipal;
	}

	public BigDecimal getDueMonthInterset() {
		return dueMonthInterset;
	}

	public void setDueMonthInterset(BigDecimal dueMonthInterset) {
		this.dueMonthInterset = dueMonthInterset;
	}

	public BigDecimal getPaidPrincipal() {
		return paidPrincipal;
	}

	public void setPaidPrincipal(BigDecimal paidPrincipal) {
		this.paidPrincipal = paidPrincipal;
	}

	public BigDecimal getPaidInterset() {
		return paidInterset;
	}

	public void setPaidInterset(BigDecimal paidInterset) {
		this.paidInterset = paidInterset;
	}

	public BigDecimal getRemainingPrincipal() {
		return remainingPrincipal;
	}

	public void setRemainingPrincipal(BigDecimal remainingPrincipal) {
		this.remainingPrincipal = remainingPrincipal;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((dueMonthAmount == null) ? 0 : dueMonthAmount.hashCode());
		result = prime * result + ((dueMonthInterset == null) ? 0 : dueMonthInterset.hashCode());
		result = prime * result + ((dueMonthPrincipal == null) ? 0 : dueMonthPrincipal.hashCode());
		result = prime * result + ((paidInterset == null) ? 0 : paidInterset.hashCode());
		result = prime * result + ((paidPrincipal == null) ? 0 : paidPrincipal.hashCode());
		result = prime * result + ((remainingPrincipal == null) ? 0 : remainingPrincipal.hashCode());
		result = prime * result + ((paidAmount == null) ? 0 : paidAmount.hashCode());
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
		RoomMortgageCashflow other = (RoomMortgageCashflow) obj;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (dueMonthAmount == null) {
			if (other.dueMonthAmount != null)
				return false;
		} else if (!dueMonthAmount.equals(other.dueMonthAmount))
			return false;
		if (dueMonthInterset == null) {
			if (other.dueMonthInterset != null)
				return false;
		} else if (!dueMonthInterset.equals(other.dueMonthInterset))
			return false;
		if (dueMonthPrincipal == null) {
			if (other.dueMonthPrincipal != null)
				return false;
		} else if (!dueMonthPrincipal.equals(other.dueMonthPrincipal))
			return false;
		if (paidInterset == null) {
			if (other.paidInterset != null)
				return false;
		} else if (!paidInterset.equals(other.paidInterset))
			return false;
		if (paidPrincipal == null) {
			if (other.paidPrincipal != null)
				return false;
		} else if (!paidPrincipal.equals(other.paidPrincipal))
			return false;
		if (remainingPrincipal == null) {
			if (other.remainingPrincipal != null)
				return false;
		} else if (!remainingPrincipal.equals(other.remainingPrincipal))
			return false;
		if (paidAmount == null) {
			if (other.paidAmount != null)
				return false;
		} else if (!paidAmount.equals(other.paidAmount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoomMortgageCashflow [dueDate=" + dueDate + ", dueMonthAmount=" + dueMonthAmount
				+ ", dueMonthPrincipal=" + dueMonthPrincipal + ", dueMonthInterset=" + dueMonthInterset
				+ ", paidPrincipal=" + paidPrincipal + ", paidInterset=" + paidInterset + ", remainingPrincipal="
				+ remainingPrincipal + ", paidAmount=" + paidAmount + "]";
	}
}
