package edu.kit.mensameet.client.model;

public class MensaData {
    public MensaData(Line[] lines) {
        this.lines = lines;
    }

    public Line[] getLines() {
        return lines;
    }

    public void setLines(Line[] lines) {
        this.lines = lines;
    }

    private Line[] lines;
}
