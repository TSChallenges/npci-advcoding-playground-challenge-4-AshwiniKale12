package com.bankmgmt.app.rest;

import com.bankmgmt.app.entity.Account;
import com.bankmgmt.app.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.ResponseCache;
import java.util.List;

@RestController
@RequestMapping("/account")
public class BankController {

   @Autowired
    private BankService bankService;

   @PostMapping()
   public ResponseEntity<?> addAccount(@RequestBody Account account){
    if(account.getBalance() < 0){
        return ResponseEntity.badRequest().body("Initial balance could not be negative.");
    }

    String type = account.getAccountType().toUpperCase();
    if(type != "SAVINGS" || type != "CURRENT"){
        return ResponseEntity.badRequest().body("Account type must be SAVINGS or CURRENT.");
    }

    boolean isEmailUnique = bankService.getAll().stream().noneMatch(a -> a.getEmail().equalsIgnoreCase(account.getEmail())) ;
    if(!isEmailUnique){
        return ResponseEntity.badRequest().body("Email already exists.");
    }
    account.setBalance(0.0);
    Account account2 = bankService.addAccount(account);
    return new ResponseEntity<>(account2,HttpStatus.CREATED);

   }

   @GetMapping()
   public ResponseEntity<List<Account>> getAllAccount(){
    List<Account> accounts = bankService.getAll();
    if(accounts.isEmpty()){
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(accounts,HttpStatus.OK);
   }  

   @GetMapping("/{id}")
   public ResponseEntity<?> getAccountById(@PathVariable("id") Integer id ){
    Account account = bankService.getAccountById(id);
    if(account == null ){
        return ResponseEntity.badRequest().body("Account Not Found");
    }
    return new ResponseEntity<>(account , HttpStatus.OK);
   }


   @PostMapping("/{id}/deposit")
   public ResponseEntity<?> deposite(@PathVariable("id") Integer id,@RequestBody Account account){
    if(account.getBalance() < 0 || account.getBalance() == null ){
        return ResponseEntity.badRequest().body("Deposite amount should be positive");
    }
    Account account2 = bankService.depositeMoney(id,account.getBalance());
    if(account2 == null ){
        return ResponseEntity.badRequest().body("Account Not Found");
    }
    return new ResponseEntity<>(account2,HttpStatus.OK);
   }

   @PostMapping("/{id}/withdraw")
   public ResponseEntity<?> withdraw(@PathVariable("id") Integer id,@RequestBody Account account){
    if(account.getBalance() < 0 || account.getBalance() == null ){
        return ResponseEntity.badRequest().body("Withdrawal amount should be positive");
    }
    Account acc = bankService.withdrawMoney(id, account.getBalance());
    if(acc == null ){
        return ResponseEntity.badRequest().body("Account Not Found");
    }

  return new ResponseEntity<>(acc,HttpStatus.OK);
   }

   @PostMapping("/transer/{fromId}/{toId}/{amount}")
   public ResponseEntity<?> transerAmmount(@RequestParam("fromId") Integer fromId ,
                                           @RequestParam("toId") Integer toId,
                                           @RequestParam("amount") Double amount){
        String result = bankService.transerAmmount(fromId, toId, amount);
        if(result.startsWith("Transfer Successfull")){
            return new ResponseEntity<>(result,HttpStatus.OK);
        }

        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteAccount(@PathVariable("id") Integer id){
        String result = bankService.deleteAccount(id);
        if(result == null){
            return new ResponseEntity<>("Account Not Found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
     }
    
}
