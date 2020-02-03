import java.awt.Color;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Board extends JPanel {
    private static int turn = 0;
    private static int Troops = 15;
    public static final int BOARD_WIDTH = 900;
    public static final int BOARD_HEIGHT = 650;
    public static final Color[] colors = {Color.GREEN, Color.BLUE};
    private static ArrayList<ArrayList<Country>> continents;
    private static final int[] continentBonuses = {5, 2, 5, 3, 7, 2};
    static Country[] countries;


    private static Country selectedCountry;
    private static Country secondSelectedCountry;
    private final JLabel tInfo;
    private static Player[] players;

    enum Mode {
        PlaceInitialSoldiers, place, AttackFrom, Attack,
        KeepAttacking, NewCountry, EndTurn, GameOver
    }

    private Mode mode = Mode.PlaceInitialSoldiers;

    public Board(final JLabel tInfo) {
        this.tInfo = tInfo;


        InitializeCountries();
        InitializeContinents();
        InitializePlayers();
        assignCountries();


        tInfo.setText(displayText());

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point mouse = e.getPoint();

                switch (mode) {
                    case PlaceInitialSoldiers:
                        placeSoldier(mouse);
                        break;
                    case place:
                        placeSoldier(mouse);
                        break;
                    case AttackFrom:
                        selectOwnerCountry(mouse);
                        break;
                    case Attack:
                        selectOppCountry(mouse);
                        break;
                    case KeepAttacking:
                        keepAttacking(mouse);
                        break;
                    case NewCountry:
                        placeSoldierNewCountry(mouse);
                        break;
                    case GameOver:
                        break;
                }
                tInfo.setText(displayText());
                repaint();
            }

        });

    }



    private void InitializePlayers() {
        players = new Player[2];
        players[0] = new Player();
        players[1] = new Player();
    }


    private void InitializeContinents() {

        continents = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            continents.add(i, new ArrayList<>());
        }

        ArrayList<Country> thisContinent = continents.get(0);
        for (int i = 0; i < 7; i++) {
            thisContinent.add(countries[i]);
        }

        thisContinent = continents.get(1);
        for (int i = 7; i < 11; i++) {
            thisContinent.add(countries[i]);
        }

        thisContinent = continents.get(2);
        for (int i = 11; i < 17; i++) {
            thisContinent.add(countries[i]);
        }

        thisContinent = continents.get(3);
        for (int i = 17; i < 23; i++) {
            thisContinent.add(countries[i]);
        }

        thisContinent = continents.get(4);
        for (int i = 23; i < 32; i++) {
            thisContinent.add(countries[i]);
        }

        thisContinent = continents.get(5);
        for (int i = 32; i < 36; i++) {
            thisContinent.add(countries[i]);
        }
    }

    private void InitializeCountries() {
        countries = new Country[36];
        countries[0] = new Country( 30, 30, 80, 60);
        countries[1] = new Country(110, 80, 140, 50);
        countries[2] = new Country(140, 210, 65, 60);
        countries[3] = new Country(280, 20, 80, 60);
        countries[4] = new Country( 110, 30, 120, 50);
        countries[5] = new Country(250, 90, 50, 70);
        countries[6] = new Country(110, 130, 140, 80);


        countries[7] = new Country(200, 270, 70, 50);
        countries[8] = new Country(230, 320, 90, 120);
        countries[9] = new Country(190, 320, 40, 120);
        countries[10] = new Country(200, 440, 100, 100);


        countries[11] = new Country( 400, 110, 20, 40);
        countries[12] = new Country( 440, 140, 60, 40);
        countries[13] = new Country( 450, 20, 50, 100);
        countries[14] = new Country(500, 70, 100, 110);
        countries[15] = new Country(440, 180, 80, 40);
        countries[16] = new Country(380, 160, 60, 40);

        countries[17] = new Country(540, 400, 20, 40);
        countries[18] = new Country(450, 240, 70, 40);
        countries[19] = new Country(360, 240, 90, 90);
        countries[20] = new Country(450, 280, 80, 110);
        countries[21] = new Country(390, 330, 60, 60);
        countries[22] = new Country( 420, 390, 80, 110);

        countries[23] = new Country( 520, 180, 110, 70);
        countries[24] = new Country( 600, 110, 160, 70);
        countries[25] = new Country(600, 55, 80, 55);
        countries[26] = new Country( 630, 180, 60, 100);
        countries[27] = new Country( 680, 35, 30, 75);
        countries[28] = new Country(690, 180, 40, 70);
        countries[29] = new Country( 710, 30, 60, 80);
        countries[30] = new Country( 770, 30, 30, 70);
        countries[31] = new Country( 810, 80, 20, 40);

        countries[32] = new Country( 730, 300, 30, 30);
        countries[33] = new Country( 740, 360, 60, 70);
        countries[34] = new Country( 800, 360, 50, 70);
        countries[35] = new Country( 800, 310, 40, 25);

        countries[0].adjacentCountries = new ArrayList<Country>();
        countries[0].adjacentCountries.add(countries[1]);
        countries[0].adjacentCountries.add(countries[5]);
        countries[0].adjacentCountries.add(countries[31]);

        countries[1].adjacentCountries = new ArrayList<Country>();
        countries[1].adjacentCountries.add(countries[0]);
        countries[1].adjacentCountries.add(countries[3]);
        countries[1].adjacentCountries.add(countries[4]);
        countries[1].adjacentCountries.add(countries[5]);
        countries[1].adjacentCountries.add(countries[6]);

        countries[2].adjacentCountries = new ArrayList<Country>();
        countries[2].adjacentCountries.add(countries[6]);
        countries[2].adjacentCountries.add(countries[7]);


        countries[3].adjacentCountries = new ArrayList<Country>();
        countries[3].adjacentCountries.add(countries[1]);
        countries[3].adjacentCountries.add(countries[4]);
        countries[3].adjacentCountries.add(countries[5]);
        countries[3].adjacentCountries.add(countries[11]);


        countries[4].adjacentCountries = new ArrayList<Country>();
        countries[4].adjacentCountries.add(countries[0]);
        countries[4].adjacentCountries.add(countries[1]);
        countries[4].adjacentCountries.add(countries[3]);

        countries[5].adjacentCountries = new ArrayList<Country>();
        countries[5].adjacentCountries.add(countries[1]);
        countries[5].adjacentCountries.add(countries[3]);
        countries[5].adjacentCountries.add(countries[6]);

        countries[6].adjacentCountries = new ArrayList<Country>();
        countries[6].adjacentCountries.add(countries[1]);
        countries[6].adjacentCountries.add(countries[2]);
        countries[6].adjacentCountries.add(countries[5]);

        countries[7].adjacentCountries = new ArrayList<Country>();
        countries[7].adjacentCountries.add(countries[2]);
        countries[7].adjacentCountries.add(countries[8]);
        countries[7].adjacentCountries.add(countries[9]);


        countries[8].adjacentCountries = new ArrayList<Country>();
        countries[8].adjacentCountries.add(countries[7]);
        countries[8].adjacentCountries.add(countries[9]);
        countries[8].adjacentCountries.add(countries[10]);

        countries[9].adjacentCountries = new ArrayList<Country>();
        countries[9].adjacentCountries.add(countries[7]);
        countries[9].adjacentCountries.add(countries[8]);
        countries[9].adjacentCountries.add(countries[10]);

        countries[10].adjacentCountries = new ArrayList<Country>();
        countries[10].adjacentCountries.add(countries[8]);
        countries[10].adjacentCountries.add(countries[9]);

        countries[11].adjacentCountries = new ArrayList<Country>();
        countries[11].adjacentCountries.add(countries[3]);
        countries[11].adjacentCountries.add(countries[12]);
        countries[11].adjacentCountries.add(countries[13]);

        countries[12].adjacentCountries = new ArrayList<Country>();
        countries[12].adjacentCountries.add(countries[11]);
        countries[12].adjacentCountries.add(countries[13]);
        countries[12].adjacentCountries.add(countries[14]);
        countries[12].adjacentCountries.add(countries[15]);
        countries[12].adjacentCountries.add(countries[16]);

        countries[13].adjacentCountries = new ArrayList<Country>();
        countries[13].adjacentCountries.add(countries[11]);
        countries[13].adjacentCountries.add(countries[12]);
        countries[13].adjacentCountries.add(countries[14]);


        countries[14].adjacentCountries = new ArrayList<Country>();
        countries[14].adjacentCountries.add(countries[12]);
        countries[14].adjacentCountries.add(countries[13]);
        countries[14].adjacentCountries.add(countries[15]);
        countries[14].adjacentCountries.add(countries[23]);
        countries[14].adjacentCountries.add(countries[24]);
        countries[14].adjacentCountries.add(countries[25]);

        countries[15].adjacentCountries = new ArrayList<Country>();
        countries[15].adjacentCountries.add(countries[12]);
        countries[15].adjacentCountries.add(countries[14]);
        countries[15].adjacentCountries.add(countries[16]);
        countries[15].adjacentCountries.add(countries[18]);
        countries[15].adjacentCountries.add(countries[19]);
        countries[15].adjacentCountries.add(countries[23]);

        countries[16].adjacentCountries = new ArrayList<Country>();
        countries[16].adjacentCountries.add(countries[11]);
        countries[16].adjacentCountries.add(countries[12]);
        countries[16].adjacentCountries.add(countries[15]);
        countries[16].adjacentCountries.add(countries[19]);

        countries[17].adjacentCountries = new ArrayList<Country>();
        countries[17].adjacentCountries.add(countries[20]);
        countries[17].adjacentCountries.add(countries[22]);

        countries[18].adjacentCountries = new ArrayList<Country>();
        countries[18].adjacentCountries.add(countries[15]);
        countries[18].adjacentCountries.add(countries[19]);
        countries[18].adjacentCountries.add(countries[20]);
        countries[18].adjacentCountries.add(countries[23]);

        countries[19].adjacentCountries = new ArrayList<Country>();
        countries[19].adjacentCountries.add(countries[8]);
        countries[19].adjacentCountries.add(countries[12]);
        countries[19].adjacentCountries.add(countries[15]);
        countries[19].adjacentCountries.add(countries[18]);
        countries[19].adjacentCountries.add(countries[20]);
        countries[19].adjacentCountries.add(countries[21]);

        countries[20].adjacentCountries = new ArrayList<Country>();
        countries[20].adjacentCountries.add(countries[17]);
        countries[20].adjacentCountries.add(countries[18]);
        countries[20].adjacentCountries.add(countries[19]);
        countries[20].adjacentCountries.add(countries[21]);
        countries[20].adjacentCountries.add(countries[22]);
        countries[20].adjacentCountries.add(countries[23]);

        countries[21].adjacentCountries = new ArrayList<Country>();
        countries[21].adjacentCountries.add(countries[18]);
        countries[21].adjacentCountries.add(countries[19]);
        countries[21].adjacentCountries.add(countries[20]);
        countries[21].adjacentCountries.add(countries[22]);

        countries[22].adjacentCountries = new ArrayList<Country>();
        countries[22].adjacentCountries.add(countries[17]);
        countries[22].adjacentCountries.add(countries[20]);
        countries[22].adjacentCountries.add(countries[21]);

        countries[23].adjacentCountries = new ArrayList<Country>();
        countries[23].adjacentCountries.add(countries[14]);
        countries[23].adjacentCountries.add(countries[15]);
        countries[23].adjacentCountries.add(countries[18]);
        countries[23].adjacentCountries.add(countries[20]);
        countries[23].adjacentCountries.add(countries[24]);
        countries[23].adjacentCountries.add(countries[26]);

        countries[24].adjacentCountries = new ArrayList<Country>();
        countries[24].adjacentCountries.add(countries[14]);
        countries[24].adjacentCountries.add(countries[23]);
        countries[24].adjacentCountries.add(countries[26]);
        countries[24].adjacentCountries.add(countries[28]);
        countries[24].adjacentCountries.add(countries[25]);
        countries[24].adjacentCountries.add(countries[27]);
        countries[24].adjacentCountries.add(countries[29]);

        countries[25].adjacentCountries = new ArrayList<Country>();
        countries[25].adjacentCountries.add(countries[14]);
        countries[25].adjacentCountries.add(countries[24]);
        countries[25].adjacentCountries.add(countries[27]);

        countries[26].adjacentCountries = new ArrayList<Country>();
        countries[26].adjacentCountries.add(countries[23]);
        countries[26].adjacentCountries.add(countries[24]);
        countries[26].adjacentCountries.add(countries[28]);

        countries[27].adjacentCountries = new ArrayList<Country>();
        countries[27].adjacentCountries.add(countries[24]);
        countries[27].adjacentCountries.add(countries[25]);
        countries[27].adjacentCountries.add(countries[29]);

        countries[28].adjacentCountries = new ArrayList<Country>();
        countries[28].adjacentCountries.add(countries[24]);
        countries[28].adjacentCountries.add(countries[26]);
        countries[28].adjacentCountries.add(countries[32]);

        countries[29].adjacentCountries = new ArrayList<Country>();
        countries[29].adjacentCountries.add(countries[24]);
        countries[29].adjacentCountries.add(countries[27]);
        countries[29].adjacentCountries.add(countries[30]);
        countries[29].adjacentCountries.add(countries[31]);

        countries[30].adjacentCountries = new ArrayList<Country>();
        countries[30].adjacentCountries.add(countries[0]);
        countries[30].adjacentCountries.add(countries[29]);
        countries[30].adjacentCountries.add(countries[31]);

        countries[31].adjacentCountries = new ArrayList<Country>();
        countries[31].adjacentCountries.add(countries[29]);
        countries[31].adjacentCountries.add(countries[30]);

        countries[32].adjacentCountries = new ArrayList<Country>();
        countries[32].adjacentCountries.add(countries[28]);
        countries[32].adjacentCountries.add(countries[33]);
        countries[32].adjacentCountries.add(countries[35]);

        countries[33].adjacentCountries = new ArrayList<Country>();
        countries[33].adjacentCountries.add(countries[32]);
        countries[33].adjacentCountries.add(countries[34]);
        countries[33].adjacentCountries.add(countries[35]);

        countries[34].adjacentCountries = new ArrayList<Country>();
        countries[34].adjacentCountries.add(countries[33]);
        countries[34].adjacentCountries.add(countries[35]);

        countries[35].adjacentCountries = new ArrayList<Country>();
        countries[35].adjacentCountries.add(countries[32]);
        countries[35].adjacentCountries.add(countries[33]);
        countries[35].adjacentCountries.add(countries[34]);

    }

    private void assignCountries() {
        int swtch = 0;
        Country[] shuffledCountries = countries.clone();
        List<Country> shufflelist = Arrays.asList(shuffledCountries);
        Collections.shuffle(shufflelist);
        for (int i = 0; i < countries.length; i++) {
            players[swtch].countriesOwned.add(shuffledCountries[i]);
            swtch = (swtch + 1) % 2;
        }
    }



    private void placeSoldier(Point mouse) {

        for (Country c : players[turn].countriesOwned) {
            if (c.isInside(mouse)) {
                c.numSoldiers++;
                Troops--;
            }
        }
        if (Troops == 0) {
            if (mode == Mode.PlaceInitialSoldiers) {
                turn++;
                if (turn == players.length) {
                    turn = 0;
                    updateTroops();
                    nextMode();
                } else {
                    Troops = 15;
                }
            } else {
                nextMode();
            }
        }
    }



    private boolean continentOwned(int continent) {
        for (Country c : continents.get(continent)) {
            if (!players[turn].countriesOwned.contains(c)) {
                return false;
            }
        }
        return true;
    }

    private void updateTroops() {

        int countryBonus = players[turn].countriesOwned.size() / 3;
        Troops = Math.max(3, countryBonus);

        for (int i = 0; i < Board.continentBonuses.length; i++) {
            if (continentOwned(i)) {
                Troops += continentBonuses[i];
            }
        }
    }

    private void selectOwnerCountry(Point mouse) {
        for (Country c : players[turn].countriesOwned) {
            if (c.isInside(mouse) && c.numSoldiers > 1) {
                selectedCountry = c;
                nextMode();
            }
        }
    }

    private void selectOppCountry(Point mouse) {
        if (selectedCountry.isInside(mouse)) {
            selectedCountry = null;
            mode = Mode.AttackFrom;
            return;
        }
        for (Country c : selectedCountry.adjacentCountries) {
            if (c.isInside(mouse) && !players[turn].countriesOwned.contains(c)) {
                secondSelectedCountry = c;
                attack(selectedCountry, secondSelectedCountry);
                checkOutcome();
                if (mode == Mode.Attack) {
                    nextMode();
                }
            }
        }
    }

    private int roll() {
        return (int) (Math.random() * 6);
    }

    private void attack(Country own, Country opp) {
        int[] attack = new int[3];
        int[] defend = new int[2];

        for (int i = 0; i < attack.length; i++) {
            attack[i] = roll();
        }
        for (int i = 0; i < defend.length; i++) {
            defend[i] = roll();
        }

        if (attack[0] > defend[0]) {
            opp.numSoldiers--;
        } else {
            own.numSoldiers--;
        }
        if (attack[1] != 0 && defend[1] != 0) {
            if (attack[1] > defend[1]) {
                opp.numSoldiers--;
            } else {
                own.numSoldiers--;
            }
        }
    }

    private void checkOutcome() {
        if (selectedCountry.numSoldiers == 1) {
            selectedCountry = null;
            secondSelectedCountry = null;
            mode = Mode.AttackFrom;
            return;
        }
        if (secondSelectedCountry.numSoldiers == 0) {
            mode = Mode.NewCountry;
            conquer();
        }
    }

    private void keepAttacking(Point mouse) {
        if (selectedCountry.isInside(mouse)) {
            selectedCountry = null;
            secondSelectedCountry = null;
            mode = Mode.AttackFrom;
            return;
        }

        if (secondSelectedCountry.isInside(mouse)) {
            attack(selectedCountry, secondSelectedCountry);
            checkOutcome();
        }

    }

    private void conquer() {
        Player fill = null;
        for (Player p : players) {
            if (p.countriesOwned.contains(secondSelectedCountry)) {
                fill = p;
            }
        }
        fill.countriesOwned.remove(secondSelectedCountry);
        players[turn].countriesOwned.add(secondSelectedCountry);

        if (fill.countriesOwned.isEmpty()) {
            fill.dead = true;
        }
        checkWin();
        secondSelectedCountry.numSoldiers = 1;
        Troops = selectedCountry.numSoldiers - 2;
        selectedCountry.numSoldiers = 1;

        if (Troops == 0) {
            selectedCountry = null;
            secondSelectedCountry = null;
            nextMode();
        }
    }

    private void placeSoldierNewCountry(Point mouse) {

        if (selectedCountry.isInside(mouse)) {
            Troops--;
            selectedCountry.numSoldiers++;
        }
        if (secondSelectedCountry.isInside(mouse)) {
            Troops--;
            secondSelectedCountry.numSoldiers++;
        }

        if (Troops == 0) {
            selectedCountry = null;
            secondSelectedCountry = null;
            nextMode();
        }
    }

    public String displayText() {
        String plyr = "Player " + (turn + 1) + ": ";
        switch (mode) {
            case PlaceInitialSoldiers:
                return plyr + "Place troops = " + Troops;
            case place:
                return plyr + "Place troops = " + Troops;
            case AttackFrom:
                return plyr + "Choose country to attack from: ";
            case Attack:
                return plyr + "Choose country to attack: ";
            case KeepAttacking:
                return plyr + "Keep Attacking? ";
            case NewCountry:
                return plyr + "Conquered! " + Troops + " Troops remaining";
            case EndTurn:
                return plyr + "End turn?";
            case GameOver:
                return plyr + "You win!";
            default:
                return "";
        }
    }

    private void nextPlayer() {
        selectedCountry = null;
        secondSelectedCountry = null;
        turn = (turn + 1) % players.length;
        while (players[turn].dead) {
            turn = (turn + 1) % players.length;
        }
        mode = Mode.place;
        updateTroops();
    }

    private void checkWin() {
        int numDead = 0;
        for (Player p : players) {
            if (p.dead) {
                numDead++;
            }
        }
        if (numDead == players.length - 1) {
            mode = Mode.GameOver;
            tInfo.setText(displayText());
            repaint();
        }
    }
    private void nextMode() {
        switch (mode) {
            case PlaceInitialSoldiers:
                mode = Mode.place;
                break;
            case place:
                mode = Mode.AttackFrom;
                break;
            case AttackFrom:
                mode = Mode.Attack;
                break;
            case Attack:
                mode = Mode.KeepAttacking;
                break;
            case KeepAttacking:
                mode = Mode.NewCountry;
                break;
            case NewCountry:
                mode = Mode.AttackFrom;
                break;
            case GameOver:
                break;
        }
    }

    public void cont() {
        switch (mode) {
            case PlaceInitialSoldiers:
                break;
            case place:
                break;
            case AttackFrom:
                mode = Mode.EndTurn;
                selectedCountry = null;
                break;
            case Attack:
                mode = Mode.EndTurn;
                selectedCountry = null;
                secondSelectedCountry = null;
                break;
            case KeepAttacking:
                mode = Mode.AttackFrom;
                selectedCountry = null;
                secondSelectedCountry = null;
                break;
            case NewCountry:
                break;
            case EndTurn:
                nextPlayer();
                break;
            case GameOver:
                break;
        }
        tInfo.setText(displayText());
        repaint();
    }

    private void drawLines(Graphics g) {
        g.drawLine(0, 60, 30, 60);
        g.drawLine(800, 60, 900, 60);

        g.drawLine(230, 55, 280, 50);
        g.drawLine(250, 80, 280, 50);
        g.drawLine(300, 90, 310, 80);
        g.drawLine(360, 80, 400, 120);
        g.drawLine(410, 150, 410, 160);

        g.drawLine(420, 130, 440, 140);
        g.drawLine(475, 120, 470, 140);
        g.drawLine(480, 220, 485, 240);
        g.drawLine(440, 220, 405, 240);
        g.drawLine(410, 200, 405, 240);
        g.drawLine(530, 280, 575, 250);
        g.drawLine(530, 390, 540, 400);
        g.drawLine(500, 445, 540, 420);
        g.drawLine(320, 320, 360, 285);
        g.drawLine(810, 120, 760, 140);
        g.drawLine(810, 80, 800, 65);
        g.drawLine(710, 250, 745, 300);
        g.drawLine(760, 315, 800, 323);
        g.drawLine(745, 330, 770, 360);
        g.drawLine(820, 335, 825, 360);
        g.drawLine(770, 360, 800, 335);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(73, 150, 154));
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            for (Country c : player.countriesOwned) {
                g.setColor(colors[i]);
                c.draw(g);
            }
        }
        drawLines(g);
    }


    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

}
