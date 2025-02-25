package com.bankmgmt.app.service;

import com.bankmgmt.app.entity.Account;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.*;

@Service
public class BankService {

	private Integer currentId = 1;
	private List<Account> accounts = new ArrayList<>(
    		Arrays.asList(
    				new Account (currentId++,"Ashwini Kale","saving",50000.00,"ashwini.kale@npci.org.in"),
    				new Account (currentId++,"abc","current",60000.00,"abc@npci.org.in"),
    				new Account (currentId++,"xyz","saving",70000.00,"xyz@npci.org.in"),
    				new Account (currentId++,"pqr","saving",80000.00,"pqr@npci.org.in"),
    				new Account (currentId++,"klm","current",90000.00,"klm@npci.org.in")
                     )
    				);

	// Method to Create a new Account
	public Account addAccount(Account newAccount) {
		newAccount.setId(currentId);
		accounts.add(newAccount);
		return newAccount;
	}
    				
    
    // Method to Get All Accounts
	public List<Account> getAllAccount(){
		return accounts;
	}
    
//	find account by is
	public Account findAccountById(int id) {
		for(Account a : accounts) {
			if(a.getId() == id) {
				return a;
			}
		}
		return null;
	}

    // Method to Get Account by ID
	public Account getAccountById(int id) {
		return findAccountById(id);
	}
    

    // Method to Deposit Money to the specified account id
	public Account depositeMoney(int id,Double amt) {
		Account a = findAccountById(id);
		if(a != null && amt != null) {
			a.setBalance(a.getBalance()+amt);
		}
		return a;	
	}
    

    //  Method to Withdraw Money from the specified account id
	public Account withdrawMoney(int id,Double amt) {
		Account a = findAccountById(id);
		if(a != null && amt != null) {
			a.setBalance(a.getBalance()- amt);
		}
		return a;	
	}
    

    // Method to Transfer Money from one account to another
	public String moneyTransfer(int fromAccId,int toAccId,Double balance) {
		Account fromAccount = findAccountById(fromAccId);
		Account toAccount = findAccountById(toAccId);
		if (fromAccount == null) {
		    return "Sender account not found.";
		}
		if (toAccount == null) {
		    return "Receiver account not found.";
		}
		if (balance == null || balance <= 0) {
		    return "Invalid transfer amount.";
		}
		if (fromAccount.getBalance() < balance) {
		    return "Insufficient Balance.";
		}
		fromAccount.setBalance(fromAccount.getBalance() - balance);
		toAccount.setBalance(toAccount.getBalance() + balance);
		
		return "Amount:"+ balance +" Transfer Successfully";
	}
    
	
    //  Method to Delete Account given a account id
	public String delete(int id) {
		Account a = findAccountById(id);
		if(a == null) {
			return "Account Not Found";
		}
	 accounts.remove(a);
	 return "Account Deleted Sucessfully";
	}
}
