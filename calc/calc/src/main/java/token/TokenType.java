package token;

public enum TokenType {
    LEFT,
    RIGHT,
    NUMBER,
    PLUS,
    MINUS,
    MUL,
    DIV;


    public int getPriority() {
        if (this.equals(LEFT) || this.equals(RIGHT)) {
            return 0;
        }
        if (this.equals(PLUS) || this.equals(MINUS)) {
            return 1;
        }
        if (this.equals(MUL) || this.equals(DIV)) {
            return 2;
        }
        return 3;
    }
}
