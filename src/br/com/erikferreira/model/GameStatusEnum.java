package br.com.erikferreira.model;

public enum GameStatusEnum {
    NON_STARTED("Nao inicializado"),
    INCOMPLETED("Incompleto"),
    COMPLETED("Completo");

    private String label;

    GameStatusEnum(final String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
