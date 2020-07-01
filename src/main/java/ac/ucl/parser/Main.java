package ac.ucl.parser;

import javascript.JavaScriptParser;
import javascript.JavaScriptParserBaseListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Main {

    private static String readFile(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return StringUtils.EMPTY;
        }
    }

    private static void printFunctionDeclaration(String path) {

        File file = new File(path);
        String script = readFile(file);
        JavaScriptParser parser = new Builder.Parser(script).build();

        // create file method
        // Method(FILE_PATH, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
        //       StringUtils.EMPTY, src, startLine, endLine, params, StringUtils.EMPTY);


        //Method(FILE_PATH, StringUtils.EMPTY, className, functionName,
        //       StringUtils.EMPTY, src, startLine, endLine, params, header.toString());

        ParseTreeWalker.DEFAULT.walk(new JavaScriptParserBaseListener() {

            @Override
            public void enterFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {
                String code = Tree.getSource(ctx);
                System.out.println("enterFunctionDeclaration" + "->>" + code + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }

            @Override
            public void enterFunctionExpression(JavaScriptParser.FunctionExpressionContext ctx) {
                String code = Tree.getSource(ctx);
                System.out.println("enterFunctionExpression" + "->>" + code + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());

            }


            @Override
            public void enterFunctionDecl(JavaScriptParser.FunctionDeclContext ctx) {
                String code = Tree.getSource(ctx);
                System.out.println("enterFunctionDecl" + "->>" + code + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }

            @Override
            public void enterAnoymousFunctionDecl(JavaScriptParser.AnoymousFunctionDeclContext ctx) {
                String code = Tree.getSource(ctx);
                System.out.println("enterAnoymousFunctionDecl" + "->>" + code + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }

            @Override
            public void enterArrowFunction(JavaScriptParser.ArrowFunctionContext ctx) {

                String code = Tree.getSource(ctx);
                System.out.println("enterArrowFunction" + "->>" + code + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }

            @Override
            public void enterMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) {

                String code = Tree.getSource(ctx);
                System.out.println("enterMethodDefinition" + "->>" + code + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }

            @Override
            public void enterObjectLiteral(JavaScriptParser.ObjectLiteralContext ctx) {

                String code = Tree.getSource(ctx);
                System.out.println("enterObjectLiteral" + "->>" + code + "->>" + ctx.getStart().getLine() + "->" + ctx.getStop().getLine());
            }

        }, parser.program());

    }


    public static void main(String[] args) throws Exception {

        String jsFileName = "Generator.js";
        File file = new File(Main.class.getClassLoader().getResource(jsFileName).getFile());
        printFunctionDeclaration(file.getPath());
    }
}