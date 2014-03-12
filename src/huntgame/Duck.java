package huntgame;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Duck extends Thread implements FieldItem {

    private HuntField field;
    private Position pos;
    private boolean alive;
    
    public Duck(HuntField field) {
        this.field=field;
        alive=true;
        do{            
            pos=getPosition();
        }while (field.setItem(this, pos)!=true);
    }

    @Override
    public boolean fired() {
        alive=false;
        return true;
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
            
            posgen=rand.nextInt(8);
            switch (posgen){
                case 0:
                    newpos=new Position(pos.getX()+1,pos.getY());
                    break;
                case 1:
                    newpos=new Position(pos.getX()+1,pos.getY()+1);
                    break;
                case 2:
                     newpos=new Position(pos.getX(),pos.getY()+1);
                    break;
                case 3:
                    newpos=new Position(pos.getX()-1,pos.getY()+1);
                    break;
                case 4:
                    newpos=new Position(pos.getX()-1,pos.getY());
                    break;
                case 5:
                    newpos=new Position(pos.getX()-1,pos.getY()-1);
                    break;
                case 6:
                    newpos=new Position(pos.getX(),pos.getY()-1);
                    break;
                case 7:
                    newpos=new Position(pos.getX()+1,pos.getY()-1);
                    break;
            }
            if (field.moveItem(this, pos, newpos))
                pos=newpos;
        }                
    }
    
    private Position getPosition() {
        Random rand = new Random();
        return new Position(rand.nextInt(field.getXLength()),rand.nextInt(field.getYLength()));
    }

}
