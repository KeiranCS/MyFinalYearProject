package keiran.finalyearproject;

/**
 * Created by Keiran on 23/10/2015.
 */
public class TurtleState {

    private float x;
    private float y;
    private float alpha;


    public TurtleState(float x, float y, float alpha){
        setX(x);
        setY(y);
        setAlpha(alpha);
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


}
