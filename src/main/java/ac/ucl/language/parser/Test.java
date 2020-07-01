package ac.ucl.language.parser;

import ac.ucl.util.SourceFileProcessor;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;

public class Test {

    public static void main(String[] args) {
        String jsFileName = "Generator.js";
        File file = new File(Main.class.getClassLoader().getResource(jsFileName).getFile());
        String script = SourceFileProcessor.getSourceCodeFromSourcePath(file.getPath());
        //JavaScriptParser parser = new Builder.Parser(script).build();

        // parser.program();
       /* ParseTreeWalker.DEFAULT.walk(new JavaScriptParserBaseListener() {

        }, parser.program());*/
    }

}
