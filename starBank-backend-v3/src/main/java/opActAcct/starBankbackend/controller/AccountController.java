package opActAcct.starBankbackend.controller;

import opActAcct.starBankbackend.services.CurrentAccountServices;
import opActAcct.starBankbackend.services.SavingAccountServices;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountController {

    private final SavingAccountServices savingAccountServices;
    private final CurrentAccountServices currentAccountServices;

    @Autowired
    public AccountController(SavingAccountServices savingAccountServices, CurrentAccountServices currentAccountServices) {
        this.savingAccountServices = savingAccountServices;
        this.currentAccountServices = currentAccountServices;
        createANewAccount("1037", "4", "currentAccount");
    }

    @PostMapping(path="/create-account")
    public boolean createANewAccount(@RequestBody String client_id, @RequestBody String sucursal_id,String account_type)  {
        try{
            if (account_type.equals("savingAccount")){
                savingAccountServices.createANewAccount(client_id, sucursal_id);
                return true;
            }
            else if( account_type.equals("currentAccount")){
                currentAccountServices.createANewAccount("nitDefault",client_id , sucursal_id);
                return true;
            }
            return  false;
        }catch (ObjectAlreadyExistsException oae){
            System.out.println(oae);
        }
        return false;
    }
}
