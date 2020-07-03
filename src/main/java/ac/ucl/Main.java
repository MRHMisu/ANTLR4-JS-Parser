package ac.ucl;

import ac.ucl.language.Builder;

import javascript.JavaScriptParser;
import javascript.JavaScriptParserBaseListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Main {

    static int count = 1;

    private static void printFunctionDeclaration(String path) {

        String script = readFile(new File(path));
        JavaScriptParser parser = new Builder.Parser(script).build();

        // create file method
        // Method(FILE_PATH, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
        //       StringUtils.EMPTY, src, startLine, endLine, params, StringUtils.EMPTY);


        //Method(FILE_PATH, StringUtils.EMPTY, className, functionName,
        //       StringUtils.EMPTY, src, startLine, endLine, params, header.toString());


        ParseTreeWalker.DEFAULT.walk(new JavaScriptParserBaseListener() {

            @Override
            public void enterFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {

                System.out.println(count++ + "->" + "enterFunctionDeclaration" + "->>" + ctx.getText() + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }

            @Override
            public void enterFunctionExpression(JavaScriptParser.FunctionExpressionContext ctx) {

                System.out.println(count++ + "->" + "enterFunctionExpression" + "->>" + ctx.getText() + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());

            }

/*
            @Override
            public void enterFunctionDecl(JavaScriptParser.FunctionDeclContext ctx) {

                System.out.println("enterFunctionDecl" + "->>" + ctx.getText() + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }*/

            /*@Override
            public void enterAnoymousFunctionDecl(JavaScriptParser.AnoymousFunctionDeclContext ctx) {

                System.out.println("enterAnoymousFunctionDecl" + "->>" + ctx.getText() + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }*/

           /* @Override
            public void enterArrowFunction(JavaScriptParser.ArrowFunctionContext ctx) {


                System.out.println("enterArrowFunction" + "->>" + ctx.getText() + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }*/

            @Override
            public void enterMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) {


                System.out.println(count++ + "->" + "enterMethodDefinition" + "->>" + ctx.getText() + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }

            /*@Override
            public void enterObjectLiteral(JavaScriptParser.ObjectLiteralContext ctx) {


                System.out.println("enterObjectLiteral" + "->>" + ctx.getText() + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }*/

        }, parser.program());

    }

    private static String readFile(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return StringUtils.EMPTY;
        }
    }

    public static void main(String[] args) throws Exception {

        String jsFileName = "All-Combination-Of-JS-Functions.js";
        File file = new File(Main.class.getClassLoader().getResource(jsFileName).getFile());
        printFunctionDeclaration(file.getPath());
    }
}