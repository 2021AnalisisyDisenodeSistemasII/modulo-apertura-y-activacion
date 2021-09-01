package opActAcct.starBankbackend.controller;

import lombok.NonNull;
import opActAcct.starBankbackend.services.CurrentAccountServices;
import opActAcct.starBankbackend.services.SavingAccountServices;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;
import opActAcct.starBankbackend.services.exception.ObjectDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@RestController
@RequestMapping(path = "/api/account")
@Validated
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

    @PostMapping(path = "savingAccount/")
    public void createANewASavingAccount(@RequestParam String client_id, @RequestParam String sucursal_id){
        try{
            savingAccountServices.createANewAccount(client_id, sucursal_id);
        }catch (ObjectAlreadyExistsException oae){
            System.out.println(oae);
        }catch (ObjectDoesNotExistException one){
            System.out.println(one);
        }
    }

    @PostMapping(path = "currentAccount/")
    public void createANewACurrentAccount(@RequestParam String nit, @RequestParam String client_id, @RequestParam String sucursal_id){
        try{
            currentAccountServices.createANewAccount(nit,client_id, sucursal_id);
        }catch (ObjectAlreadyExistsException oae){
            System.out.println(oae);
        }
    }

    @PutMapping(path = "savingAccount/")
    public void activateASavingAccount(@RequestParam String client_id){

    }




}
