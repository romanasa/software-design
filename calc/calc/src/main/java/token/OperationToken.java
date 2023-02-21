package token;

import visitor.TokenVisitor;

public class OperationToken implements Token {
    private final TokenType operationToken;

    public OperationToken(TokenType operationToken) {
        this.operationToken = operationToken;
    }

    @Override
    public void accept(TokenVisitor tokenVisitor) {
        tokenVisitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return operationToken;
    }

    public int evaluate(int first, int second) {
        return switch (operationToken) {
            case DIV -> first / second;
            case MUL -> first * second;
            case PLUS -> first + second;
            case MINUS -> first - second;
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public String toString() {
        return "OperationToken{" +
                "operationToken=" + operationToken +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationToken that = (OperationToken) o;
        return operationToken == that.operationToken;
    }
}
