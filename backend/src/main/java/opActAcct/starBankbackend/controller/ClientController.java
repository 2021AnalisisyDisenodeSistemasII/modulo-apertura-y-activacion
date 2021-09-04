package opActAcct.starBankbackend.controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.Gson.*;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Primitives;
import com.google.gson.stream.JsonReader;
import opActAcct.starBankbackend.model.NaturalClient;
import opActAcct.starBankbackend.services.NaturalClientServices;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(path="/api/client")
public class ClientController {

    private final NaturalClientServices naturalClientServices;

    public ClientController(NaturalClientServices naturalClientServices) {
        this.naturalClientServices = naturalClientServices;
    }


}
