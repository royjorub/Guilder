package com.royjo.guilder.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;


import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="auctions")
public class Auction {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	private int coinAmount;
	private Double pricePerCoin;
	private String action;
//	buy or sell
	
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User maker;
	
	
	public Auction() {
    }
	
	
	public Auction(int coinAmount, Double pricePerCoin, String action) {
		this.coinAmount = coinAmount;
		this.pricePerCoin = pricePerCoin;
		this.action = action;
	}
	
	
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getCoinAmount() {
		return coinAmount;
	}


	public void setCoinAmount(int coinAmount) {
		this.coinAmount = coinAmount;
	}


	public Double getPricePerCoin() {
		return pricePerCoin;
	}


	public void setPricePerCoin(Double pricePerCoin) {
		this.pricePerCoin = pricePerCoin;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	public User getMaker() {
		return maker;
	}


	public void setMaker(User maker) {
		this.maker = maker;
	}


	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
