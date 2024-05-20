package mazmorra;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * Clase SelectType
 *
 * Contiene el programa
 *
 * @author Fran
 * @version 1.0
 */

public class SelectType {

    private static JFrame frame = new JFrame("Select characters");

    private JPanel selectCharacter;
    private JButton magButton;
    private JButton guerrerButton;
    private JButton sacerdotButton;
    private JPanel charactersPanel;
    private JTextField playerName;
    private JButton startButton;
    private JPanel playerDataPanel;
    private JPanel titlePanel;
    private JLabel lblTitle;
    private JLabel lblRanking;
    private JButton exitButton;
    private JButton btnRanking;
    private JPanel magAttributes;
    private JPanel guerrerAttributes;
    private JPanel sacerdotAttributes;

    private String type = "";
    private String name = "";
    private String level = "";

    /**
     * Funcion main
     *
     * Contiene el flujo de ejecución del programa
     * @param args prueba
     *
     */
    public static void main(String[] args) {

        frame.setContentPane(new SelectType().selectCharacter);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setPreferredSize(new Dimension(700, 600));
        frame.setSize(frame.getPreferredSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }


    public SelectType(){

        final String IMG_MAG = "img/wizard/wizard_selection.png";
        final String IMG_GUERRER = "img/warrior/warrior_selection.png";
        final String IMG_SACERDOT = "img/priest/priest_selection.png";

        magButton.setPreferredSize(new Dimension(98,103));
        magButton.setSize(magButton.getPreferredSize());
        URL url = getClass().getResource(IMG_MAG);
        ImageIcon imageIcon = new ImageIcon(url);
        Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(39,74,Image.SCALE_DEFAULT));

        magButton.setIcon(icon);
        magButton.setBackground(Color.WHITE);
        magButton.setToolTipText("wizard");
        magButton.setFocusPainted(false);
        magButton.addMouseListener(new CursorPointer(magButton));

        guerrerButton.setPreferredSize(new Dimension(98,103));
        guerrerButton.setSize(guerrerButton.getPreferredSize());
        url = getClass().getResource(IMG_GUERRER);
        imageIcon = new ImageIcon(url);
        icon = new ImageIcon(imageIcon.getImage().getScaledInstance(39,74,Image.SCALE_DEFAULT));

        guerrerButton.setIcon(icon);
        guerrerButton.setBackground(Color.WHITE);
        guerrerButton.setToolTipText("warrior");
        guerrerButton.setFocusPainted(false);
        guerrerButton.addMouseListener(new CursorPointer(guerrerButton));

        sacerdotButton.setPreferredSize(new Dimension(98,103));
        sacerdotButton.setSize(sacerdotButton.getPreferredSize());
        url = getClass().getResource(IMG_SACERDOT);
        imageIcon = new ImageIcon(url);
        icon = new ImageIcon(imageIcon.getImage().getScaledInstance(39,74,Image.SCALE_DEFAULT));

        sacerdotButton.setIcon(icon);
        sacerdotButton.setBackground(Color.WHITE);
        sacerdotButton.setToolTipText("priest");
        sacerdotButton.setFocusPainted(false);
        sacerdotButton.addMouseListener(new CursorPointer(sacerdotButton));

        startButton.setEnabled(false);
        startButton.setPreferredSize(new Dimension(100,60));
        startButton.setSize(startButton.getPreferredSize());
        exitButton.setPreferredSize(new Dimension(100,30));
        exitButton.setSize(startButton.getPreferredSize());

        playerName.setEnabled(false);
        playerName.setPreferredSize(new Dimension(100,40));
        playerName.setSize(playerName.getPreferredSize());
        playerName.setHorizontalAlignment(JTextField.CENTER);
        playerName.setFont(new Font("Calibri", Font.BOLD, 20));

        setCharacterAttributes("wizard");
        setCharacterAttributes("warrior");
        setCharacterAttributes("priest");


        events();
        settings();
        onlyUpperCase();
    }

