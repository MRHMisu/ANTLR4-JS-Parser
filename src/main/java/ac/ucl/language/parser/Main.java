package ac.ucl.language.parser;

import ac.ucl.util.SourceFileProcessor;
import javascript.JavaScriptParser;
import javascript.JavaScriptParserBaseListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;

public class Main {

    private static void printFunctionDeclaration(String path) {

        String script = SourceFileProcessor.getSourceCodeFromSourcePath(path);
        JavaScriptParser parser = new Builder.Parser(script).build();

        ParseTreeWalker.DEFAULT.walk(new JavaScriptParserBaseListener() {
            //ctx.identifier().getText()-> function name

            // function declaration (statement start with the keyword function)
            @Override
            public void enterFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {
                if (ctx.identifier() != null) {
                    //System.out.println("enterFunctionDeclaration: " + ctx.getText() + "->" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
                    String code = Tree.getText(ctx);
                    System.out.println(code);
                }
            }
/*
            @Override
            public void enterFunctionExpression(JavaScriptParser.FunctionExpressionContext ctx) {
                if (ctx.anoymousFunction() != null) {
                    System.out.println("enterFunctionExpression: " + ctx.anoymousFunction().getText() + "->" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
                }
            }


            @Override
            public void enterFunctionDecl(JavaScriptParser.FunctionDeclContext ctx) {
                if (ctx.functionDeclaration() != null) {
                    System.out.println("enterFunctionDecl: " + ctx.functionDeclaration().getText() + "->" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
                }
            }

            @Override
            public void enterAnoymousFunctionDecl(JavaScriptParser.AnoymousFunctionDeclContext ctx) {
                System.out.println("enterAnoymousFunctionDecl: " + ctx.Function().getText() + "->" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());

            }

            @Override
            public void enterArrowFunction(JavaScriptParser.ArrowFunctionContext ctx) {

                System.out.println("enterAnoymousFunctionDecl: " + ctx.getText() + "->" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());

            }

            @Override
            public void enterMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) {

                System.out.println("enterMethodDefinition: " + ctx.getText() + "->" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());

            }*/

        }, parser.program());


    }


    public static void main(String[] args) throws Exception {

        String jsFileName = "Generator.js";
        File file = new File(Main.class.getClassLoader().getResource(jsFileName).getFile());
        printFunctionDeclaration(file.getPath());
    }
}