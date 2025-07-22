package br.com.erikferreira.model;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private final List<List<Space>> spaces;

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus() {
        if(spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))){
            return GameStatusEnum.NON_STARTED;
        } else if(spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual()))){
            return GameStatusEnum.INCOMPLETED;
        } else {
            return GameStatusEnum.COMPLETED;
        }
    }

    public boolean hasErrors() {
        if(getStatus() == GameStatusEnum.NON_STARTED) {
            return false;
        }

        return spaces.stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && s.getActual().equals(s.getExpected()));
    }

    public boolean changeValue(final int coluna, final int linha, final Integer valor) {
        var space = spaces.get(coluna).get(linha);
        if(space.isFixed()){
            return false;
        }

        space.setActual(valor);
        return true;
    }

    public boolean clearValue(final int coluna, final int linha) {
        var space = spaces.get(coluna).get(linha);
        if(space.isFixed()){
            return false;
        }

        space.clear();
        return true;
    }

    public void resetar(){
        spaces.forEach(r -> r.forEach(Space::clear));
    }

    public boolean jogoFinalizado() {
        return !hasErrors() && getStatus() == GameStatusEnum.COMPLETED;
    }

}
