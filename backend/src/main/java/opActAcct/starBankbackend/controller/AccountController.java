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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
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

    @GetMapping(path = "/formSaving")
    public ModelAndView getFormAccount(){
        //System.out.println("Hola");
        return new ModelAndView("formSaving").addObject("account", new SavingAccount());
    }

    @PostMapping(path = "/savingAccount")
    public String createANewASavingAccount(SavingAccount account, Model model){
        String client_id = account.getClient_id();
        String sucursal_id = account.getSucursal_id();

        try{
             savingAccountServices.createANewAccount(client_id, sucursal_id, null);
             return "exito";
        }catch (ObjectAlreadyExistsException oae){
            System.out.println(oae);
        }
        catch (ObjectDoesNotExistException one){
            System.out.println(one);
        }
        return "error";
    }

    @GetMapping(path = "/formCurrent")
    public ModelAndView getFormCurrent(){
        //System.out.println("Hola");
        return new ModelAndView("formCurrent").addObject("account", new SavingAccount());
    }

    @PostMapping(path = "/currentAccount")
    public String createANewACurrentAccount(CurrentAccount account, Model model){
        String client_id = account.getClient_id();
        String sucursal_id = account.getSucursal_id();
        String nit = account.getAccount_id();
        try{
            currentAccountServices.createANewAccount(nit,client_id, sucursal_id);
            return "exito";
        }catch (ObjectAlreadyExistsException oae){
            System.out.println(oae);
        } catch ( ObjectDoesNotExistException one){
            System.out.println(one);
        }

        return "error";
    }

    @PutMapping(path = "savingAccount/")
    public void activateSavingAccount(@RequestParam String client_id){

    }




}
