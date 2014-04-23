/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dama;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Tavola implements Iterable<Cell>{//itera su celle piene + comodo
	
	private HashMap <Cell,Pedina> pedine;//stacca pedine da celle
        
        public Tavola(String s)throws CellaNonVuotaException, MetodoUtilizzabileSoloPerDebug, CellaInesistenteException{//per debug
           
            if(!s.equals("debug"))
                throw new MetodoUtilizzabileSoloPerDebug();
            pedine=new HashMap();
                }
        
        public Tavola(Tavola t){   
            
            //for(Iterator <Cell> i=t.getFullCells();i.hasNext();){
              //  Cell c=i.next();
            this.pedine=new HashMap<>();
            for(Cell c:t){
                try {
                    if(t.getPedina(c).isDamone())
                        this.pedine.put(c, new Damone(t.getPedina(c)));
                    else
                        this.pedine.put(c, new Pedina(t.getPedina(c)));
                } catch (CellaVuotaException ex) {
                    
                }
            }
        }
	public Tavola() throws CellaNonVuotaException{
		
                pedine=new HashMap<>();
		int y;
		int f=0;
		for (y=0;y<3;y++)//riempie le prime tre righe
			for(int x=0;x<8;x++)
                            try {
                                if(new Cell(x,y).isNera()){
                                    try{
                                        Cell c=new Cell(x,y);
                                        this.pedine.put(c, new Pedina(Pedina.nero));
                                    }catch(CellaInesistenteException e){System.out.println("cella sbagliata in costruttore tavola");}
                                    
                                }
                    } catch (CellaInesistenteException ex) {
                        Logger.getLogger(Tavola.class.getName()).log(Level.SEVERE, null, ex);
                    }
		for (y=5;y<8;y++){//ultime tre
			for(int x=0;x<8;x++){				
                            try {
                                if(new Cell(x,y).isNera()){
                                    Cell c;
                                    try {
                                        c = new Cell(x,y);
                                        this.pedine.put(c,new Pedina(Pedina.bianco));
                                    } catch (CellaInesistenteException ex) { System.out.println("cella sbagliata in costruttore tavola");}
                                    
                                }
                            } catch (CellaInesistenteException ex) {
                                Logger.getLogger(Tavola.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
		}
		/*char a='a';
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				tavoliere[i][j]=new Pedina(a);
				a++;
			}
		System.out.println(this.toString());
		System.out.println(this.getText(2, 3));*/
	//this.user=1;
	}
	/*
	public boolean isUserBlack(){
            return user==0;
        }
        public boolean isUserWhite(){
            return user==1;
        }
        */
        
	private char getText(int x,int y) throws CellaInesistenteException{
		//if(i<this.getLunghezza()||j<this.getAltezza()) throw new IllegalArgumentException();		
		if(this.pedine.containsKey(new Cell(x,y)))
                    return this.pedine.get(new Cell(x,y)).getColor();
                else{
                    if(new Cell(x,y).isBianca())
                        return 'x';
                    if(new Cell(x,y).isNera())
                        return ' ';
		}
                return 'J';
        }
	/*
	public Pedina getPedina(int x,int y){
		return this.tavoliere.get(y*8+x).getPedina();
	}
	
	*/
        
	public Cell getCell(int x,int y) throws CellaInesistenteException{
            
            try{
		return new Cell(x,y);
            } catch (IndexOutOfBoundsException e){throw new CellaInesistenteException();}
               
	}
        
	/*
	public void setPedina(char h,Pedina p){
		for(int i=0;i<8;i++){
			for (int j=0;j<8;j++){
				if(p==this.getPedina(i, j))
					this.setText(h,i,j);
			}
		}
	}
	*/
	public void swapCelle(Cell a,Cell b){
            /*
            Cell at=null;
            Cell bt=null;
            for(Cell c:this){
                if(c.getX()==a.getX()&&c.getY()==a.getY()){
                    at=c;
                }
                if(c.getX()==b.getX()&&c.getY()==b.getY()){
                    bt=c;
                }
            }
            */
            
            Pedina a1=this.pedine.remove(a);            
            Pedina a2=this.pedine.remove(b);
            if(a2!=null)
                this.pedine.put(a, a2);
            if(a1!=null)
                this.pedine.put(b, a1);
            
	}
	
        public Cell middleCell(Cell c1,Cell c2){
            try{
            int mx;
            if(c1.getX()<c2.getX())
                mx=c1.getX()+1;
            else
                mx=c2.getX()+1;
            int my;
            if(c1.getY()<c2.getY())
                my=c1.getY()+1;
            else
                my=c2.getY()+1;
            return this.getCell(mx, my);
            }catch(CellaInesistenteException e){ return null;}
        }
        @Override
	public String toString(){
		int y;
		String s="";
		
		for (y=0;y<8;y++){
			s+=y;
			for(int x=0;x<8;x++)
				try {
                                    s+=" | "+this.getText(x, y);
                            } catch (CellaInesistenteException ex) {
                               
                            }
			s+="\n";
		}
		s+=" ";
		for(int i1=0;i1<8;i1++)
			s+=" | "+i1;
		return s;
	}
        
	
	
        
        public void insertPedinaBianca(Cell c) throws CellaNonVuotaException{
            if(!this.pedine.containsKey(c))
                            this.pedine.put(c, new Pedina(Pedina.bianco));
                    else throw new CellaNonVuotaException();
                
	}
	
	public void insertPedinaNera(Cell c) throws CellaNonVuotaException{
		if(!this.pedine.containsKey(c))
                            this.pedine.put(c, new Pedina(Pedina.nero));
                    else throw new CellaNonVuotaException();
	}
        
        public void removePezzo(Cell c) {
		
                    this.pedine.remove(c);
                
	}
        
	public boolean isEmpty(Cell c){//potrebbe non servire
            return !this.pedine.containsKey(c);
	}

    
    public void promuoviPedina(Cell c) throws CellaVuotaException{
		if(!this.pedine.containsKey(c))
			throw new CellaVuotaException();
                else                 
                    this.pedine.put(c, new Damone(this.pedine.get(c).getColor()));
                
	}
	public Pedina getPedina(Cell c) throws CellaVuotaException{//da vedere se togliere
            if(this.pedine.containsKey(c))
                return this.pedine.get(c);
            else
                throw new CellaVuotaException();
	}
	/*
	public char getColor(){//per compatibilità da togliere
		if(p!=null)
			return p.getColor();
		else
			return this.color;
	}
        */
        public boolean containsPezzoBianco(Cell c) throws CellaVuotaException{
		if(this.pedine.get(c)==null)
                   throw new CellaVuotaException(c.getX()+" "+c.getY());
               return this.pedine.get(c).isBianca();
		
		
	}
        public boolean containsPezzoNero(Cell c) throws CellaVuotaException{
		return !this.containsPezzoBianco(c);
		
	}
       
	public boolean containsPedinaBianca(Cell c) throws CellaVuotaException{
		
                    return this.containsPezzoBianco(c)&&!this.containsDamone(c);
		
		
	
        }
	public boolean containsPedinaNera(Cell c) throws CellaVuotaException{
		
                    return !this.containsDamone(c)&&this.containsPezzoNero(c);
		
	}
	public boolean containsDamoneBianco(Cell c) throws CellaVuotaException{
		
                    return this.containsDamone(c)&&this.containsPezzoBianco(c);
	
	}
	public boolean containsDamoneNero(Cell c) throws CellaVuotaException{
		
                    return this.containsDamone(c)&&this.containsPezzoNero(c);
	}
        
	public boolean containsDamone(Cell c) throws CellaVuotaException{
		if(this.pedine.get(c)==null)
                   throw new CellaVuotaException();
               return this.pedine.get(c).isDamone();
	}

    @Override
    public Iterator<Cell> iterator() {
        return this.pedine.keySet().iterator();
    }
}
