package com.bankmgmt.app.rest;

import com.bankmgmt.app.entity.Account;
import com.bankmgmt.app.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class BankController {

   @Autowired
    private BankService bankService;

    //API to Create a new account
    @PostMapping("/create")
    public ResponseEntity<Account> addNewAccount(Account account) {
    	Account a = bankService.addAccount(account);
    	return new ResponseEntity<>(a,HttpStatus.CREATED);
    }

    //API to Get all accounts
    @GetMapping("/getAll")
    public ResponseEntity<List<Account>> getAllAccount(){
    	List<Account> accounts=bankService.getAllAccount();
    	if(accounts.isEmpty()) {
    		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>(accounts,HttpStatus.OK);
    }
    

    //API to Get an account by ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<Account> getAccounUsingId(@PathVariable("id") int id){
    	Account account = bankService.getAccountById(id);
    	return new ResponseEntity<>(account,HttpStatus.OK);
    }
    

    //  API to Deposit money
    @PutMapping("/deposite")
    public ResponseEntity<Account> depositeMoneyToAcc(@RequestBody Account account){
    	Account  acc = bankService.depositeMoney(account.getId(),account.getBalance());
    	if(acc == null) {
    		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>(acc,HttpStatus.OK);
    }
    

    //  API to Withdraw money
    @PutMapping("/withdraw")
    public ResponseEntity<Account> withdrwaMoneyToAcc(@RequestBody Account account){
    	Account  acc = bankService.withdrawMoney(account.getId(),account.getBalance());
    	if(acc == null) {
    		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>(acc,HttpStatus.OK);
    }
    

    //API to Transfer money between accounts
    @PutMapping("/transfer")
    public ResponseEntity<String> transferMoneytoAcc(@RequestParam int fromAccId, 
            @RequestParam int toAccId, 
            @RequestParam double amount){
    	String result = bankService.moneyTransfer(fromAccId, toAccId, amount);
    	if (result.startsWith("Amount:")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
    

    //API to Delete an account
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") int id) {
    	String deletion = bankService.delete(id);
    	return new ResponseEntity<>(deletion,HttpStatus.OK);
    }
    
    
    
}
