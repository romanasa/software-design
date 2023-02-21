package token;

import state.EndState;
import state.ErrorState;
import state.StartState;
import state.State;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private State state;
    private String input;
    private int pos;

    public void setState(State state) {
        this.state = state;
    }

    public List<Token> parseInput(String input) throws ParseException {
        this.input = input;
        pos = 0;

        List<Token> result = new ArrayList<>();

        state = new StartState();
        state.nextState(this);

        while (!(state instanceof EndState || state instanceof ErrorState)) {
            result.add(state.getToken(this));
            skipSpaces();
            state.nextState(this);
        }
        if (state instanceof ErrorState) {
            throw new ParseException(((ErrorState) state).getErrorMessage(), pos);
        }
        return result;
    }

    public boolean hasNext() {
        return pos < this.input.length();
    }

    public char getNext() {
        return this.input.charAt(pos);
    }

    public boolean isSpace() {
        return Character.isWhitespace(getNext());
    }

    public void skipSpaces() {
        while (hasNext() && isSpace()) {
            readNext();
        }
    }

    public char readNext() {
        char result = getNext();
        pos++;
        return result;
    }

    public boolean isDigit() {
        return Character.isDigit(getNext());
    }

    public boolean isOperationOrBrace() {
        return "+-*/()".indexOf(getNext()) >= 0;
    }
}
