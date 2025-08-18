/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package framework;

import framework.config.AppConfig;
import framework.injector.ControllerManager;
import framework.injector.DIContainer;
import framework.model.network.NetworkRequest;
import framework.model.network.NetworkResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import framework.simplelogger.LogLevel;
import framework.simplelogger.SimpleLogger;

/**
 *
 * @author Djurkovic
 */
public class RequestDispatcher implements Runnable {

    private final Socket clientSocket;
    private final InetAddress ip;
    private final AppConfig config;
    private final DIContainer container;

    public RequestDispatcher(Socket s, AppConfig config, DIContainer container) {
        clientSocket = s;
        ip = clientSocket.getInetAddress();
        this.config = config;
        this.container = container;
    }

    //proveri da li zahtev nije besmislen i prosledi ga odgovarajucem kontroleru
    @Override
    public void run() {
        try (ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream()); ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());) {

            output.flush();
            SimpleLogger.log(LogLevel.LOG_INFO, "Krecem obradu zahteva klijenta [" + ip + "]");
            Object obj = input.readObject();
            if (!(obj instanceof NetworkRequest zahtev)) {
                SimpleLogger.log(LogLevel.LOG_FATAL, "Obustavljam zahtev od [" + ip + "], nepoznat objekat poslat kao zahtev.");
                output.writeObject(new NetworkResponse("Nepoznat zahtev",false,null));
                return;
            }
            //uzimamo i dispatch pod trajanje jednog zahteva
            long pocetakZahteva = System.nanoTime();
            output.writeObject(ControllerManager.dispatch(zahtev,config,container));
            long trajanjeZahteva = System.nanoTime() - pocetakZahteva;
            SimpleLogger.log(LogLevel.LOG_INFO, "Zavrseno opsluzivanje klijenta [" + ip + "] - " + trajanjeZahteva/1_000_000.0 + " ms");
        } catch (IOException e) {
            SimpleLogger.log(LogLevel.LOG_FATAL, "Neuspesno opsluzen klijent: " + e);
        } catch (ClassNotFoundException ex) {
            SimpleLogger.log(LogLevel.LOG_FATAL, "Nepostojeca klasa (mismatch verzija?): " + ex);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                SimpleLogger.log(LogLevel.LOG_FATAL, "Greska pri zatvaranju klijentskog soketa.[" + ip + "]: " + e);
            }
        }
    }

}
