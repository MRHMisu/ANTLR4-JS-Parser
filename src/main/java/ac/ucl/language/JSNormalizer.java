package ac.ucl.language;


import ac.ucl.Normalizer;
import ac.ucl.NormalizerMode;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Normalizer for JavaScript source code.
 */
public class JSNormalizer implements Normalizer {

    private Map<String, Boolean> normalizeSettings;

    private static final String NORMALIZED_TOKEN_KEYWORD = "K";
    private static final String NORMALIZED_TOKEN_VALUE = "V";
    private static final String NORMALIZED_TOKEN_STRING = "S";
    private static final String NORMALIZED_TOKEN_OPERATOR = "O";
    private static final String NORMALIZED_TOKEN_NAME = "W"; // same symbol as Java clone search


    /**
     * Maps {@link javascript.JavaScriptLexer} to a normalised form.
     * Symbols with no corresponding normalised form have the empty string value. Normalization for these
     * symbols will always be false.
     */
    private static final Map<String, String> normalizedSymbols = ImmutableMap.<String, String>builder()
            .put("HASHBANGLINE", "")
            .put("MULTILINECOMMENT", "")
            .put("SINGLELINECOMMENT", "")
            .put("REGULAREXPRESSIONLITERAL", "")
            .put("OPENBRACKET", "")
            .put("CLOSEBRACKET", "")
            .put("OPENPAREN", "")
            .put("CLOSEPAREN", "")
            .put("OPENBRACE", "")
            .put("CLOSEBRACE", "")
            .put("SEMICOLON", "")
            .put("COMMA", "")
            .put("ASSIGN", "")
            .put("QUESTIONMARK", "")
            .put("COLON", "")
            .put("ELLIPSIS", "")
            .put("DOT", "")
            .put("PLUSPLUS", NORMALIZED_TOKEN_OPERATOR)
            .put("MINUSMINUS", NORMALIZED_TOKEN_OPERATOR)
            .put("PLUS", NORMALIZED_TOKEN_OPERATOR)
            .put("MINUS", NORMALIZED_TOKEN_OPERATOR)
            .put("BITNOT", NORMALIZED_TOKEN_OPERATOR)
            .put("NOT", NORMALIZED_TOKEN_OPERATOR)
            .put("MULTIPLY", NORMALIZED_TOKEN_OPERATOR)
            .put("DIVIDE", NORMALIZED_TOKEN_OPERATOR)
            .put("MODULUS", NORMALIZED_TOKEN_OPERATOR)
            .put("POWER", NORMALIZED_TOKEN_OPERATOR)
            .put("NULLCOALESCE", "")
            .put("HASHTAG", "")
            .put("RIGHTSHIFTARITHMETIC", NORMALIZED_TOKEN_OPERATOR)
            .put("LEFTSHIFTARITHMETIC", NORMALIZED_TOKEN_OPERATOR)
            .put("RIGHTSHIFTLOGICAL", NORMALIZED_TOKEN_OPERATOR)
            .put("LESSTHAN", NORMALIZED_TOKEN_OPERATOR)
            .put("MORETHAN", NORMALIZED_TOKEN_OPERATOR)
            .put("LESSTHANEQUALS", NORMALIZED_TOKEN_OPERATOR)
            .put("GREATERTHANEQUALS", NORMALIZED_TOKEN_OPERATOR)
            .put("EQUALS_", NORMALIZED_TOKEN_OPERATOR)
            .put("NOTEQUALS", NORMALIZED_TOKEN_OPERATOR)
            .put("IDENTITYEQUALS", NORMALIZED_TOKEN_OPERATOR)
            .put("IDENTITYNOTEQUALS", NORMALIZED_TOKEN_OPERATOR)
            .put("BITAND", NORMALIZED_TOKEN_OPERATOR)
            .put("BITXOR", NORMALIZED_TOKEN_OPERATOR)
            .put("BITOR", NORMALIZED_TOKEN_OPERATOR)
            .put("AND", NORMALIZED_TOKEN_OPERATOR)
            .put("OR", NORMALIZED_TOKEN_OPERATOR)
            .put("MULTIPLYASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("DIVIDEASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("MODULUSASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("PLUSASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("MINUSASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("LEFTSHIFTARITHMETICASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("RIGHTSHIFTARITHMETICASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("RIGHTSHIFTLOGICALASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("BITANDASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("BITXORASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("BITORASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("POWERASSIGN", NORMALIZED_TOKEN_OPERATOR)
            .put("ARROW", NORMALIZED_TOKEN_OPERATOR)
            .put("NULLLITERAL", NORMALIZED_TOKEN_VALUE)
            .put("BOOLEANLITERAL", NORMALIZED_TOKEN_VALUE)
            .put("DECIMALLITERAL", NORMALIZED_TOKEN_VALUE)
            .put("HEXINTEGERLITERAL", NORMALIZED_TOKEN_VALUE)
            .put("OCTALINTEGERLITERAL", NORMALIZED_TOKEN_VALUE)
            .put("OCTALINTEGERLITERAL2", NORMALIZED_TOKEN_VALUE)
            .put("BINARYINTEGERLITERAL", NORMALIZED_TOKEN_VALUE)
            .put("BIGHEXINTEGERLITERAL", NORMALIZED_TOKEN_VALUE)
            .put("BIGOCTALINTEGERLITERAL", NORMALIZED_TOKEN_VALUE)
            .put("BIGBINARYINTEGERLITERAL", NORMALIZED_TOKEN_VALUE)
            .put("BIGDECIMALINTEGERLITERAL", NORMALIZED_TOKEN_VALUE)
            .put("BREAK", NORMALIZED_TOKEN_KEYWORD)
            .put("DO", NORMALIZED_TOKEN_KEYWORD)
            .put("INSTANCEOF", NORMALIZED_TOKEN_KEYWORD)
            .put("TYPEOF", NORMALIZED_TOKEN_KEYWORD)
            .put("CASE", NORMALIZED_TOKEN_KEYWORD)
            .put("ELSE", NORMALIZED_TOKEN_KEYWORD)
            .put("NEW", NORMALIZED_TOKEN_KEYWORD)
            .put("VAR", NORMALIZED_TOKEN_KEYWORD)
            .put("CATCH", NORMALIZED_TOKEN_KEYWORD)
            .put("FINALLY", NORMALIZED_TOKEN_KEYWORD)
            .put("RETURN", NORMALIZED_TOKEN_KEYWORD)
            .put("VOID", NORMALIZED_TOKEN_KEYWORD)
            .put("CONTINUE", NORMALIZED_TOKEN_KEYWORD)
            .put("FOR", NORMALIZED_TOKEN_KEYWORD)
            .put("SWITCH", NORMALIZED_TOKEN_KEYWORD)
            .put("WHILE", NORMALIZED_TOKEN_KEYWORD)
            .put("DEBUGGER", NORMALIZED_TOKEN_KEYWORD)
            .put("FUNCTION", NORMALIZED_TOKEN_KEYWORD)
            .put("THIS", NORMALIZED_TOKEN_KEYWORD)
            .put("WITH", NORMALIZED_TOKEN_KEYWORD)
            .put("DEFAULT", NORMALIZED_TOKEN_KEYWORD)
            .put("IF", NORMALIZED_TOKEN_KEYWORD)
            .put("THROW", NORMALIZED_TOKEN_KEYWORD)
            .put("DELETE", NORMALIZED_TOKEN_KEYWORD)
            .put("IN", NORMALIZED_TOKEN_KEYWORD)
            .put("TRY", NORMALIZED_TOKEN_KEYWORD)
            .put("AS", NORMALIZED_TOKEN_KEYWORD)
            .put("FROM", NORMALIZED_TOKEN_KEYWORD)
            .put("CLASS", NORMALIZED_TOKEN_KEYWORD)
            .put("ENUM", NORMALIZED_TOKEN_KEYWORD)
            .put("EXTENDS", NORMALIZED_TOKEN_KEYWORD)
            .put("SUPER", NORMALIZED_TOKEN_KEYWORD)
            .put("CONST", NORMALIZED_TOKEN_KEYWORD)
            .put("EXPORT", NORMALIZED_TOKEN_KEYWORD)
            .put("IMPORT", NORMALIZED_TOKEN_KEYWORD)
            .put("ASYNC", NORMALIZED_TOKEN_KEYWORD)
            .put("AWAIT", NORMALIZED_TOKEN_KEYWORD)
            .put("IMPLEMENTS", NORMALIZED_TOKEN_KEYWORD)
            .put("STRICTLET", NORMALIZED_TOKEN_KEYWORD)
            .put("NONSTRICTLET", NORMALIZED_TOKEN_KEYWORD)
            .put("PRIVATE", NORMALIZED_TOKEN_KEYWORD)
            .put("PUBLIC", NORMALIZED_TOKEN_KEYWORD)
            .put("INTERFACE", NORMALIZED_TOKEN_KEYWORD)
            .put("PACKAGE", NORMALIZED_TOKEN_KEYWORD)
            .put("PROTECTED", NORMALIZED_TOKEN_KEYWORD)
            .put("STATIC", NORMALIZED_TOKEN_KEYWORD)
            .put("YIELD", NORMALIZED_TOKEN_KEYWORD)
            .put("IDENTIFIER", NORMALIZED_TOKEN_STRING)
            .put("STRINGLITERAL", NORMALIZED_TOKEN_STRING)
            .put("TEMPLATESTRINGLITERAL", "")
            .put("WHITESPACES", "")
            .put("LINETERMINATOR", "")
            .put("HTMLCOMMENT", "")
            .put("CDATACOMMENT", "")
            .put("UNEXPECTEDCHARACTER", "")
            .build();


    /**
     * Constructor that refer to parent Object class. Required for calling Class::newInstance.
     */
    public JSNormalizer() {
        super();
    }

    /**
     * Configures the normalization settings according to the input NormalizerMode.
     *
     * @param normalizerMode Configuration specifications for which category of symbols are to be normalised
     */
    @Override
    public void configure(NormalizerMode normalizerMode) {
        JSNormalizerMode jsNormalizerMode = (JSNormalizerMode) normalizerMode;
        normalizeSettings = ImmutableMap.<String, Boolean>builder()
                .put(NORMALIZED_TOKEN_KEYWORD, jsNormalizerMode.isKeywordToBeNormalised())
                .put(NORMALIZED_TOKEN_VALUE, jsNormalizerMode.isValueToBeNormalised())
                .put(NORMALIZED_TOKEN_STRING, jsNormalizerMode.isStringToBeNormalised())
                .put(NORMALIZED_TOKEN_OPERATOR, jsNormalizerMode.isOperatorToBeNormalised())
                .put(NORMALIZED_TOKEN_NAME, jsNormalizerMode.isNameToBeNormalised())
                .put("", false)
                .build();
    }

    /**
     * Normalizes a token depending on the normalization settings.
     *
     * @param token String text of the token
     * @param type  String type of the token
     * @return Normalised token
     */
    @Override
    public String normalizeAToken(String token, String type) {
        String normalizedSymbol = normalizedSymbols.get(type);
        return normalizeSettings.get(normalizedSymbol) ? normalizedSymbol : token;
    }

    @Override
    public ArrayList<String> noNormalizeAToken(String token) {
        return null;
    }
}
