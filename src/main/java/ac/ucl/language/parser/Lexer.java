package ac.ucl.language.parser;

import javascript.JavaScriptLexer;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;

public final class Lexer {

    private JavaScriptLexer lexer;
    private static final DescriptiveBailErrorListener ERROR_LISTENER = new DescriptiveBailErrorListener();

    public Lexer(String input) {
        this(new ANTLRInputStream(input));
    }

    public Lexer(CharStream input) {
        this.lexer = new JavaScriptLexer(input);
        this.lexer.removeErrorListeners();
        this.lexer.addErrorListener(ERROR_LISTENER);
    }

    public Lexer withStrictMode(boolean strictMode) {
        this.lexer.setUseStrictDefault(strictMode);
        return this;
    }

    public Lexer withErrorListener(ANTLRErrorListener listener) {
        this.lexer.removeErrorListeners();
        this.lexer.addErrorListener(listener);
        return this;
    }

    public JavaScriptLexer build() {
        return this.lexer;
    }
}
