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

	public Account addAccount(Account newAccount){
		newAccount.setId(currentId++);
		accounts.add(newAccount);
		return newAccount;
	}

	public List<Account> getAll(){
		return accounts;
	}

	private Account findAccountById(Integer id){
		return accounts.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
		
	}

	public Account getAccountById(Integer id){
		return findAccountById(id);
	}

	public Account depositeMoney(Integer id,Double newBalance ){
		Account account = findAccountById(id);
		if(account != null && newBalance > 0 ){
			account.setBalance(account.getBalance() + newBalance);
		}
        return account;
	}

	public Account withdrawMoney(Integer id,Double newBalance){
		Account account = findAccountById(id);

		if(account != null &&  newBalance > 0){
			if(account.getBalance() >= newBalance){
           account.setBalance(account.getBalance() - newBalance);
		   return account;
			}
		}else{
			System.out.println("Insufficient funds");
			return null;
		}
		return null;
	}

	public String transerAmmount(Integer fromAccId , Integer toAccId , Double newBalance){
		Account fromAccount = findAccountById(fromAccId);
		Account toAccount = findAccountById(toAccId);

		if(fromAccount == null || toAccount == null){
            return "Invalid account(s)";
		}

		if(newBalance == null || newBalance <= 0){
			return "Transfer amount must be positive.";
		}

		if(fromAccount.getBalance() < newBalance){
			return "Insufficient Funds";
		}
        
		if( fromAccount.getBalance() >= newBalance && newBalance > 0){
            fromAccount.setBalance(fromAccount.getBalance() - newBalance);
			toAccount.setBalance(toAccount.getBalance() + newBalance);
			return "Transfer Successfull From:" + fromAccount.getId() + "to account: " + toAccount.getId();
		}
	  return "Transer Successfull";
	}

	public String deleteAccount(Integer id){
		Account account = findAccountById(id);
		if(account == null ){
			return " Account not found for id: " + id;
		}
		accounts.remove(account);
		return "Account Deleted Successfully";
	}


}
