/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import iznajmljivanjeapp.domain.Zaposleni;
import framework.model.network.NetworkRequest;
import framework.model.network.NetworkResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.RequestManager;

/**
 *
 * @author Djurkovic
 */
public class LoginModel {
    
    public NetworkResponse login(String email, String password) throws Exception {
        Zaposleni z = new Zaposleni(email,password);
        return RequestManager.sendRequest(new NetworkRequest("zaposleni/prijava",z));
    }
}
