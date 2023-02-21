package state;

import token.Token;
import token.Tokenizer;

public class EndState implements State {
    @Override
    public Token getToken(Tokenizer tokenizer) {
        return null;
    }

    @Override
    public void nextState(Tokenizer tokenizer) {

    }
}
