package ac.ucl.javascript;

import ac.ucl.MethodParser;
import ac.ucl.language.model.Method;

import java.util.ArrayList;

public class JSMethodParser implements MethodParser {

    @Override
    public String getLicense() {
        return null;
    }


    /**
     * Uses ANTLR4 generated Parser and Lexer to extract methods from JavaScript source code file
     *
     * @return ArrayList of methods extracted from FILE_PATH attribute
     */
    @Override
    public ArrayList<Method> parseMethods() {

        ArrayList<Method> methods = new ArrayList<>();
        if (methods.size() > 0)
            return methods;

        return createFileMethod();

    }

    @Override
    public void configure(String filePath, String prefixToRemove, String mode, boolean isPrint) {

    }

    public ArrayList<Method> createFileMethod() {
        // transfer the file as a Method
        return new ArrayList<>();
    }
}
