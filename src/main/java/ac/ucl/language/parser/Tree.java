package ac.ucl.language.parser;

import ac.ucl.language.model.Parameter;
import javascript.JavaScriptParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Tree {

    public static String getText(ParseTree tree) {
        List<TerminalNodeImpl> terminalNodes = walk(tree);
        String code = getText(terminalNodes);
        return code;
    }

    public static String getText(List<TerminalNodeImpl> terminalNodes) {
        StringBuilder builder = new StringBuilder();
        for (TerminalNodeImpl tm : terminalNodes) {
            builder.append(tm.getText() + " ");
        }
        return builder.toString();
    }

    public static List<TerminalNodeImpl> walk(ParseTree tree) {
        List<TerminalNodeImpl> terminalNodes = new ArrayList<>();
        List<ParseTree> firstStack = new ArrayList<ParseTree>();
        firstStack.add(tree);

        List<List<ParseTree>> childListStack = new ArrayList<List<ParseTree>>();
        childListStack.add(firstStack);
        String functionName = getFunctionIdentifier(tree);
        String className = getClassName(tree);
        while (!childListStack.isEmpty()) {
            List<ParseTree> childStack = childListStack.get(childListStack.size() - 1);
            if (childStack.isEmpty()) {
                childListStack.remove(childListStack.size() - 1);
            } else {
                tree = childStack.remove(0);
                if (tree instanceof TerminalNodeImpl) {
                    terminalNodes.add((TerminalNodeImpl) tree);
                }

                if (tree.getChildCount() > 0) {
                    List<ParseTree> children = new ArrayList<ParseTree>();
                    for (int i = 0; i < tree.getChildCount(); i++) {
                        children.add(tree.getChild(i));
                    }
                    childListStack.add(children);
                }
            }
        }
        return terminalNodes;
    }


    private List<Parameter> getParameters(JavaScriptParser.FormalParameterListContext parameters) {
        List<Parameter> params = new ArrayList<>();
        // process parameters
        return params;
    }

    private static String getClassName(ParseTree tree) {
        String className = "";
        if (tree instanceof JavaScriptParser.MethodDefinitionContext) {
            ParseTree parentClassContext = tree.getParent();
            while (!(parentClassContext instanceof JavaScriptParser.ClassDeclarationContext)) {
                parentClassContext = parentClassContext.getParent();
            }
            ParseTree identifier = parentClassContext.getChild(1);
            className = identifier.getText();
        }
        return className;
    }

    private static String getFunctionIdentifier(ParseTree tree) {
        String functionName = "";
        if (tree instanceof JavaScriptParser.FunctionDeclarationContext) {
            if (tree.getChildCount() > 0) {
                for (int i = 0; i < tree.getChildCount(); i++) {
                    ParseTree child = tree.getChild(i);
                    if (child instanceof JavaScriptParser.IdentifierContext) {
                        functionName = child.getText();
                        break;
                    }
                }
            }
        } else if (tree instanceof JavaScriptParser.FunctionExpressionContext) {
            functionName = "Function Expression";
        } else if (tree instanceof JavaScriptParser.ArrowFunctionContext) {
            functionName = "Arrow Function";
        } else if (tree instanceof JavaScriptParser.AnoymousFunctionContext) {
            functionName = "Anonymous Function";
        } else if (tree instanceof JavaScriptParser.MethodDefinitionContext) {
            while (!(tree instanceof JavaScriptParser.IdentifierContext)) {
                if (tree.getChildCount() > 0)
                    tree = tree.getChild(0);
            }
            functionName = tree.getText();
        }
        return functionName;
    }

    private String getHeaders() {

        // process function headers
        return "";
    }

    private Map<String, Integer> getStartEnd() {
        Map<String, Integer> startEnd = new HashMap<>();
        return startEnd;

    }
}
