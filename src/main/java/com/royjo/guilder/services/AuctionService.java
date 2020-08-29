package com.royjo.guilder.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royjo.guilder.models.Auction;
import com.royjo.guilder.repositories.AuctionRepository;


@Service
public class AuctionService {
	@Autowired
	private AuctionRepository auctionRepo;
	
	public List<Auction> getAllAuctions(){
		return this.auctionRepo.findAll();
	}
	public Auction findAuctionById(Long id) {
		return auctionRepo.findById(id).orElse(null);
	}
	public Auction createAuction(Auction auction) {
		return this.auctionRepo.save(auction);
	}
	public void removeAuction(Auction auction) {
		this.auctionRepo.delete(auction);
		return;
	}
	
}