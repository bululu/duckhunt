package huntgame;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HuntField {

    private int XLength;
    private int YLength;
    private FieldItem field[][];
    
    public HuntField(int XLength, int YLength) {
        this.XLength = XLength;
        this.YLength = YLength;
        field=new FieldItem[XLength][YLength];
        for (int i=0;i<XLength;i++)
            for (int j=0; j<YLength;j++)
                field[i][j]=null;            
    }
    
    public int getXLength() {
        return XLength;
    }

    public int getYLength() {
        return YLength;
    }
   
    public synchronized boolean setItem(FieldItem item, Position pos){
        if ((pos.getX()<0)||(pos.getY()<0)||(pos.getX()>=XLength)||(pos.getY()>=YLength)||(field[pos.getX()][pos.getY()]!=null))
            return false;
        field[pos.getX()][pos.getY()]=item;
        return true;
    }
    
    public synchronized boolean shot(Position pos){
        if ((pos.getX()<0)||(pos.getY()<0)||(pos.getX()>=XLength)||(pos.getY()>=YLength)||(field[pos.getX()][pos.getY()]==null))
            return false;
        return field[pos.getX()][pos.getY()].fired();
    }
    
    public synchronized boolean removeItem(FieldItem item, Position pos){
        if ((pos.getX()<0)||(pos.getY()<0)||(pos.getX()>=XLength)||(pos.getY()>=YLength)||(field[pos.getX()][pos.getY()]!=item))
            return false;        
        field[pos.getX()][pos.getY()]=null;
        return true;
    }
    
    public synchronized char getItemType(Position pos){
    char type;
        if (field[pos.getX()][pos.getY()]!=null)
             return field[pos.getX()][pos.getY()].getType();
        return ' ';
    }
    
    public synchronized boolean moveItem(FieldItem item, Position pos, Position newpos){
        if ((pos.getX()<0)||(pos.getY()<0)||(pos.getX()>=XLength)||(pos.getY()>=YLength)||(field[pos.getX()][pos.getY()]!=item) 
            ||(newpos.getX()<0)||(newpos.getY()<0)||(newpos.getX()>=XLength)||(newpos.getY()>=YLength))
            return false;
        long time=System.nanoTime();
        long auxtime=time;
        
        if (field[newpos.getX()][newpos.getY()]!=null)
        {
        
            do{
                auxtime=System.nanoTime();
                try {
                    wait(1000);

                } catch (InterruptedException ex) {
                    Logger.getLogger(HuntField.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while((auxtime-time<=60000)&&(field[newpos.getX()][newpos.getY()]!=null));
        }
        if(auxtime-time>60000)
            return false;
        field[pos.getX()][pos.getY()]=null;
        field[newpos.getX()][newpos.getY()]=item;
        return true;
    }
    
    public synchronized int getNumberOfItems(char type){
    int count=0;
    
        for (int i=0; i<XLength; i++)
            for (int j=0; j<YLength; j++){
                if ((field[i][j]!=null)&&(field[i][j].getType()==type))
                    count++;
            }
        return count;
    }
    
    @Override
    public synchronized String toString(){
    String fieldstr="";
        
        for (int i=0; i<XLength; i++){
            for (int j=0; j<YLength; j++){
                if (field[i][j]==null)
                    fieldstr+=" ";
                else
                    fieldstr+=field[i][j].getType();
            }
            fieldstr+="\n";
        }
        return fieldstr;
    }
}
