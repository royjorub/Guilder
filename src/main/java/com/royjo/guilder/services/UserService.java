package com.royjo.guilder.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royjo.guilder.models.User;
import com.royjo.guilder.repositories.UserRepository;


@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	public List<User> getAllUsers(){
		return this.userRepo.findAll();
	}
	public User findUserById(Long id) {
		return userRepo.findById(id).orElse(null);
	}
	public User createUser(User user) {
		return this.userRepo.save(user);
	}
	
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepo.save(user);
    }
        
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    
    public boolean authenticateUser(String email, String password) {
        User user = userRepo.findByEmail(email);
        if(user == null) {
            return false;
        } else {
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
    
}