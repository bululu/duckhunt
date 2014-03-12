package huntgame;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Duck extends Thread implements FieldItem {

    private HuntField field;
    private Position pos;
    private boolean alive;
    private boolean checked;
    
    public Duck(HuntField field) {
        this.field=field;
        alive=true;
        checked=false;
        do{            
            pos=getPosition();
        }while (field.setItem(this, pos)!=true);
    }
    
    @Override
    public void setChecked() {
        this.checked = true;
    }

    @Override
    public boolean fired() {
        if (alive){
            alive=false;
            return true;         
        }
        return false;
    }

    @Override
    public char getType() {
        return 'D';
    }

    @Override
    public void run() {
    Position newpos=null;
    int posgen;
    Random rand= new Random();

        while (alive)
        {
            try {
                Thread.sleep((rand.nextInt(301)));
            } catch (InterruptedException ex) {
                Logger.getLogger(Duck.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            posgen=rand.nextInt(4);
            switch (posgen){
                case 0:
                    newpos=new Position(pos.getX()+1,pos.getY());
                    break;
                case 1:
                    newpos=new Position(pos.getX(),pos.getY()-1);
                    break;
                case 2:
                     newpos=new Position(pos.getX()-1,pos.getY());
                    break;
                case 3:
                    newpos=new Position(pos.getX(),pos.getY()+1);
                    break;
            }
            if (field.moveItem(this, pos, newpos))
                pos=newpos;
        }
        while(!checked){
            try {
                Thread.sleep(300);
            }
            catch (InterruptedException ex) {
                Logger.getLogger(Duck.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        field.removeItem(this, pos);
    }
    
    private Position getPosition() {
        Random rand = new Random();
        return new Position(rand.nextInt(field.getXLength()),rand.nextInt(field.getYLength()));
    }
}
