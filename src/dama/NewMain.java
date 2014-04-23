/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dama;

/**
 *
 * @author Manu
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CellaNonVuotaException, CellaInesistenteException, MetodoUtilizzabileSoloPerDebug {
        Tavola t=new Tavola("debug");
        Tavola t2=new Tavola(t);
        
        
    }
    
}
