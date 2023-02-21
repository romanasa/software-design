package state;

import token.Token;
import token.Tokenizer;

public class ErrorState implements State {

    private final String errorMessage;

    public ErrorState(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public Token getToken(Tokenizer tokenizer) {
        return null;
    }

    @Override
    public void nextState(Tokenizer tokenizer) {

    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
