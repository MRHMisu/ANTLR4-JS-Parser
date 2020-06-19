package ac.ucl.language.parser;

import ac.ucl.util.SourceFileProcessor;
import javascript.JavaScriptParser;
import javascript.JavaScriptParserBaseListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;

public class Main {

    private static void printRegexLiterals(String path) {

        String script = SourceFileProcessor.getSourceCodeFromSourcePath(path);

        JavaScriptParser parser = new Builder.Parser(script).build();


    }

    private static void printFunctionDeclaration(String path) {

        String script = SourceFileProcessor.getSourceCodeFromSourcePath(path);
        JavaScriptParser parser = new Builder.Parser(script).build();
        ParseTreeWalker.DEFAULT.walk(new JavaScriptParserBaseListener() {
            //ctx.identifier().getText()-> function name

            // function declaration (statement start with the keyword function)
            @Override
            public void enterFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {
                if (ctx.identifier() != null) {
                    System.out.println("Function Declaration: " + ctx.identifier().getText() + "->" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
                }
            }

            @Override
            public void enterFunctionExpression(JavaScriptParser.FunctionExpressionContext ctx) {
                if (ctx.anoymousFunction() != null) {
                    System.out.println("Function Expression: " + ctx.anoymousFunction().getText() + "->" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
                }
            }



        }, parser.program());


    }


    public static void main(String[] args) throws Exception {
        File file = new File(Main.class.getClassLoader().getResource("Demo.js").getFile());
        printFunctionDeclaration(file.getPath());
    }
}