/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dama;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;





public class GUI1 extends JFrame {
	
	private boolean ins=true;
        private boolean rim=false;
	private JLabel a[][];
        private JLabel x[];
        private JLabel y[];
	private GridBagConstraints c=new GridBagConstraints();
	private JLabel l;
        private JLabel label2=new JLabel("modalità inserimento");
	private Tavola t;
	private final JRadioButton bianco=new JRadioButton("Bianco");
        private JRadioButton nero=new JRadioButton("Nero");
        private JRadioButton pedina=new JRadioButton("pedina");
        private JRadioButton damone=new JRadioButton("damone");
        private JButton rpedina=new JButton("togliPedina");
        private JButton load=new JButton("load");
        private JButton save=new JButton("save");
        private JButton add=new JButton("add");
        private JButton ok=new JButton ("ok");
        private ButtonGroup pezzo=new ButtonGroup();
        private ButtonGroup colore=new ButtonGroup();
        private JButton pturno=new JButton("nextTurn");
	private Engine user=new Engine(0);
        private Engine pc=new Engine(1);
	private ImageIcon cvn =new ImageIcon("cvn.jpg");
	private ImageIcon pb=new ImageIcon("pb.jpg");
	private ImageIcon pn =new ImageIcon("pn.jpg");
	private ImageIcon psb=new ImageIcon("psb.jpg");
	private ImageIcon psn =new ImageIcon("psn.jpg");
	private ImageIcon cvb =new ImageIcon("cvb.jpg");
	private ImageIcon dn =new ImageIcon("dn.jpg");
	private ImageIcon dsb=new ImageIcon("dsb.jpg");
	private ImageIcon dsn =new ImageIcon("dsn.jpg");
	private ImageIcon db =new ImageIcon("db.jpg");
	
	
	
	public GUI1(String name,Tavola t) throws CellaInesistenteException, CellaVuotaException{		
		super(name);
		
		this.t=t;
		this.setLayout(new GridBagLayout());
		this.a=new JLabel [8][8];
		for(int y=0;y<a.length;y++){
			for(int x=0;x<a[0].length;x++){
				c.gridx=x+1;
				c.gridy=y+1;
				
				a[y][x]=new JLabel();
				a[y][x].addMouseListener(new GestoreEventi());				
				this.add(a[y][x],c);
				
			}			
		}   
                        x=new JLabel[8];
                        y=new JLabel[8];
                        for(int i=0;i<8;i++){
                            c.gridx=i+1;
                            c.gridy=0;
                            this.x[i]=new JLabel(""+i);
                            this.add(this.x[i],c);
                        }
                         for(int i=0;i<8;i++){
                            c.gridx=0;
                            c.gridy=i+1;
                            this.x[i]=new JLabel(""+i);
                            this.add(this.x[i],c);
                        }
			l=new JLabel();
			c.gridx=0;
			c.gridy=9;
			c.gridwidth=8;
			this.add(l,c);
			
			this.aggiorna(t);
			this.setSize(400,400);
			this.setVisible(true);
                        JFrame f=new JFrame("comandi");
                        GridLayout gr=new GridLayout(3,4);
                        f.setLayout(gr);
                        pezzo.add(pedina);                        
                        pezzo.add(damone);                        
                        colore.add(bianco);                        
                        colore.add(nero);
                        bianco.setSelected(true);
                        damone.setSelected(true);
                        f.add(pedina);                        
                        f.add(bianco);
                        f.add(add);
                        f.add(load);
                        f.add(damone);                        
                        f.add(nero);                        
                        f.add(ok);
                        f.add(save);
                        f.add(label2);
                        f.add(rpedina);
                        f.add(this.pturno);
                       // f.add(load);
                       // f.add(save);
                        pturno.addMouseListener(new GestoreEventi());
                        add.addMouseListener(new GestoreEventi ());
                        ok.addMouseListener(new GestoreEventi());
                        rpedina.addMouseListener(new GestoreEventi());
                        load.addMouseListener(new GestoreEventi2());
                        save.addMouseListener(new GestoreEventi2());
                        f.setSize(400,200);
                        f.setVisible(true);
		}

    
		private void aggiorna(Tavola t) throws CellaInesistenteException, CellaVuotaException{
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
			aggiorna(t);
			//if(e.turnoBianco()){
				if(t.containsPedinaBianca(c))
                                    a[c.getY()][c.getX()].setIcon(psb);
				if(t.containsDamoneBianco(c))
                                    a[c.getY()][c.getX()].setIcon(dsb);
			//}
			/*if(e.turnoNero()){
				if(t.containsPedinaNera(c))
				a[c.getY()][c.getX()].setIcon(psn);
				if(t.containsDamoneNero(c))
					a[c.getY()][c.getX()].setIcon(dsn);
			}*/
			
		}
                private void aggiornaTurno(){
                    Engine a=new Engine(this.user);
                   
                   l.setText(a.getTurnoToString());
                }

		
		

		
		

		
		
