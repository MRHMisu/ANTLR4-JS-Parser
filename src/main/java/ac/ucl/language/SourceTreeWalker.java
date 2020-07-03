package ac.ucl.language;

import javascript.JavaScriptParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public final class SourceTreeWalker {
    private static void printFunctionDeclaration(File sourceFile, String jsSourceCode) {

        JavaScriptParser parser = new Builder.Parser(jsSourceCode).build();
        List<Method> methods = new ArrayList<>();
        try {
            ParseTree parseTree = parser.program();
            JSParseTreeListener jsParseTreeListener = new JSParseTreeListener(sourceFile.getPath(), parseTree);
            ParseTreeWalker.DEFAULT.walk(jsParseTreeListener, parseTree);
            //methods.add(jsParseTreeListener.getFileBlockMethod());
            methods.addAll(jsParseTreeListener.getJSMethods());
        } catch (Exception e) {
            System.err.println("Source file->" + sourceFile.getPath() + "-cannot be parsed ");
            System.out.println(e.getMessage());
        }
        System.out.println("---Completed---");

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

        String jsFileName = "Demo.js";
        //String jsFileName = "All-Combination-Of-JS-Functions.js";
        File file = new File(SourceTreeWalker.class.getClassLoader().getResource(jsFileName).getFile());
        String sourceCode = readFile(file);
        printFunctionDeclaration(file, sourceCode);
    }
}
