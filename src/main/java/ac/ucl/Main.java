package ac.ucl;

import ac.ucl.parser.Builder;
import ac.ucl.util.SourceFileProcessor;
import javascript.JavaScriptParser;
import javascript.JavaScriptParserBaseListener;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;

public class Main {

    private static void printRegexLiterals(String path) {

        String script = SourceFileProcessor.getSourceCodeFromSourcePath(path);

        JavaScriptParser parser = new Builder.Parser(script).build();

        ParseTreeWalker.DEFAULT.walk(new JavaScriptParserBaseListener() {
            @Override
            public void enterLiteral(@NotNull JavaScriptParser.LiteralContext ctx) {
                if (ctx.RegularExpressionLiteral() != null) {
                    System.out.println("regex: " + ctx.RegularExpressionLiteral().getText());
                }
            }
        }, parser.program());
    }

    private static void printFunctionDeclaration(String path) {

        String script = SourceFileProcessor.getSourceCodeFromSourcePath(path);
        JavaScriptParser parser = new Builder.Parser(script).build();
        ParseTreeWalker.DEFAULT.walk(new JavaScriptParserBaseListener() {
            //ctx.identifier().getText()-> function name
            @Override
            public void enterFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {
                if (ctx.identifier() != null) {
                    System.out.println("Function: " + ctx.identifier().getText()+"->"+ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
                }
            }
        }, parser.program());
    }


    public static void main(String[] args) throws Exception {
        File file = new File( Main.class.getClassLoader().getResource("Demo.js").getFile());
        printFunctionDeclaration(file.getPath());
    }
}