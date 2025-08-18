/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.UI;

import framework.model.KriterijumDescriptor;
import framework.model.KriterijumWrapper;
import framework.model.network.NetworkRequest;
import framework.model.network.NetworkResponse;
import framework.orm.Entitet;
import framework.simplelogger.LogLevel;
import framework.simplelogger.SimpleLogger;
import iznajmljivanjeapp.domain.Iznajmljivanje;
import iznajmljivanjeapp.domain.Vozac;
import java.awt.Window;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import model.tablemodel.EntityTableModel;
import model.tablemodel.FieldAccessor;
import network.RequestManager;
import util.KriterijumGUI;
import view.richdialogs.PregledVozac;

/**
 *
 * @author Djurkovic
 */
public class DugmeUtils {

    public static <T extends Entitet> void pretraziCall(
            java.awt.Frame glavniProzor,
            java.awt.Window caller,
            JButton btn, List<KriterijumGUI> krits,
            String entitetString, String requestName,
            Class<? extends JDialog> pregledDialog,
            KriterijumWrapper w
    ) {
        List<KriterijumDescriptor> kds;
        try {
            w.kds = KriterijumGUI.processKriterijums(krits);
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Sistem ne može da nađe " + entitetString + " po zadatim kriterijumima.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            NetworkResponse resp = RequestManager.sendRequest(new NetworkRequest(requestName, w));
            if (!resp.success) {
                JOptionPane.showMessageDialog(null, "Sistem ne može da nađe " + entitetString + " po zadatim kriterijumima.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            caller.dispose();
            List<T> entiteti = (List<T>) resp.payload;

            if (entiteti == null || entiteti.isEmpty()) {
                entiteti = new ArrayList<T>();
                JOptionPane.showMessageDialog(null, "Pretraga po zadatim kriterijumima je uspešna, ali nije pronađen nijedan odgovarajuć objekat.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Sistem je našao " + entitetString + " po zadatim kriterijumima.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            }

            Constructor konst = pregledDialog.getDeclaredConstructor(java.awt.Frame.class, boolean.class, List.class, List.class);
            JDialog pregledDialogInstance = (JDialog) konst.newInstance(glavniProzor, true, entiteti, w.kds);
            pregledDialogInstance.setLocationRelativeTo(glavniProzor);
            pregledDialogInstance.setVisible(true);

        } catch (Exception ex) {
            System.out.println("lol " + ex);
            JOptionPane.showMessageDialog(null, "Sistem ne može da nađe " + entitetString + " po zadatim kriterijumima.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static <T extends Entitet> void registrujPretrazi(
            java.awt.Frame glavniProzor,
            java.awt.Window caller,
            JButton btn, List<KriterijumGUI> krits,
            String entitetString, String requestName,
            Class<? extends JDialog> pregledDialog,
            Supplier<KriterijumWrapper> sdl
    ) {
        btn.addActionListener(e -> {
            pretraziCall(glavniProzor, caller, btn, krits, entitetString, requestName, pregledDialog, sdl.get());
        });
        
    }

    public static <T extends Entitet> void registrujPretrazi(
            java.awt.Frame glavniProzor,
            java.awt.Window caller,
            JButton btn, List<KriterijumGUI> krits,
            String entitetString, String requestName,
            Class<? extends JDialog> pregledDialog,
            KriterijumWrapper.DepthLevel depthLevel
    ) {
        registrujPretrazi(glavniProzor, caller, btn, krits, entitetString, requestName, pregledDialog, new KriterijumWrapper(depthLevel));
    }

    public static <T extends Entitet> void registrujPretrazi(
            java.awt.Frame glavniProzor,
            java.awt.Window caller,
            JButton btn, List<KriterijumGUI> krits,
            String entitetString, String requestName,
            Class<? extends JDialog> pregledDialog,
            KriterijumWrapper w
    ) {
        btn.addActionListener((e) -> {
            List<KriterijumDescriptor> kds;
            System.out.println("sisaj kurac: " + w.depthLevel);
            try {
                w.kds = KriterijumGUI.processKriterijums(krits);
            } catch (Exception ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, "Sistem ne može da nađe " + entitetString + " po zadatim kriterijumima.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                NetworkResponse resp = RequestManager.sendRequest(new NetworkRequest(requestName, w));
                if (!resp.success) {
                    JOptionPane.showMessageDialog(null, "Sistem ne može da nađe " + entitetString + " po zadatim kriterijumima.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                caller.dispose();
                List<T> entiteti = (List<T>) resp.payload;

                if (entiteti == null || entiteti.isEmpty()) {
                    entiteti = new ArrayList<T>();
                    JOptionPane.showMessageDialog(null, "Pretraga po zadatim kriterijumima je uspešna, ali nije pronađen nijedan odgovarajuć objekat.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Sistem je našao " + entitetString + " po zadatim kriterijumima.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                }

                Constructor konst = pregledDialog.getDeclaredConstructor(java.awt.Frame.class, boolean.class, List.class, List.class);
                JDialog pregledDialogInstance = (JDialog) konst.newInstance(glavniProzor, true, entiteti, w.kds);
                pregledDialogInstance.setLocationRelativeTo(glavniProzor);
                pregledDialogInstance.setVisible(true);

            } catch (Exception ex) {
                System.out.println("lol " + ex);
                JOptionPane.showMessageDialog(null, "Sistem ne može da nađe " + entitetString + " po zadatim kriterijumima.", "Greška", JOptionPane.ERROR_MESSAGE);
            }

        });
    }

    public static <T extends Entitet> void registrujPretrazi(
            java.awt.Frame glavniProzor,
            java.awt.Window caller,
            JButton btn, List<KriterijumGUI> krits,
            String entitetString, String requestName,
            Class<? extends JDialog> pregledDialog
    ) {
        registrujPretrazi(glavniProzor, caller, btn, krits, entitetString, requestName, pregledDialog, KriterijumWrapper.DepthLevel.SHALLOW);
    }

    public static <T extends Entitet> void registrujOsvezi(
            JButton btn,
            JTable tbl,
            List<KriterijumDescriptor> kds,
            String entityString,
            String requestName,
            KriterijumWrapper.DepthLevel depthLevel
    ) {
        btn.addActionListener(e -> {
            try {
                NetworkResponse resp = RequestManager.sendRequest(new NetworkRequest(requestName, new KriterijumWrapper(kds, depthLevel)));
                if (!resp.success) {
                    JOptionPane.showMessageDialog(null, "Došlo je do greške pri osvežavanju pregleda " + entityString + ".", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                List<T> entiteti = (List<T>) resp.payload;
                EntityTableModel<T> model = (EntityTableModel<T>) tbl.getModel();
                model.setEntiteti(entiteti);
                JOptionPane.showMessageDialog(null, "Uspešno osvežavanje pregleda " + entityString + ".", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                SimpleLogger.log(LogLevel.LOG_ERROR, "Greska pri osvezavanju: " + ex);
                JOptionPane.showMessageDialog(null, "Došlo je do greške pri osvežavanju pregleda " + entityString + ".", "Greška", JOptionPane.ERROR_MESSAGE);
            }

        });

    }

    public static <T extends Entitet> void registrujOsvezi(
            JButton btn,
            JTable tbl,
            List<KriterijumDescriptor> kds,
            String entityString,
            String requestName
    ) {
        registrujOsvezi(btn, tbl, kds, entityString, requestName, KriterijumWrapper.DepthLevel.SHALLOW);

    }

    public static <T extends Entitet> void registrujPronadji(
            java.awt.Frame parent,
            Class<T> entityClass,
            JButton btn,
            JTable tbl,
            String entityString,
            String requestName,
            Window window,
            List<FieldAccessor<T>> fas,
            String[] imenaAtributa,
            Class<?> kontrolerKlasa,
            Class<? extends JDialog> viewKlasa
    ) {
        registrujPronadji(parent, entityClass, btn, tbl, entityString, requestName, window, fas, imenaAtributa, kontrolerKlasa, viewKlasa, KriterijumWrapper.DepthLevel.SHALLOW);
    }

    public static <T extends Entitet> void registrujPronadji(
            java.awt.Frame parent,
            Class<T> entityClass,
            JButton btn,
            JTable tbl,
            String entityString,
            String requestName,
            Window window,
            List<FieldAccessor<T>> fas,
            String[] imenaAtributa,
            Class<?> kontrolerKlasa,
            Class<? extends JDialog> viewKlasa,
            KriterijumWrapper.DepthLevel dl
    ) {
        btn.addActionListener(e -> {

            int[] selectedRows = tbl.getSelectedRows();
            if (selectedRows.length == 0) {
                return;
            }
            if (selectedRows.length > 1) {
                JOptionPane.showMessageDialog(null, "Izaberite samo jednog " + entityString + " za pronalaženje.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            EntityTableModel<T> m = (EntityTableModel<T>) tbl.getModel();
            try {
                List<KriterijumDescriptor> kds = new ArrayList<>();
                if (fas.size() != imenaAtributa.length || imenaAtributa.length == 0) {
                    throw new Exception("Losi kriterijumi");
                }
                for (int i = 0; i < fas.size(); i++) {
                    kds.add(new KriterijumDescriptor(entityClass, imenaAtributa[i], "=", fas.get(i).get(m.getEntitetAt(selectedRows[0]))));
                }

                NetworkResponse resp = RequestManager.sendRequest(new NetworkRequest(requestName, new KriterijumWrapper(kds, dl)));

                if (!resp.success) {
                    JOptionPane.showMessageDialog(null, "Sistem ne može da nađe " + entityString + ".", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                T entitet = ((List<T>) resp.payload).getFirst();
                JOptionPane.showMessageDialog(null, "Sistem je našao " + entityString + ".", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                //window.dispose();

                Constructor konstView = viewKlasa.getDeclaredConstructor(java.awt.Frame.class, boolean.class);
                Constructor konstKontroler = kontrolerKlasa.getDeclaredConstructor(viewKlasa, boolean.class, entityClass);

                JDialog viewInstance = (JDialog) konstView.newInstance(parent, true);
                Object kontrolerInstance = konstKontroler.newInstance(viewInstance, true, entityClass.cast(entitet));
                viewInstance.setLocationRelativeTo(parent);
                viewInstance.setVisible(true);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, "Sistem ne može da nađe " + entityString + ".", "Greška", JOptionPane.ERROR_MESSAGE);
            }

        });
    }

    public static <T extends Entitet> void zapamtiCall(
            Supplier<T> entitetFactory,
            String requestName,
            String entityString,
            boolean promena,
            Window window) {
        T entitet = entitetFactory.get();
        if (entitet == null) {
            JOptionPane.showMessageDialog(null, "Sistem ne može da zapamti " + entityString + ".", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            NetworkResponse resp;
            resp = RequestManager.sendRequest(new NetworkRequest(requestName + (promena ? "/promeni" : "/kreiraj"), entitet));

            if (!resp.success) {
                JOptionPane.showMessageDialog(null, resp.responseMessage, "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, resp.responseMessage, "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                window.dispose();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Sistem ne može da zapamti " + entityString + ".", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static <T extends Entitet> void registrujZapamti(
            JButton btn,
            Supplier<T> entitetFactory,
            String requestName,
            String entityString,
            boolean promena,
            Window window) {
        btn.addActionListener(e -> {
            zapamtiCall(entitetFactory, requestName, entityString, promena, window);
        });
    }

    public static <T extends Entitet> void registrujObrisi(JButton btn, T entitet, String requestName, String entityString, Window view) {
        btn.addActionListener(e -> {
            try {
                NetworkResponse resp = RequestManager.sendRequest(new NetworkRequest(requestName, entitet));
                if (!resp.success) {
                    JOptionPane.showMessageDialog(null, resp.responseMessage, "Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, resp.responseMessage, "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    view.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Sistem ne može da obriše " + entityString + ".", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

}
