package visitor;

import token.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    private Stack<Token> tokenStack;
    private List<Token> rpnTokens;

    public List<Token> parse(List<Token> tokens) {
        tokenStack = new Stack<>();
        rpnTokens = new ArrayList<>();
        tokens.forEach(token -> token.accept(this));
        List<Token> result = new ArrayList<>(rpnTokens);
        while (!tokenStack.empty()) {
            result.add(tokenStack.pop());
        }
        return result;
    }

    @Override
    public void visit(NumberToken token) {
        rpnTokens.add(token);
    }

    @Override
    public void visit(BraceToken token) {
        if (token.getTokenType().equals(TokenType.LEFT)) {
            tokenStack.push(token);
        } else {
            while (!tokenStack.empty() && !tokenStack.peek().getTokenType().equals(TokenType.LEFT)) {
                rpnTokens.add(tokenStack.pop());
            }
            if (tokenStack.empty()) {
                throw new IllegalStateException();
            }
            tokenStack.pop();
        }
    }

    @Override
    public void visit(OperationToken token) {
        while (!tokenStack.empty() &&
                token.getTokenType().getPriority() <= tokenStack.peek().getTokenType().getPriority()) {
            rpnTokens.add(tokenStack.pop());
        }
        tokenStack.push(token);
    }
}
