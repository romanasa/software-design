package state;

import token.NumberToken;
import token.Token;
import token.Tokenizer;

public class NumberState implements State {
    @Override
    public Token getToken(Tokenizer tokenizer) {
        StringBuilder number = new StringBuilder();
        while (tokenizer.hasNext() && tokenizer.isDigit()) {
            number.append(tokenizer.readNext());
        }
        return new NumberToken(Integer.parseInt(number.toString()));
    }

    @Override
    public void nextState(Tokenizer tokenizer) {
        if (!tokenizer.hasNext()) {
            tokenizer.setState(new EndState());
        } else if (tokenizer.isOperationOrBrace()) {
            tokenizer.setState(new StartState());
        } else {
            tokenizer.setState(new ErrorState("Unexpected character: " + tokenizer.getNext()));
        }
    }
}
