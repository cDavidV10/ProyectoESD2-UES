/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package funciones;

import java.awt.BorderLayout;
import javax.swing.GroupLayout;

import javax.swing.JPanel;

/**
 *
 * @author cdavi
 */
public class Paneles {
    public void insertarPaneles(JPanel panel, JPanel contenedor) {
        panel.setSize(1060, 720);

        contenedor.removeAll();
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();
    }
    
    public void insertarPaneles(JPanel panel, JPanel contenedor, int w, int h) {
        panel.setSize(w, h);

        contenedor.removeAll();
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();
    }
    
    public void insertarPaneles(JPanel panel, JPanel contenedor, int w, int h, boolean remove) {
        panel.setSize(w, h);

        contenedor.removeAll();
        contenedor.add(panel);
        panel.setLocation(100, 0);
        contenedor.revalidate();
        contenedor.repaint();
    }
}
