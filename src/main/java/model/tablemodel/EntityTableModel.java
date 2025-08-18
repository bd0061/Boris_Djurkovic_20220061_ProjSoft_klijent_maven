/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.tablemodel;

import framework.orm.Entitet;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;



/**
 *
 * @author Djurkovic
 */
public class EntityTableModel<T extends Entitet> extends AbstractTableModel {

    private List<T> entiteti;
    private List<Kolona<T>> kolone;

    public EntityTableModel(List<T> entiteti, List<Kolona<T>> kolone) {
        this.entiteti = entiteti;
        this.kolone = kolone;
    }

    @Override
    public int getRowCount() {
        return entiteti.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return kolone.get(columnIndex).getAccesor().get(entiteti.get(rowIndex));
    }

    @Override
    public String getColumnName(int column) {
        return kolone.get(column).getImeKolone();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public T getEntitetAt(int row) {
        return entiteti.get(row);
    }

    public List<T> getEntiteti() {
        return entiteti;
    }

    public void addEntitet(T entitet) {
        entiteti.add(entitet);
        fireTableDataChanged();
    }

    public void addEntiteti(List<T> noviEntiteti) {
        entiteti.addAll(noviEntiteti);
        fireTableDataChanged();
    }

    public void undoEntiteti(Map<Integer, T> noviEntiteti) {
        for (var entry : noviEntiteti.entrySet()) {
            entiteti.add(entry.getKey() > entiteti.size() ? entiteti.size() : entry.getKey(), entry.getValue());
        }
        fireTableDataChanged();
    }

    public void redoEntiteti(Map<Integer, T> noviEntiteti) {
        for (Integer index : noviEntiteti.keySet()) {
            if (index >= 0 && index < entiteti.size()) {
                entiteti.remove((int) index);
                fireTableRowsDeleted(index, index);
            }
        }
    }

    
    public void setEntitet(int idx, T ent) {
        if(idx < 0 || idx >= entiteti.size()) return;
        entiteti.set(idx, ent);
        fireTableRowsUpdated(idx, idx);
    }
    
    public void setEntiteti(List<T> noviEntiteti) {
        this.entiteti.clear();
        this.entiteti.addAll(noviEntiteti);
        fireTableDataChanged();
    }

    public static void centrirajVrednosti(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

        public void removeRow(int index) {
        entiteti.remove(index);
        fireTableRowsDeleted(index, index);
    }

}
