package com.royjo.guilder.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.royjo.guilder.models.Auction;
import com.royjo.guilder.models.User;
import com.royjo.guilder.services.ApiService;
import com.royjo.guilder.services.AuctionService;
import com.royjo.guilder.services.UserService;
import com.royjo.guilder.validators.UserValidator;

@Controller
public class MainController {
	@Autowired
	private UserService userServ;
	@Autowired
	private AuctionService auctionServ;
	@Autowired
	private ApiService apiServ;
	@Autowired
	private UserValidator userValid;
	

    @RequestMapping("/")
    public String registerForm(@ModelAttribute("user") User user) {
        return "index.jsp";
    }
    
    
    @RequestMapping(value="/", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
    	userValid.validate(user, result);
    	if(result.hasErrors()) {
    		return "index.jsp";
    	}
    	else {
    		User newUser = userServ.registerUser(user);
    		session.setAttribute("user_id", newUser.getId());
    		Long id = newUser.getId();
    		return "redirect:/treasurechest/";
    	}
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    	if(userServ.authenticateUser(email, password)) {
    		User user = userServ.findByEmail(email);
    		session.setAttribute("user_id", user.getId());
    		Long id = user.getId();
    		return "redirect:/treasurechest/";
    	}
    	else {
    		redirectAttributes.addFlashAttribute("error", "INVALID CREDENTIALS");
    		return "redirect:/";
    	}
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/";
    }
    
    @RequestMapping("/treasurechest")
    public String treasureChest(Model model, HttpSession session) {
    	if (session.getAttribute("user_id")==null) {
    		return "redirect:/";
    	}
   	try {
			JSONObject obj = apiServ.getGold();
			double goldValuePreFormat = (1 / (double)(obj.getJSONObject("rates").get("XAU")));
			Double goldValueFormatted = new Double(goldValuePreFormat);
			int goldValue = goldValueFormatted.intValue();
			session.setAttribute("goldValue", goldValue);
		} catch (Exception e) {
			System.out.println(e);
			
		}
    	Long uid = (Long) session.getAttribute("user_id");
    	User user = userServ.findUserById(uid);
    	int goldValue = (int) session.getAttribute("goldValue");
    	model.addAttribute("user", user);
    	model.addAttribute("goldValue", goldValue);
    	return "treasureChest.jsp";
    }
    
    @RequestMapping("/purchase")
    public String purchaseOrder(Model model, HttpSession session) {
    if (session.getAttribute("user_id")==null) {
		return "redirect:/";
	}
    int goldValue = (int) session.getAttribute("goldValue");
	int goldCoinValue = (int) (goldValue / 10);
	model.addAttribute("goldCoinValue", goldCoinValue);
	model.addAttribute("goldValue", goldValue);
	return "purchase.jsp";
    }
    
    @PostMapping("/purchase")
    public String purchaseGold(@RequestParam(value="amount") int amount, HttpSession session, Model model) {
    	if (session.getAttribute("user_id")==null) {
    		return "redirect:/";
    	}
    	session.setAttribute("goldPurchaseAmount", amount);
    	return "redirect:/confirmpurchase";
    }
    
    @RequestMapping("/confirmpurchase")
    public String confirmPurchase(Model model, HttpSession session) {
    if (session.getAttribute("user_id")==null) {
		return "redirect:/";
	}
    int goldValue = (int) session.getAttribute("goldValue");
	int goldCoinValue = (int) (goldValue / 10);
	int goldPurchaseAmount = (int) session.getAttribute("goldPurchaseAmount");
	int goldPurchasePrice = goldCoinValue*goldPurchaseAmount;
	model.addAttribute("goldCoinValue", goldCoinValue);
	model.addAttribute("goldPurchasePrice", goldPurchasePrice);
	model.addAttribute("goldPurchaseAmount", goldPurchaseAmount);
	
	return "confirmpurchase.jsp";
    }
    
    @RequestMapping("/processpurchase")
    public String processPurchase(Model model, HttpSession session) {
    	if (session.getAttribute("user_id")==null) {
    		return "redirect:/";
    	}
    	Long uid = (Long) session.getAttribute("user_id");
    	User user = userServ.findUserById(uid);
    	int goldPurchaseAmount = (int) session.getAttribute("goldPurchaseAmount");
    	int currentUserGold = user.getGoldCoins();
    	user.setGoldCoins(currentUserGold + goldPurchaseAmount);
    	userServ.createUser(user);
    	return "redirect:/treasurechest";
    }
    
