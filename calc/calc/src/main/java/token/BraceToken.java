package token;

import visitor.TokenVisitor;

public class BraceToken implements Token {
    private final TokenType braceType;

    public BraceToken(TokenType braceType) {
        this.braceType = braceType;
    }

    @Override
    public void accept(TokenVisitor tokenVisitor) {
        tokenVisitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return braceType;
    }

    @Override
    public String toString() {
        return "BraceToken{" +
                "braceType=" + braceType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BraceToken that = (BraceToken) o;
        return braceType == that.braceType;
    }
}
