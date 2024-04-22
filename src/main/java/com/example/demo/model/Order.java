package com.example.demo.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="Orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	@Temporal(TemporalType.DATE)
	private Date orderDate;
	private String orderStatus;
	@Column(name = "total_price")
	private float orderPrice;

	@Column(name = "discount")
	private float discount;
	
	// bi-directional many-to-one association to Brand
			@ManyToOne()
			@JoinColumn(name="userId")
			private User user;

	@OneToMany(mappedBy="orders", cascade = {CascadeType.ALL})
	private List<OrderItem> orderItems = new ArrayList<>();

	@OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private Payment payment;

	public Order() {

	}

		public Order(int order_id, Date order_date, String order_status, float order_price, User user) {
			super();
			this.orderId = order_id;
			this.orderDate = order_date;
			this.orderStatus = order_status;
			this.orderPrice = order_price;
			this.user = user;
		}

		public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}

		public Date getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(Date orderDate) {
			this.orderDate = orderDate;
		}

		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		public float getOrderPrice() {
			return orderPrice;
		}

		public void setOrderPrice(float orderPrice) {
			this.orderPrice = orderPrice;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		for (var item : orderItems) {
			item.setOrders(this);
		}
		 this.orderItems = orderItems;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
