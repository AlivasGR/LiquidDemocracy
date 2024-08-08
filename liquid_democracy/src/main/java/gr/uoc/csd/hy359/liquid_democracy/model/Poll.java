package gr.uoc.csd.hy359.liquid_democracy.model;

/**
 *
 * @author Alivas
 */
public class Poll {
    private int upv;
    private int downv;

    public Poll() {
        this.upv = 0;
        this.downv = 0;
    }

    public Poll(int upv, int downv) {
        if (upv < 0 || downv < 0) {
            throw new IllegalArgumentException("Votes Must be Positive!");
        }
        this.upv = upv;
        this.downv = downv;
    }

    public void addUpv() {
        this.upv++;
    }

    public int getUpv() {
        return this.upv;
    }

    public void addDownv() {
        this.downv++;
    }

    public int getDownv() {
        return this.downv;
    }

    public boolean getResult() {
        return (this.upv - this.downv) > 0;
    }
}
