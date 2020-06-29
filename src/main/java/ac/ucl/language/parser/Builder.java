package ac.ucl.language.parser;

import javascript.JavaScriptLexer;
import javascript.JavaScriptParser;
import org.antlr.v4.runtime.*;

public final class Builder {

    private static final DescriptiveBailErrorListener ERROR_LISTENER = new DescriptiveBailErrorListener();

    // No need to instantiate this class.
    private Builder() {
    }


    public static final class Parser {

        private JavaScriptParser parser;

        public Parser(String input) {
            this(new ANTLRInputStream(input));
        }

        public Parser(CharStream input) {
            JavaScriptLexer lexer = new JavaScriptLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(ERROR_LISTENER);
            this.parser = new JavaScriptParser(new CommonTokenStream(lexer));
            this.parser.removeErrorListeners();
            this.parser.addErrorListener(ERROR_LISTENER);
        }

        public Parser(JavaScriptLexer lexer) {
            this.parser = new JavaScriptParser(new CommonTokenStream(lexer));
            this.parser.removeErrorListeners();
            this.parser.addErrorListener(ERROR_LISTENER);
        }

        public Parser withErrorListener(ANTLRErrorListener listener) {
            this.parser.removeErrorListeners();
            this.parser.addErrorListener(listener);
            return this;
        }

        public JavaScriptParser build() {
            return this.parser;
        }
    }

}