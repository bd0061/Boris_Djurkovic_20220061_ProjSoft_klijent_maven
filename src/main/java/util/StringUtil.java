/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import framework.orm.EntityManager;
import iznajmljivanjeapp.domain.Vozac;
import java.lang.reflect.Field;

/**
 *
 * @author Djurkovic
 */
public class StringUtil {

    public static String camelCaseToSentence(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String spaced = input.replaceAll("([A-Z])", " $1");

        return spaced.substring(0, 1).toUpperCase() + spaced.substring(1);
    }

    public static void main(String[] args) throws NoSuchFieldException {
        Field f = Vozac.class.getDeclaredField("id");
        String imeKolone = StringUtil.camelCaseToSentence(EntityManager.vratiImePolja(f));
        String imeGetera = "get" + f.getName().substring(0,1).toUpperCase() + f.getName().substring(1);
        System.out.println(imeKolone);
        System.out.println(imeGetera);
    }
}
