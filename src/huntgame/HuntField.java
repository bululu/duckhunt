package huntgame;

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
        if (field[pos.getX()][pos.getY()].fired())
            return removeItem(field[pos.getX()][pos.getY()],pos);
        return false;
    }
    
    private synchronized boolean removeItem(FieldItem item, Position pos){
        if ((pos.getX()<0)||(pos.getY()<0)||(pos.getX()>=XLength)||(pos.getY()>=YLength)||(field[pos.getX()][pos.getY()]!=item))
            return false;        
        field[pos.getX()][pos.getY()]=null;
        return true;
    }
    
    private char getItemType(Position pos){
        if (field[pos.getX()][pos.getY()]!=null)
            return field[pos.getX()][pos.getY()].getType();
        return ' ';
    }
    
    public synchronized boolean moveItem(FieldItem item, Position pos, Position newpos){
        if ((pos.getX()<0)||(pos.getY()<0)||(pos.getX()>=XLength)||(pos.getY()>=YLength)||(field[pos.getX()][pos.getY()]!=item) 
            ||(newpos.getX()<0)||(newpos.getY()<0)||(newpos.getX()>=XLength)||(newpos.getY()>=YLength)||(field[newpos.getX()][newpos.getY()]!=null))
            return false;
        field[pos.getX()][pos.getY()]=null;
        field[newpos.getX()][newpos.getY()]=item;
        return true;
    }
    
    public int getNumberOfItems(char type){
    int count=0;
    
        for (int i=0; i<XLength; i++)
            for (int j=0; j<YLength; j++){
                if ((field[i][j]!=null)&&(field[i][j].getType()==type))
                    count++;
            }
        return count;
    }
    
    @Override
    public String toString(){
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
