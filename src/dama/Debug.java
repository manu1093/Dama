/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dama;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Manu
 */
public class Debug {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {
	
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				GUI1 f=null;
				try {
					f = new GUI1("dama",new Tavola("debug"));
				} catch (CellaNonVuotaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MetodoUtilizzabileSoloPerDebug ex) {
                                Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (CellaInesistenteException ex) {
                                Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (CellaVuotaException ex) {
                                Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
                            }
				
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				
			}
		});
		}
    
}
