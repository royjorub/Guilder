package com.royjo.guilder.models;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.royjo.guilder.models.Auction;



@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message="name must not be empty")
    private String name;

    
    @Email(message="Email must be valid")
    private String email;
    
    @Size(min=8, message="Password must be greater than 8 characters")
    private String password;
    
    @Transient
    private String passwordConfirmation;
    @Transient
    private String duplicate;
    

    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    @Min(0)
    private int goldCoins;
    
    @OneToMany(mappedBy="maker", fetch=FetchType.LAZY)
	private List<Auction> auctions;
    
    public User() {
    }
    
    
    public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.goldCoins = 0;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}


	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	
	public String getDuplicate() {
		return duplicate;
	}
	public void setDuplicate(String duplicate) {
		this.duplicate = duplicate;
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

	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

	


	public int getGoldCoins() {
		return goldCoins;
	}


	public void setGoldCoins(int goldCoins) {
		this.goldCoins = goldCoins;
	}
	
	
	

	public List<Auction> getAuctions() {
		return auctions;
	}


	public void setAuctions(List<Auction> auctions) {
		this.auctions = auctions;
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