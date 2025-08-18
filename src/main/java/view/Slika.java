/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author Djurkovic
 */
public class Slika extends JPanel {

    private final Image image;
    private final float alpha;

    public Slika(Image image, float alpha) {
        this.image = image;
        this.alpha = alpha;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        //omoguci transparentnost
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        
        //nacrtaj logo na centru forme
        int imgWidth = image.getWidth(this);
        int imgHeight = image.getHeight(this);
        int x = (getWidth() - imgWidth) / 2;
        int y = (getHeight() - imgHeight) / 2;

        g2d.drawImage(image, x, y, this);
        g2d.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        // uvek vracamo false da bi se ignorisali svi mouse eventovi za ovu sliku
        return false;
    }
}
