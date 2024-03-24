package com.marlonnunes.carrental.model.enums;

public enum Color {
    BLACK("Preto"),
    GRAY("Cinza"),
    WHITE("Branco"),
    RED("Vermelho"),
    BLUE("Azul"),
    GREEN("Verde");

    private String label;
    private Color(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
