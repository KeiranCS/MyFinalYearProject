package keiran.finalyearproject;

import java.util.Stack;

/**
 * Created by Keiran on 23/10/2015.
 */
public class Turtle {

    private float x;        //position x
    private float y;        //position y
    private float d;        //distance  increment

    private float alpha = (float) (Math.PI)/2;  //direction
    private float delta;    //angle increment

    private Stack<TurtleState> stack = new Stack<TurtleState>();
    private TurtleState state;

    /**
     *
     * @param chr Character defining the rule
     * @return True is a line is to be drawn; False is line is not drawn
     */
    protected boolean rules(char chr){
        switch(chr){
            case 'F':
                x = x + d*(float)Math.cos(alpha);
                y = y - d*(float)Math.sin(alpha);
                return true;
            case 'f':
                x = x + d*(float)Math.cos(alpha);
                y = y + d*(float)Math.sin(alpha);
                return false;
            case '+':
                alpha = alpha + delta;
                return false;
            case '-':
                alpha = alpha - delta;
                return false;
            case '|':
                alpha = alpha + 180;
                return false;
            case '[':
                stack.push(new TurtleState(x, y, alpha));
                return false;
            case ']':
                state = stack.pop();
                x = state.getX();
                y = state.getY();
                alpha = state.getAlpha();
                return false;
            default:
                return false;
        }
    }

    /**
     *
     * @param axiomX Initial point x
     * @param axiomY Initial point y
     * @param d distance interval
     * @param delta angle interval
     */
    public Turtle(float axiomX, float axiomY, float d, float delta){
        setX(axiomX);
        setY(axiomY);
        setD(d);
        setDelta(delta);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }
}
