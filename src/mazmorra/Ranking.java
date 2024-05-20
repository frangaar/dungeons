package mazmorra;

import mazmorra.ranking.Table;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Ranking extends JTable{
    private JPanel rankingPanel;
    private JPanel panelTitle;
    private JPanel panelContent;
    private JPanel panelType;
    private JPanel panelGeneral;
    private JPanel panelWizard;
    private JPanel panelWarrior;
    private JPanel panelPriest;
    private JButton btnVolver;
    private JLabel lblRankingList;
    JFrame frame = new JFrame("Ranking");

    ArrayList<String> rankingSplitLines = new ArrayList<>();
    ArrayList<String> generalRanking = new ArrayList<>();
    ArrayList<String> wizardRanking = new ArrayList<>();
    ArrayList<String> warriorRanking = new ArrayList<>();
    ArrayList<String> priestRanking = new ArrayList<>();

    public Ranking(){
        frame.setContentPane(rankingPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);




        String[] headers = {};
        headers = generateHeaders();


        DefaultTableModel tblModelGeneral = new DefaultTableModel();
        tblModelGeneral = generateModel(headers);

        DefaultTableModel tblModelWizard = new DefaultTableModel();
        tblModelWizard = generateModel(headers);

        DefaultTableModel tblModelWarrior = new DefaultTableModel();
        tblModelWarrior = generateModel(headers);

        DefaultTableModel tblModelPriest = new DefaultTableModel();
        tblModelPriest = generateModel(headers);

        String rankingfile = "src/mazmorra/ranking/ranking.txt";
        retrieveData(rankingfile);

        final int TABLE_WIDTH = 610;
        final int TABLE_HEIGHT = 150;

        List<String> dataTable = new ArrayList<>();

        Table tblGeneral = new Table("Ranking General", TABLE_WIDTH,TABLE_HEIGHT);

        dataTable = formatDataTable(generalRanking);
        generateTable(dataTable, tblModelGeneral,tblGeneral,panelGeneral,TABLE_WIDTH,TABLE_HEIGHT);

        Table tblWizard = new Table("Wizard Ranking", TABLE_WIDTH,TABLE_HEIGHT);

        dataTable = formatDataTable(wizardRanking);
        generateTable(dataTable, tblModelWizard,tblWizard,panelWizard,TABLE_WIDTH,TABLE_HEIGHT);

        Table tblWarrior = new Table("Warrior Ranking", TABLE_WIDTH,TABLE_HEIGHT);

        dataTable = formatDataTable(warriorRanking);
        generateTable(dataTable, tblModelWarrior,tblWarrior, panelWarrior,TABLE_WIDTH,TABLE_HEIGHT);

        Table tblPriest = new Table("Priest Ranking", TABLE_WIDTH,TABLE_HEIGHT);

        dataTable = formatDataTable(priestRanking);
        generateTable(dataTable, tblModelPriest,tblPriest,panelPriest,TABLE_WIDTH,TABLE_HEIGHT);


        events();
        setBackgroundItems();
        setFontTitleRanking();
        setVolverButton();
    }

    private String[] generateHeaders() {

        String headers[] = {};

        headers = new String[5];

        headers[0] = "Name".toUpperCase();
        headers[1] = "Type".toUpperCase();
        headers[2] = "Remaining_lifes".toUpperCase();
        headers[3] = "Gold_amount".toUpperCase();
        headers[4] = "Game_duration(sec)".toUpperCase();

        return headers;
    }

    private DefaultTableModel generateModel(String[] headers) {

        DefaultTableModel tblModel = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        tblModel.setColumnIdentifiers(headers);

        return tblModel;
    }

    private void generateTable(List<String> dataTable, DefaultTableModel tblModel, JTable tableName, JPanel panelName, int width, int height) {

        tableName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableName.getTableHeader().setResizingAllowed(false);
        // Si se pone esta opción, no aparece scrollbar
        //tableName.setPreferredSize(new Dimension(width,height));
        tableName.setSize(new Dimension(width,height));
        tableName.setPreferredScrollableViewportSize(new Dimension(width,height));

        int rows = dataTable.size();
        String[] dataInfo = new String[5];
        int index = 0;

        sortData(dataTable);

        while (index < 5 && index < rows){
            dataInfo = dataTable.get(index).split(",");
            tblModel.addRow(dataInfo);
            index++;
        }

        tableName.setModel(tblModel);
        tableName.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Evitar que la tabla sea seleccionable
        tableName.setCellSelectionEnabled(false);
        tableName.setFocusable(false);


        TableColumnModel columns = tableName.getColumnModel();
        columns.getColumn(0).setMinWidth(160);
        columns.getColumn(1).setMinWidth(80);
        columns.getColumn(2).setMinWidth(110);
        columns.getColumn(3).setMinWidth(110);
        columns.getColumn(4).setMinWidth(150);

        tableName.getTableHeader().setPreferredSize(
                new Dimension(columns.getColumn(0).getWidth(),30)
        );

        tableName.getTableHeader().setFont(new Font("Serif", Font.BOLD, 12));
        tableName.getTableHeader().setForeground(Color.WHITE);
        tableName.getTableHeader().setBackground(Color.decode("#0066cc"));
        tableName.setShowGrid(false);


        tableName.setRowHeight(30);
        tableName.setFont(new Font("Serif", Font.BOLD, 18));


        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();

        centerRender.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(0).setCellRenderer(centerRender);
        columns.getColumn(1).setCellRenderer(centerRender);
        columns.getColumn(2).setCellRenderer(centerRender);
        columns.getColumn(3).setCellRenderer(centerRender);
        columns.getColumn(4).setCellRenderer(centerRender);

        JScrollPane jScrollPane = new JScrollPane(tableName);
        panelName.setLayout(new FlowLayout());
        panelName.add(jScrollPane);
    }

    private List<String> sortData(List<String> dataToSort){

        Collections.sort(dataToSort, new Comparator<String>() {
            @Override
            public int compare(String str2, String str1) {

                String[] row1 = str1.split(",");
                String[] row2 = str2.split(",");

                // Vidas[2]
                // Tiempo[4]
                // Oro[3]

                int firstCriteria = row1[2].compareTo(row2[2]);

                if (firstCriteria != 0) {
                    return firstCriteria;
                }
                int secondCriteria = row1[3].compareTo(row2[3]);

                if (secondCriteria != 0) {
                    return secondCriteria;
                }
                return row1[4].compareTo(row2[4]);
            }
        });

        return dataToSort;
    }

    private void retrieveData(String ranking){

        String todasEstadisticas = "";
        Path path = Paths.get(ranking);

        java.util.List<String> liniesFitxer = null;

        if(Files.exists(path)){

            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(ranking));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            String line;
            while (true) {
                try {
                    if (!((line = br.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(line.contains("wizard")){
                    wizardRanking.add(line);
                }else if(line.contains("warrior")){
                    warriorRanking.add(line);
                }else if(line.contains("priest")){
                    priestRanking.add(line);
                }

                generalRanking.add(line);
            }
        }
    }

    private List<String> formatDataTable(ArrayList<String> dataTable){

        String[] tableTemp = new String[5];

        String data = "";

        List<String> finalTable = new ArrayList<>();

        for (int i = 0; i < dataTable.size(); i++) {
            tableTemp = dataTable.get(i).split(",");

            for (int j = 0; j < tableTemp.length; j++) {

                if(j == 0) {
                    data = tableTemp[j];
                }else {
                    data += "," + tableTemp[j];
                }
            }
            finalTable.add(data);
        }

        return finalTable;
    }

    private void events(){

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                String[] args = {};
                SelectType.main(args);
            }
        });
    }

    private void setBackgroundItems(){

        panelTitle.setBackground(Color.WHITE);
        panelContent.setBackground(Color.WHITE);
        panelGeneral.setBackground(Color.WHITE);
        panelType.setBackground(Color.WHITE);
        panelWizard.setBackground(Color.WHITE);
        panelWarrior.setBackground(Color.WHITE);
        panelPriest.setBackground(Color.WHITE);

    }

    private void setVolverButton(){

        btnVolver.setFocusable(false);
        btnVolver.setBackground(Color.WHITE);
        btnVolver.setPreferredSize(new Dimension(21,15));
        btnVolver.setSize(btnVolver.getPreferredSize());
        final String IMG_RETURN = "img/ranking/return.png";
        URL url = getClass().getResource(IMG_RETURN);
        ImageIcon imageIcon = new ImageIcon(url);
        Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(btnVolver.getWidth(),btnVolver.getHeight(),Image.SCALE_DEFAULT));
        btnVolver.setIcon(icon);
    }

    private void setFontTitleRanking(){

        lblRankingList.setFont(new Font("Calibri", Font.BOLD, 40));
    }
}