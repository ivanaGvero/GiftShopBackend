package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="Order_item")
public class OrderItem {

	@Id
	@SequenceGenerator(name="ORDER_ITEM_ID_GENERATOR", sequenceName="ORDER_ITEM_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDER_ITEM_ID_GENERATOR")
	private int orderItemId;
	
	private String quantity;
	
	// bi-directional many-to-one association to Brand
				@ManyToOne(cascade = CascadeType.ALL)
				@JoinColumn(name="Product")
				private Product product;
				
	// bi-directional many-to-one association to Brand
				@ManyToOne(cascade = CascadeType.ALL)
				@JoinColumn(name="Orders")
				private Order orders;
				
				public OrderItem() {
					
				}

				public OrderItem(int order_item_id, String quantity, Product product, Order orders) {
					super();
					this.orderItemId = order_item_id;
					this.quantity = quantity;
					this.product = product;
					this.orders = orders;
				}

				public int getOrderItemId() {
					return orderItemId;
				}

				public void setOrderItemId(int orderItemId) {
					this.orderItemId = orderItemId;
				}

				public String getQuantity() {
					return quantity;
				}

				public void setQuantity(String quantity) {
					this.quantity = quantity;
				}

				public Product getProduct() {
					return product;
				}

				public void setProduct(Product product) {
					this.product = product;
				}

				public Order getOrders() {
					return orders;
				}

				public void setOrders(Order orders) {
					this.orders = orders;
				}
				
		
}