    private void setCharacterAttributes(String playerType) {

        if(playerType.equals("wizard")){
            magAttributes.setLayout(null);

            JLabel lblLifes = new JLabel("Lifes:");
            lblLifes.setPreferredSize(new Dimension(35,20));
            lblLifes.setSize(lblLifes.getPreferredSize());
            lblLifes.setLocation(3,0);
            magAttributes.add(lblLifes);
            int position = lblLifes.getWidth() + 5;
            for (int i = 0; i < 5; i++) {
                JLabel iconLifes = new JLabel();
                iconLifes.setPreferredSize(new Dimension(18,15));
                iconLifes.setSize(iconLifes.getPreferredSize());
                iconLifes.setLocation(position,3);
                Icon icon;
                icon = setIcon();
                iconLifes.setIcon(icon);
                magAttributes.add(iconLifes);
                position = position + iconLifes.getWidth();
            }
            JLabel lblSpeed = new JLabel("Speed: x7");
            lblSpeed.setPreferredSize(new Dimension(60,20));
            lblSpeed.setSize(lblSpeed.getPreferredSize());
            lblSpeed.setLocation(3,lblLifes.getHeight());
            magAttributes.add(lblSpeed);

            JLabel lblAttack = new JLabel("Attack: 70 points");
            lblAttack.setPreferredSize(new Dimension(110,20));
            lblAttack.setSize(lblAttack.getPreferredSize());
            lblAttack.setLocation(3,lblLifes.getHeight() + lblSpeed.getHeight());
            magAttributes.add(lblAttack);
        } else if (playerType.equals("warrior")) {
            guerrerAttributes.setLayout(null);

            JLabel lblLifes = new JLabel("Lifes:");
            lblLifes.setPreferredSize(new Dimension(40,20));
            lblLifes.setSize(lblLifes.getPreferredSize());
            lblLifes.setLocation(3,0);
            guerrerAttributes.add(lblLifes);
            int position = lblLifes.getWidth() + 5;
            for (int i = 0; i < 3; i++) {
                JLabel iconLifes = new JLabel();
                iconLifes.setPreferredSize(new Dimension(18,15));
                iconLifes.setSize(iconLifes.getPreferredSize());
                iconLifes.setLocation(position,3);
                Icon icon;
                icon = setIcon();
                iconLifes.setIcon(icon);
                guerrerAttributes.add(iconLifes);
                position = position + iconLifes.getWidth();
            }
            JLabel lblSpeed = new JLabel("Speed: x3");
            lblSpeed.setPreferredSize(new Dimension(60,20));
            lblSpeed.setSize(lblSpeed.getPreferredSize());
            lblSpeed.setLocation(3,lblLifes.getHeight());
            guerrerAttributes.add(lblSpeed);

            JLabel lblAttack = new JLabel("Attack: 120 points");
            lblAttack.setPreferredSize(new Dimension(110,20));
            lblAttack.setSize(lblAttack.getPreferredSize());
            lblAttack.setLocation(3,lblLifes.getHeight() + lblSpeed.getHeight());
            guerrerAttributes.add(lblAttack);
        }else {
            sacerdotAttributes.setLayout(null);

            JLabel lblLifes = new JLabel("Lifes:");
            lblLifes.setPreferredSize(new Dimension(40,20));
            lblLifes.setSize(lblLifes.getPreferredSize());
            lblLifes.setLocation(3,0);
            sacerdotAttributes.add(lblLifes);
            int position = lblLifes.getWidth() + 5;
            for (int i = 0; i < 4; i++) {
                JLabel iconLifes = new JLabel();
                iconLifes.setPreferredSize(new Dimension(18,15));
                iconLifes.setSize(iconLifes.getPreferredSize());
                iconLifes.setLocation(position,3);
                Icon icon;
                icon = setIcon();
                iconLifes.setIcon(icon);
                sacerdotAttributes.add(iconLifes);
                position = position + iconLifes.getWidth();
            }
            JLabel lblSpeed = new JLabel("Speed: x5");
            lblSpeed.setPreferredSize(new Dimension(60,20));
            lblSpeed.setSize(lblSpeed.getPreferredSize());
            lblSpeed.setLocation(3,lblLifes.getHeight());
            sacerdotAttributes.add(lblSpeed);

            JLabel lblAttack = new JLabel("Attack: 90 points");
            lblAttack.setPreferredSize(new Dimension(110,20));
            lblAttack.setSize(lblAttack.getPreferredSize());
            lblAttack.setLocation(3,lblLifes.getHeight() + lblSpeed.getHeight());
            sacerdotAttributes.add(lblAttack);
        }
    }