		private class GestoreEventi implements MouseListener{
			
			
			
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
                                if(arg0.getSource()==pturno){
                                    
                                    try {
                                        aggiorna(t);
                                    } catch (CellaInesistenteException ex) {
                                        Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (CellaVuotaException ex) {
                                        Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
				if(ins&&!rim){//preparazione
                                    label2.setText("modalità inserimento");
                                    if(arg0.getSource()==ok){
                                        ins=false;
                                        rim=false;
                                    }
                                    if(arg0.getSource()==rpedina){
                                        ins=false;
                                        rim=true;
                                    }
                                    for(int y=0;y<8;y++){
					for(int x=0;x<8;x++){
                                            if(arg0.getSource()==a[y][x]){
                                                
                                               
                                                if(pedina.isSelected()&&nero.isSelected())
                                                    try{
                                                        t.insertPedinaNera(new Cell(x,y));
                                                    } catch(CellaNonVuotaException e){} catch (CellaInesistenteException ex) {
                                                    Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                if(pedina.isSelected()&&bianco.isSelected())
                                                    try{
                                                        t.insertPedinaBianca(new Cell(x,y));
                                                    } catch(CellaNonVuotaException e ){} catch (CellaInesistenteException ex) {
                                                    Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                               if(damone.isSelected()&&bianco.isSelected())
                                                    try{
                                                        t.insertPedinaBianca(new Cell(x,y));
                                                        t.promuoviPedina(new Cell(x,y));
                                                    } catch(CellaNonVuotaException d){} catch (CellaVuotaException ex) {
                                                    Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (CellaInesistenteException ex) {
                                                    Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                if(damone.isSelected()&&nero.isSelected())
                                                    try{
                                                        t.insertPedinaNera(new Cell(x,y));
                                                        t.promuoviPedina(new Cell(x,y));
                                                    } catch(CellaNonVuotaException e){} catch (CellaVuotaException ex) {
                                                    Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (CellaInesistenteException ex) {
                                                    Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                        }
                                    }
                                    try {    
                                        aggiorna(t);
                                    } catch (CellaInesistenteException ex) {
                                        Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (CellaVuotaException ex) {
                                        Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                if(!ins&&rim){
                                    label2.setText("modalità rimozione");
                                    if(arg0.getSource()==ok){
                                        ins=false;
                                        rim=false;                                        
                                    }
                                    if(arg0.getSource()==add){
                                        ins=true;
                                        rim=false;
                                    }
                                    for(int y=0;y<a.length;y++){
					for(int x=0;x<a[0].length;x++){
                                            if(arg0.getSource()==a[y][x]){
                                                Cell c=null;
                                                    try {
                                                        c = t.getCell(x, y);
                                                        t.removePezzo(c);
                                                    } catch (CellaInesistenteException ex) {                                               
                                                    } 
                                                   
                                                    
                                        }
                                        }
                                    }
                                    try {
                                        aggiorna(t);
                                    } catch (CellaInesistenteException ex) {
                                        Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (CellaVuotaException ex) {
                                        Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                if(!ins&&!rim){
                                    label2.setText("modalità gioco");
                                    if(arg0.getSource()==add)
                                        ins=true;
                                        
                                    if(arg0.getSource()==rpedina)
                                        rim=true;
				for(int y=0;y<a.length;y++){
					for(int x=0;x<a[0].length;x++){
						if(arg0.getSource()==a[y][x]){
							Cell c=null;
                                                    try {
                                                        c = t.getCell(x, y);
                                                    } catch (CellaInesistenteException ex) {                                               
                                                    }   
                                                        
							System.out.println(c.getX()+" "+c.getY());
							//System.out.println(c.getColor());
							System.out.println(x+" "+y);
							int r=user.receivedinput(t, c);
                                                        System.out.println(t.toString());
							if(r==0){try {
                                                            //
                                                            aggiornaSM(user.getArbitro().getSource());
                                                            } catch (CellaInesistenteException ex) {
                                                                Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                                            } catch (CellaVuotaException ex) {
                                                                Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                                            }
							}
							while(r==2){
                                                            
                                                                r=user.receivedinput(t, c);
                                                            
                                                        }
                                                try {
                                                               
                                                              if(r==1){  
                                                                t=pc.mossaPc(t);
                                                                aggiorna(t);
                                                              }
                                                  } catch (CellaInesistenteException ex) {
                                                                Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                                            } catch (CellaVuotaException ex) {
                                                                Logger.getLogger(GUI1.class.getName()).log(Level.SEVERE, null, ex);
                                                            }  
                                                
                                                                if(user.controlVictory(t))
                                                                   System.out.println("Ha perso"+user.getTurnoToString());
                                                        
                                                       
                                                    
                                                                }
                                                               
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
        public void mouseClicked(MouseEvent arg0) {
            if(arg0.getSource()==load){
                JFileChooser fc=new JFileChooser();                
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"/salvataggi/"));
                int ret=fc.showOpenDialog(load);
                if(ret==JFileChooser.APPROVE_OPTION){
                    try {
                        Scanner s=new Scanner(new FileReader(fc.getSelectedFile()));
                        while(s.hasNext()){
                            String r=s.nextLine();
                            int x=Integer.parseInt(r.split(":")[0]);
                            int y=Integer.parseInt(r.split(":")[1]);
                            Cell c=new Cell(x,y);
                            char p=r.split(":")[2].charAt(0);
                            if(p=='n')
                                t.insertPedinaNera(c);
                            if(p=='N'){
                                t.insertPedinaNera(c);
                                t.promuoviPedina(c);
                            }
                            if(p=='b')
                                t.insertPedinaBianca(c);
                            if(p=='B'){
                                t.insertPedinaBianca(c);
                                t.promuoviPedina(c);
                            }
                            
                        }
                         aggiorna(t);
                    } catch (CellaVuotaException|CellaInesistenteException|CellaNonVuotaException |FileNotFoundException ex) {  }
                }
               
            }
            if(arg0.getSource()==save){
                JFileChooser fc=new JFileChooser();                
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"/salvataggi/"));
                int ret=fc.showOpenDialog(load);
                if(ret==JFileChooser.APPROVE_OPTION){
                    try {
                        PrintWriter pw=new PrintWriter(fc.getSelectedFile());
                        for(Cell c:t){
                            if(t.getPedina(c).isDamone())
                                pw.println(c.getX()+":"+c.getY()+(":"+t.getPedina(c).getColor()).toUpperCase());
                            else
                                pw.println(c.getX()+":"+c.getY()+(":"+t.getPedina(c).getColor()));
                        }
                        pw.close();
                    } catch (FileNotFoundException |CellaVuotaException e) {  } 
                    }
                }
            }
                
        

        @Override
        public void mousePressed(MouseEvent e) {
            
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
                    
                }
	}
	


