package state;

import token.Token;
import token.Tokenizer;

public interface State {

    Token getToken(Tokenizer tokenizer);

    void nextState(Tokenizer tokenizer);
}
