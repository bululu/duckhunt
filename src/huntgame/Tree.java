package huntgame;

import java.util.Random;

public class Tree implements FieldItem {
    
    private HuntField field;
    private Position pos;

    public Tree(HuntField field) {
        this.field=field;
        do{            
            pos=getPosition();
        }while (field.setItem(this, pos)!=true);
    }

    @Override
    public boolean fired() {
        return false;
    }

    @Override
    public char getType() {
        return 'T';
    }
    
    private Position getPosition() {
        Random rand = new Random();
        return new Position(rand.nextInt(field.getXLength()),rand.nextInt(field.getYLength()));
    }

}
