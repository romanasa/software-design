import org.junit.Test;
import token.*;
import visitor.CalcVisitor;
import visitor.ParserVisitor;

import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CalcTest {

    @Test
    public void calcTest() throws ParseException {
        String input = "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2";

        Tokenizer tokenizer = new Tokenizer();
        CalcVisitor calcVisitor = new CalcVisitor();
        ParserVisitor parserVisitor = new ParserVisitor();

        List<Token> tokens = tokenizer.parseInput(input);
        List<Token> expected = List.of(
                new BraceToken(TokenType.LEFT),
                new NumberToken(23),
                new OperationToken(TokenType.PLUS),
                new NumberToken(10),
                new BraceToken(TokenType.RIGHT),
                new OperationToken(TokenType.MUL),
                new NumberToken(5),
                new OperationToken(TokenType.MINUS),
                new NumberToken(3),
                new OperationToken(TokenType.MUL),
                new BraceToken(TokenType.LEFT),
                new NumberToken(32),
                new OperationToken(TokenType.PLUS),
                new NumberToken(5),
                new BraceToken(TokenType.RIGHT),
                new OperationToken(TokenType.MUL),
                new BraceToken(TokenType.LEFT),
                new NumberToken(10),
                new OperationToken(TokenType.MINUS),
                new NumberToken(4),
                new OperationToken(TokenType.MUL),
                new NumberToken(5),
                new BraceToken(TokenType.RIGHT),
                new OperationToken(TokenType.PLUS),
                new NumberToken(8),
                new OperationToken(TokenType.DIV),
                new NumberToken(2)
        );
        assertThat(tokens.size()).isEqualTo(expected.size());
        for (int i = 0; i < tokens.size(); i++) {
            assertThat(tokens.get(i)).isEqualTo(expected.get(i));
        }

        List<Token> rpnTokens = parserVisitor.parse(tokens);
        int value = calcVisitor.calc(rpnTokens);
        assertThat(value).isEqualTo(1279);
    }
}
