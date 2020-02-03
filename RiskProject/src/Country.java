import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;


public class Country {
    List<Country> adjacentCountries;

    int numSoldiers;
    private int x;
    private int y;
    private int width;
    private int height;


    public Country(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        numSoldiers = 1;
    }

    public boolean isInside(Point mouse) {
        int mouseX = (int) mouse.getX();
        int mouseY = (int) mouse.getY();

        if (mouseX < x  || mouseX > x + width ) {
            return false;
        }
        if (mouseY < y  || mouseY > y + height ) {
            return false;
        }
        return true;
    }

    public void draw(Graphics g) {

        g.fillRect(x, y, width, height);
        g.setColor(Color.RED);
        g.drawRect(x, y, width, height);
        g.drawString("" + numSoldiers, x + width / 2, y + height / 2);
    }







}