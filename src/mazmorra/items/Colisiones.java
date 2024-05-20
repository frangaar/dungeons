package mazmorra.items;

import javax.swing.*;

public class Colisiones {

    public Double valueX;
    public Double valueY;
    public JLabel item;

    public Colisiones(double x, double y,JLabel item){
        this.valueX = x;
        this.valueY = y;
        this.item = item;
    }

    public void setValueX(Double valueX) {
        this.valueX = valueX;
    }

    public void setValueY(Double valueY) {
        this.valueY = valueY;
    }

    public Double getValueX() {
        return valueX;
    }

    public Double getValueY() {
        return valueY;
    }
}
