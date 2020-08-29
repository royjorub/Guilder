package com.royjo.guilder.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.royjo.guilder.models.Auction;


@Repository
public interface AuctionRepository extends CrudRepository<Auction, Long> {
	List<Auction> findAll();
}