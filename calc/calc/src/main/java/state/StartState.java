package state;

import token.*;

public class StartState implements State {
    @Override
    public Token getToken(Tokenizer tokenizer) {
        char c = tokenizer.readNext();
        return switch (c) {
            case '(' -> new BraceToken(TokenType.LEFT);
            case ')' -> new BraceToken(TokenType.RIGHT);
            case '+' -> new OperationToken(TokenType.PLUS);
            case '-' -> new OperationToken(TokenType.MINUS);
            case '*' -> new OperationToken(TokenType.MUL);
            case '/' -> new OperationToken(TokenType.DIV);
            default -> null;
        };
    }

    @Override
    public void nextState(Tokenizer tokenizer) {
        if (!tokenizer.hasNext()) {
            tokenizer.setState(new EndState());
        } else if (tokenizer.isDigit()) {
            tokenizer.setState(new NumberState());
        } else if (tokenizer.isOperationOrBrace()) {
            tokenizer.setState(new StartState());
        } else {
            tokenizer.setState(new ErrorState("Unexpected character: " + tokenizer.getNext()));
        }
    }
}
