
package com.github.benyzhous.springboot.sharding.jdbc.masterslave.model;

import javax.persistence.Table;

@Table(name = "t_order")
public final class Order {

	private long orderId;

	private int userId;

	private String status;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(final int userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("order_id: %s, user_id: %s, status: %s", orderId, userId, status);
	}
}
