package mazmorra.ranking;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class Table extends JTable {

    private String name;
    private int width;
    private int heigth;

    public Table(String name, int width, int heigth) {
        this.name = name;
        this.width = width;
        this.heigth = heigth;
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        String tooltip = null;
        java.awt.Point p = event.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);

        try {
            tooltip = getValueAt(rowIndex, colIndex).toString();
        } catch (RuntimeException e1) {
            //catch null pointer exception if mouse is over an empty line
        }

        return tooltip;
    }

}
