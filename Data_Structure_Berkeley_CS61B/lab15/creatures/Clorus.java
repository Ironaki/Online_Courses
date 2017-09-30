package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/**
 * Created by Armstrong on 5/18/17.
 */
public class Clorus extends Creature{


    /* Color*/
    private static final int R = 34;
    private static final int G = 0;
    private static final int B = 231;

    /* Constants for Actions*/
    private static final double LOSE_PER_MOVE = 0.03;
    private static final double LOSE_PER_STAY = 0.01;

    /* Constructor*/
    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Color color() {
        return color(R, G, B);
    }

    /* Get all energy of the creature attacked*/
    @Override
    public void attack (Creature c) {
        energy += c.energy();
    }


    /* 50% energy goes to offspring. 50% energy left. No energy loss*/
    @Override
    public Clorus replicate() {
        double halfEnergy = energy*0.5;
        energy = halfEnergy;
        return new Clorus(halfEnergy);
    }

    @Override
    public void move() {
        energy -= LOSE_PER_MOVE;
    }

    @Override
    public void stay() {
        energy -= LOSE_PER_STAY;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties= getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");

        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        if (plips.size() > 0) {
            Direction moveDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, moveDir);
        }
        if (energy >= 1) {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, moveDir);
        }
        Direction moveDir = HugLifeUtils.randomEntry(empties);
        return new Action(Action.ActionType.MOVE, moveDir);
    }
}
