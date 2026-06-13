/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arboles;

import java.util.ArrayList;

/**
 *
 * @author Estudiante
 */
public class Pagina <T extends Comparable<T>>{
    private ArrayList<T> claves;
    private ArrayList<Pagina<T>> hijos;
    private boolean hoja;

    public Pagina(boolean hoja) {
        this.hoja = hoja;
        claves = new ArrayList<>();
        hijos = new ArrayList<>();
    }

    public ArrayList<T> getClaves() {
        return claves;
    }

    public ArrayList<Pagina<T>> getHijos() {
        return hijos;
    }

    public boolean isHoja() {
        return hoja;
    }
    
    
}
