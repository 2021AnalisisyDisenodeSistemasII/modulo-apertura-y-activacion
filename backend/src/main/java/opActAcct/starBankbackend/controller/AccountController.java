package opActAcct.starBankbackend.controller;

import opActAcct.starBankbackend.model.CurrentAccount;
import opActAcct.starBankbackend.model.SavingAccount;
import opActAcct.starBankbackend.services.CurrentAccountServices;
import opActAcct.starBankbackend.services.SavingAccountServices;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;
import opActAcct.starBankbackend.services.exception.ObjectDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase controller, que ofrece los servicios al frontend por medio de la URL.
 * Esta clase tiene como raíz "/api/account" y controla todas la cuentas.
 *
 */
@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

    //Instancias de los atributos que apuntan a la capa de servicios (modelo de negocio)
    private final SavingAccountServices savingAccountServices;
    private final CurrentAccountServices currentAccountServices;

    //Constructor con Inyección de dependencias para no tener que inicializar los parámetros.
    @Autowired
    public AccountController(SavingAccountServices savingAccountServices, CurrentAccountServices currentAccountServices) {
        this.savingAccountServices = savingAccountServices;
        this.currentAccountServices = currentAccountServices;
    }

    /**
     * Servicio que crea nuevas cuentas de ahorros.
     * Este servicio se ofrece en la URL: ".../api/account"/savingAccount"
     *
     * @param client_id : Id del cliente al que se le va a crear una nueva cuenta.
     * @param sucursal_id : Sucursal desde donde el usuario crea la cuenta
     * @return Objeto tipo SavingAccount: *En caso de éxito, devuelve el objeto creado.
     *                                    *En caso de que NO HAYA éxito, devuelve el
     *                                     objeto con todos los daos null.
     */
    @PostMapping(path = "/savingAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingAccount createANewASavingAccount(@RequestParam String client_id,
                                                  @RequestParam String sucursal_id){
        SavingAccount cuenta = new SavingAccount();
        try{
            cuenta = savingAccountServices.createANewAccount(client_id, sucursal_id, null);
        }catch (ObjectAlreadyExistsException | ObjectDoesNotExistException oae){
            System.out.println(oae);
        }
        return cuenta;
    }

    /**
     * Servicio que crea nuevas cuentas tipo corriente.
     * Este servicio se ofrece en la URL: ".../api/account"/currentAccount"
     *
     * @param client_id : Id del cliente al que se le va a crear una nueva cuenta.
     * @param sucursal_id : Sucursal desde donde el usuario crea la cuenta
     * @param nit : id de la cuenta.
     * @return Objeto tipo currentAccount: *En caso de éxito, devuelve el objeto creado.
     *                                     *En caso de que NO HAYA éxito, devuelve el
     *                                      objeto con todos los datos null.
     */
    @PostMapping(path = "/currentAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public CurrentAccount createANewACurrentAccount(@RequestParam String client_id,
                                                   @RequestParam String sucursal_id,
                                                   @RequestParam String nit){
        System.out.println(client_id + sucursal_id + nit);
        CurrentAccount cuenta = new CurrentAccount();
        try{
            cuenta = currentAccountServices.createANewAccount(client_id,sucursal_id, nit);

        }catch (ObjectAlreadyExistsException | ObjectDoesNotExistException oae){
            System.out.println(oae);
        }
        System.out.println(cuenta);
        return cuenta;
    }

    /**
     * Servicio que activa una cuenta de ahorros.
     * Este servicio se ofrece en la URL: ".../api/active/savingAccount"
     *
     * @param account_id : Id de la cuenta al que se pretende activar
     * @return Boolean
     *
     */
    @PostMapping(path = "/active/savingAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean activeSavingAccount(@RequestParam String account_id){
        try{
            return savingAccountServices.ActiveSavingAccount(account_id, true);
        }
        catch (ObjectDoesNotExistException one){
            System.out.println(one);
        }
        return false;
    }

    /**
     * Servicio que activa una cuenta corriente.
     * Este servicio se ofrece en la URL: ".../api/active/currentAccount"
     *
     * @param account_id : Id de la cuenta al que se pretende activar
     * @return Boolean
     */
    @PostMapping(path = "/active/currentAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean activeCurrentAccount(@RequestParam String account_id){
        try{
            return currentAccountServices.ActiveCurrentAccount(account_id, true);
        }
        catch (ObjectDoesNotExistException one){
            System.out.println(one);
        }
        return false;
    }



}
