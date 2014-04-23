/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dama;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;





public class GUI extends JFrame {
	
	
	final private JMenuBar menuBar=new JMenuBar();
        final private JMenu gioco=new JMenu("Gioco");
        final  private JMenu about=new JMenu("About");
        private final JFrame in;
        private JFrame fabout=new JFrame("about");
        final private JMenuItem nuovaPartita=new JMenuItem("Nuova Partita");
        private final JMenuItem opzioni;
        final private JLabel a[][];
	final private GridBagConstraints c=new GridBagConstraints();
	final private JLabel l;
	private Tavola t;	
	final private Engine e;
        final private JRadioButton bianco=new JRadioButton("Bianco");
        final private JRadioButton nero=new JRadioButton("Nero");   
        final private JButton ok=new JButton("ok");
        final private JButton ok2=new JButton("ok");
        private boolean userIsBlack=false;
	private final ImageIcon cvn =new ImageIcon("cvn.jpg");
	private ImageIcon pb=new ImageIcon("pb.jpg");
	private ImageIcon pn =new ImageIcon("pn.jpg");
	private ImageIcon psb=new ImageIcon("psb.jpg");
	private ImageIcon psn =new ImageIcon("psn.jpg");
	private final ImageIcon cvb =new ImageIcon("cvb.jpg");
        private ImageIcon dn =new ImageIcon("dn.jpg");
	private ImageIcon dsb=new ImageIcon("dsb.jpg");
	private ImageIcon dsn =new ImageIcon("dsn.jpg");
	private ImageIcon db =new ImageIcon("db.jpg");
	
	
	
	public GUI(String name,Tavola t){		
		super(name);
                
        this.opzioni = new JMenuItem("Opzioni");
		e=new Engine(0);
		this.t=t;
		this.setLayout(new GridBagLayout());
		this.a=new JLabel [8][8];
		for(int y=0;y<a.length;y++){
			for(int x=0;x<a[0].length;x++){
				c.gridx=x;
				c.gridy=y;
				
				a[y][x]=new JLabel();
				a[y][x].addMouseListener(new GestoreEventi());
				
				this.add(a[y][x],c);
				
			}			
		}
			l=new JLabel();
			c.gridx=0;
			c.gridy=8;
			c.gridwidth=8;
			this.add(l,c);
                        
			this.in=new JFrame("che colore schegli?");
                        this.in.setLayout(new FlowLayout());
                        ButtonGroup g=new ButtonGroup();
                        g.add(nero);
                        g.add(bianco);
                        this.in.add(bianco);
                        this.in.add(nero);
                        this.in.add(ok);
                        ok.addMouseListener(new GestoreEventi2());		
                        bianco.setSelected(true);
                        ok2.addMouseListener(new GestoreEventi2());
                         fabout.setLayout(new FlowLayout());
                         JLabel l=new JLabel("made by Manuele Frigo & Stefano Gugole");
                            fabout.add(l);
                        fabout.add(ok2);
                        fabout.setSize(250,100);
                        fabout.setResizable(false);
                        
                        menuBar.add(gioco);
                        gioco.add(nuovaPartita);
                        gioco.add(opzioni);
                        menuBar.add(about);
                        opzioni.addMouseListener(new GestoreEventi2());
                        nuovaPartita.addMouseListener(new GestoreEventi2());
                        about.addMouseListener(new GestoreEventi2());
                        this.setJMenuBar(menuBar);
			this.setSize(400,400);
                        this.setResizable(false);
			this.setVisible(true);
                        in.setSize(300,100);
                        in.setVisible(true);
		}
		private void aggiorna() throws CellaInesistenteException, CellaVuotaException{
                    for(int y=0;y<8;y++){
                        for(int x=0;x<8;x++){
                            if(t.isEmpty(new Cell(x,y))){
                                if(new Cell(x,y).isBianca())
                                    a[y][x].setIcon(cvb);	
                                if(new Cell(x,y).isNera())
                                   a[y][x].setIcon(cvn);
                            }else{
                                    
                                if(t.containsPedinaBianca(new Cell(x,y)))
                                        a[y][x].setIcon(pb);
                                if(t.containsPedinaNera(new Cell(x,y)))
                                        a[y][x].setIcon(pn);
                                if(t.containsDamoneBianco(new Cell(x,y)))
                                        a[y][x].setIcon(db);
                                if(t.containsDamoneNero(new Cell(x,y)))
                                        a[y][x].setIcon(dn);
					
                            }
                        }
                    }
			this.aggiornaTurno();
		}
		private void aggiornaSM(Cell c) throws CellaInesistenteException, CellaVuotaException{
			aggiorna();
			if(e.turnoBianco()){
				if(t.containsPedinaBianca(c))
                                    a[c.getY()][c.getX()].setIcon(psb);
				if(t.containsDamoneBianco(c))
                                    a[c.getY()][c.getX()].setIcon(dsb);
			}
			if(e.turnoNero()){
				if(t.containsPedinaNera(c))
				a[c.getY()][c.getX()].setIcon(psn);
				if(t.containsDamoneNero(c))
					a[c.getY()][c.getX()].setIcon(dsn);
			}
			
		}
                private void aggiornaTurno(){
                    Engine a=new Engine(this.e);
                    if(userIsBlack){
                        a.nextTurn();                        
                    }
                    l.setText(a.getTurnoToString());
                }
		private class GestoreEventi implements MouseListener{
			
			
			
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				for(int y=0;y<a.length;y++){
					for(int x=0;x<a[0].length;x++){
						if(arg0.getSource()==a[y][x]){
							Cell c=null;
                                                    try {
                                                        c = t.getCell(x, y);
                                                    } catch (CellaInesistenteException ex) {                                               
                                                    }
							
							int r=e.receivedinput(t, c);
							if(r==0){try {
                                                            //
                                                            aggiornaSM(e.getArbitro().getSource());
                                                            } catch (CellaInesistenteException ex) {
                                                                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                                                            } catch (CellaVuotaException ex) {
                                                                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                                                            }
							}
							if(r==1)
								try {
                                                                    aggiorna();
                                                        } catch (CellaInesistenteException ex) {
                                                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                                                        } catch (CellaVuotaException ex) {
                                                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                                                        }
                                                        
                                                        
                                                        e.mossaPc(t);
							
						}
						
						
							
					}
				}
			}
							
				
				
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
				
			

			
			
		}
                private class GestoreEventi2 implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent arg) {
           if(arg.getSource()==ok){
                                    if(nero.isSelected()){
                                        ImageIcon temp;
                                        
                                        temp=pb;
                                        pb=pn;
                                        pn=temp;
                                        
                                        temp=db;
                                        db=dn;
                                        dn=temp;
                                        
                                        temp=psb;
                                        psb=psn;
                                        psn=temp;
                                        
                                        temp=dsb;
                                        dsb=dsn;
                                        dsn=temp;
                                        
                                        userIsBlack=true;
                                                                               
                                    }
                                    in.setVisible(false);
               try { 
                   aggiorna();
               } catch (CellaInesistenteException ex) {
                   Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
               } catch (CellaVuotaException ex) {
                   Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
               }
                                }
           if(arg.getSource()==about){
              
               fabout.setVisible(true);
           }
           if(arg.getSource()==ok2){
              fabout.setVisible(false);
           }
           if(arg.getSource()==nuovaPartita){
                              
           }
           
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
             //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
             //To change body of generated methods, choose Tools | Templates.
        }
                    
                }
	}
	