    @RequestMapping("/sendgold")
    public String sendGold(Model model, HttpSession session) {
    	if (session.getAttribute("user_id")==null) {
    		return "redirect:/";
    	}
    	Long uid = (Long) session.getAttribute("user_id");
    	User user = userServ.findUserById(uid);
    	List<User> userList = userServ.getAllUsers();
    	model.addAttribute("user", user);
    	model.addAttribute("userList", userList);
    	return "sendGold.jsp";
    }
    
    @PostMapping("/sendgold")
    public String processSend(HttpSession session, @RequestParam(value="amount") int amountToSend, @RequestParam(value="receiver") Long receiverId) {
    	if (session.getAttribute("user_id")==null) {
    		return "redirect:/";
    	}
    	Long uid = (Long) session.getAttribute("user_id");
    	User sender = userServ.findUserById(uid);
    	User receiver = userServ.findUserById(receiverId);
    	int senderCurrentGold = sender.getGoldCoins();
    	int receiverCurrentGold = receiver.getGoldCoins();
    	sender.setGoldCoins(senderCurrentGold - amountToSend);
    	receiver.setGoldCoins(receiverCurrentGold + amountToSend);
    	userServ.createUser(sender);
    	userServ.createUser(receiver);
    	return "redirect:/treasurechest";
    }
    
    @RequestMapping("/auctionhouse")
    public String newCourse(@ModelAttribute("auction") Auction auction, Model model, HttpSession session) {
    	if (session.getAttribute("user_id")==null) {
    		return "redirect:/";
    	}
    	Long uid = (Long) session.getAttribute("user_id");
    	User user = userServ.findUserById(uid);
    	List <Auction> auctions = auctionServ.getAllAuctions();
    	String sell = "sell";
    	String buy = "buy";
    	model.addAttribute("auctions", auctions);
    	model.addAttribute("user", user);
    	model.addAttribute("sell", sell);
    	model.addAttribute("buy", buy);
    	return "auctionHouse.jsp";
    }
    
    @PostMapping("/auctionhouse")
	public String createCourse(@Valid @ModelAttribute("auction") Auction auction, BindingResult results){
		if (results.hasErrors()) {
			return "auctionHouse.jsp";
		} else {
			auctionServ.createAuction(auction);
			return "redirect:/auctionhouse";
		}
	}
    @RequestMapping("/takeauction/{id}")
    public String takeAuction(@PathVariable("id") Long id, HttpSession session, RedirectAttributes redirectAttributes) {
    	if (session.getAttribute("user_id")==null) {
    		return "redirect:/";
    	}
    	Long uid = (Long) session.getAttribute("user_id");
    	Auction auction = auctionServ.findAuctionById(id);
    	User maker = auction.getMaker();
    	User taker = userServ.findUserById(uid);
    	int auctionGoldAmount = auction.getCoinAmount();
    	int makerCurrentGold = maker.getGoldCoins();
    	int takerCurrentGold = taker.getGoldCoins();
    	String sell = "sell";
    	String action = auction.getAction();
    	if(action.equals(sell)) {
    		if((makerCurrentGold - auctionGoldAmount) < 0) {
    			redirectAttributes.addFlashAttribute("error", "Cannot drop below 0 gold coins");
    			return "redirect:/auctionhouse";
    		} else {
	    		maker.setGoldCoins(makerCurrentGold - auctionGoldAmount);
	    		taker.setGoldCoins(takerCurrentGold + auctionGoldAmount);
    		}
    	}
    	else {
    		if((takerCurrentGold - auctionGoldAmount) < 0) {
    			redirectAttributes.addFlashAttribute("error", "Cannot drop below 0 gold coins");
    			return "redirect:/auctionhouse";
    		} else {
    			maker.setGoldCoins(makerCurrentGold + auctionGoldAmount);
    			taker.setGoldCoins(takerCurrentGold - auctionGoldAmount);
    		}
    	}
    	userServ.createUser(maker);
    	userServ.createUser(taker);
    	auctionServ.removeAuction(auction);
    	return "redirect:/auctionhouse";
    }
    
}
