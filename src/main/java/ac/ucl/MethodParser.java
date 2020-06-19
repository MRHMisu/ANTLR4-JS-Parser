package ac.ucl;

import ac.ucl.language.model.Method;

import java.util.ArrayList;

public interface MethodParser {
    public String getLicense();

    public ArrayList<Method> parseMethods();

    public void configure(String filePath, String prefixToRemove, String mode, boolean isPrint);
}
