package simulation;

import algorithm.Population;

import javax.swing.*;
import java.awt.*;

public class GraphicSimulation extends JPanel {

    Population population;
    JFrame frame;

    public GraphicSimulation() {
        frame = new JFrame("Epidemic Simulation");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(800, 800);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
    }

    public static void main(String[] args) {
        new GraphicSimulation();
    }

}
