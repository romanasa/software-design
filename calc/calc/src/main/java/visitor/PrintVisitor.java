package visitor;

import token.BraceToken;
import token.NumberToken;
import token.OperationToken;
import token.Token;

import java.util.List;

public class PrintVisitor implements TokenVisitor {
    StringBuilder result;

    private void addToken(Token token) {
        result.append(token).append(", ");
    }

    public String print(List<Token> tokens) {
        result = new StringBuilder();
        tokens.forEach(token -> token.accept(this));
        return result.toString();
    }

    @Override
    public void visit(NumberToken token) {
        addToken(token);
    }

    @Override
    public void visit(BraceToken token) {
        addToken(token);
    }

    @Override
    public void visit(OperationToken token) {
        addToken(token);
    }
}
