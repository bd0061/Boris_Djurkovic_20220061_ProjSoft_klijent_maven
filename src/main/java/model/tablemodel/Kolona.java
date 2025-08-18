/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.tablemodel;

import framework.orm.Entitet;

/**
 *
 * @author Djurkovic
 */
public class Kolona<T extends Entitet> {
    
    private String imeKolone;
    private FieldAccessor<T> accesor;
    

    public Kolona(String imeKolone, FieldAccessor<T> accesor) {
        this.imeKolone = imeKolone;
        this.accesor = accesor;
    }

    public String getImeKolone() {
        return imeKolone;
    }

    public void setImeKolone(String imeKolone) {
        this.imeKolone = imeKolone;
    }

    public FieldAccessor<T> getAccesor() {
        return accesor;
    }

    public void setAccesor(FieldAccessor<T> accesor) {
        this.accesor = accesor;
    }
    
    
}
