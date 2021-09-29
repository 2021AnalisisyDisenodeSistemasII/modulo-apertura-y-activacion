package opActAcct.starBankbackend.controller;

import opActAcct.starBankbackend.model.CurrentAccount;
import opActAcct.starBankbackend.model.SavingAccount;
import opActAcct.starBankbackend.services.CurrentAccountServices;
import opActAcct.starBankbackend.services.SavingAccountServices;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;
import opActAcct.starBankbackend.services.exception.ObjectDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

    private final SavingAccountServices savingAccountServices;
    private final CurrentAccountServices currentAccountServices;
    /*
    post -> crear
    pull -> actualizar
    get -> consultar.
    delete -> borrar.
     */

    @Autowired
    public AccountController(SavingAccountServices savingAccountServices, CurrentAccountServices currentAccountServices) {
        this.savingAccountServices = savingAccountServices;
        this.currentAccountServices = currentAccountServices;
    }

    @GetMapping(path = {"/Inicio", "/"})
    public String inicio(){
        //model.addAttribute("Lubs", this.getLubricantes());
        return "index";
    }

    @PostMapping(path = "/savingAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingAccount createANewASavingAccount(@RequestParam String client_id, @RequestParam String sucursal_id){
        System.out.println(client_id + sucursal_id);
        SavingAccount cuenta = new SavingAccount();
        try{
            cuenta = savingAccountServices.createANewAccount(client_id, sucursal_id, null);
        }catch (ObjectAlreadyExistsException oae){
            System.out.println(oae);
        }
        catch (ObjectDoesNotExistException one){
            System.out.println(one);
        }
        System.out.println(cuenta);
        //return "exito";
        return cuenta;
    }

    @PostMapping(path = "/currentAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public CurrentAccount createNewCurrentAccount(@RequestParam String client_id, @RequestParam String sucursal_id, @RequestParam String nit){
        CurrentAccount cuenta = new CurrentAccount();
        try{
            cuenta = currentAccountServices.createANewAccount(nit,client_id, sucursal_id);
        }catch (ObjectAlreadyExistsException oae){
            System.out.println(oae);
        }
        catch (ObjectDoesNotExistException one){
            System.out.println(one);
        }
        System.out.println(cuenta);
        return cuenta;
    }

    @PutMapping(path = "savingAccount/")
    public void activateSavingAccount(@RequestParam String client_id){

    }




}
