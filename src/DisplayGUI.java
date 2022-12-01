import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

public class DisplayGUI extends JFrame {

    JFrame frame = new JFrame();
    JPanel mainPnl;
    JPanel topPnl;
    JPanel midPnl;
    JPanel botPnl;

    JLabel titleLbl;
    JTextArea displayTags;
    JScrollPane scroller;

    JButton saveButton;
    JButton quitButton;

    ArrayList<String> filesToWrite = new ArrayList<String>();

    public DisplayGUI() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        createTopPanel();
        mainPnl.add(topPnl, BorderLayout.NORTH);
        createMiddlePanel();
        mainPnl.add(midPnl, BorderLayout.CENTER);
        createBottomPanel();
        mainPnl.add(botPnl, BorderLayout.SOUTH);

        add(mainPnl);

        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createTopPanel() {
        topPnl = new JPanel();
        titleLbl = new JLabel("Tag Extractor", JLabel.CENTER);
        titleLbl.setVerticalTextPosition(JLabel.BOTTOM);
        titleLbl.setHorizontalTextPosition(JLabel.CENTER);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 70));

        topPnl.add(titleLbl);
    }

    private void createMiddlePanel() {
        midPnl = new JPanel();
        displayTags = new JTextArea(40, 60);
        scroller = new JScrollPane(displayTags);
        midPnl.add(scroller);
    }

    private void createBottomPanel() {
        botPnl = new JPanel();
        botPnl.setLayout(new GridLayout(1, 2));
        saveButton = new JButton("Save");
        quitButton = new JButton("Quit");

        quitButton.addActionListener((ActionEvent ae) -> System.exit(0));

        saveButton.addActionListener((ActionEvent ae) -> {
            FileWriter fileWrite = new FileWriter();
            fileWrite.writeFile(filesToWrite);
        });

        botPnl.add(saveButton);
        botPnl.add(quitButton);
    }

    public void writeToTextBox(String line)
    {
        displayTags.append(line + "\n");
        filesToWrite.add(line + "\n");
    }
}