/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import config.Config;
import framework.model.network.NetworkRequest;
import framework.model.network.NetworkResponse;
import framework.orm.Entitet;
import iznajmljivanjeapp.domain.Dozvola;
import iznajmljivanjeapp.domain.Iznajmljivanje;
import iznajmljivanjeapp.domain.Smena;
import iznajmljivanjeapp.domain.StavkaIznajmljivanja;
import iznajmljivanjeapp.domain.TerminDezurstva;
import iznajmljivanjeapp.domain.Vozac;
import iznajmljivanjeapp.domain.Vozilo;
import iznajmljivanjeapp.domain.Zaposleni;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import org.reflections.Reflections;

/**
 *
 * @author Djurkovic
 */
public class RequestManager {

    private static Set<Class<?>> st;
    
    
    public static NetworkResponse sendRequest(NetworkRequest req) throws Exception {
        try (Socket sock = new Socket(Config.DOMAIN, Config.PORT); /*ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream()); 
                ObjectInputStream in = new ObjectInputStream(sock.getInputStream())*/) {
            Kryo kryo = new Kryo();
            kryo.setReferences(true);
            kryo.setRegistrationRequired(false);

            Input kryoInput = new Input(sock.getInputStream());
            Output kryoOutput = new Output(sock.getOutputStream());

            //out.writeObject(req);
            kryo.writeClassAndObject(kryoOutput, req);
            kryoOutput.flush();
            //out.flush();
            //return (NetworkResponse) in.readObject();
            return (NetworkResponse) kryo.readClassAndObject(kryoInput);
        }
    }
}