    private void settings() {

        lblTitle.setFont(new Font("Calibri", Font.BOLD, 45));
        UIManager.put("Button.select", Color.WHITE);
        btnRanking.setFont(new Font("Calibri", Font.BOLD, 20));
        btnRanking.setBackground(Color.WHITE);
        btnRanking.setFocusable(false);

        magButton.setBackground(Color.decode("#BBBBBB"));
        guerrerButton.setBackground(Color.decode("#BBBBBB"));
        sacerdotButton.setBackground(Color.decode("#BBBBBB"));

    }

    private class CursorPointer extends MouseAdapter{

        JButton button;

        public CursorPointer(JButton button){
            this.button = button;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    private void events(){

        magButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                magButton.setBackground(Color.WHITE);
                guerrerButton.setBackground(Color.decode("#BBBBBB"));
                sacerdotButton.setBackground(Color.decode("#BBBBBB"));
                type = "wizard";
                enablePlayerName();
            }
        });

        guerrerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                magButton.setBackground(Color.decode("#BBBBBB"));
                guerrerButton.setBackground(Color.WHITE);
                sacerdotButton.setBackground(Color.decode("#BBBBBB"));
                type = "warrior";
                enablePlayerName();
            }
        });

        sacerdotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                magButton.setBackground(Color.decode("#BBBBBB"));
                guerrerButton.setBackground(Color.decode("#BBBBBB"));
                sacerdotButton.setBackground(Color.WHITE);
                type = "priest";
                enablePlayerName();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Game(type,name,level);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });

        playerName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                name = playerName.getText();
                enableStart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                name = playerName.getText();
                enableStart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        playerName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    startButton.doClick();
                }
            }
        });

        startButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    startButton.doClick();
                }
            }
        });

        btnRanking.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                new Ranking();
            }
        });
    }

    private void onlyUpperCase() {
        ((AbstractDocument) (playerName.getDocument())).setDocumentFilter(new DocumentFilter() {
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                fb.insertString(offset, string.toUpperCase(), attr);
            }

            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)
                    throws BadLocationException {
                fb.replace(offset, length, string.toUpperCase(), attr);
            }
        });
    }

    private void enableStart() {

        if(!(type.equals("")) && !(name.equals(""))) {
            startButton.setEnabled(true);
        }else{
            startButton.setEnabled(false);
        }
    }

    private void enablePlayerName() {

        playerName.setText("");
        playerName.setEnabled(true);
        playerName.requestFocus();
    }

    private Icon setIcon(){

        final String IMG_COR = "img/dungeon/heart.png";
        JLabel iconLifes = new JLabel();
        URL urlPlayer;
        ImageIcon iconItem;

        iconLifes.setPreferredSize(new Dimension(15,15));
        iconLifes.setSize(iconLifes.getPreferredSize());

        urlPlayer = getClass().getResource(IMG_COR);
        iconItem = new ImageIcon(urlPlayer);
        Icon icon = new ImageIcon(iconItem.getImage().getScaledInstance(iconLifes.getWidth(),iconLifes.getHeight(),Image.SCALE_DEFAULT));

        return icon;
    }

}
