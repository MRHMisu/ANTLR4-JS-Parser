package ac.ucl.language.parser;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.ArrayList;
import java.util.List;

public final class Tree {
    private String FILE_PATH;
    private String MODE;
    private static final String WHITESPACE = " ";
    private static final String NEWLINE = "\n";
    private static final String TAB = "\t";

    public static String getText(ParseTree tree) {
        StringBuilder builder = new StringBuilder();

        List<TerminalNodeImpl> terminalNodes = walk(tree);
        String code = getText(terminalNodes);
        return code;
    }


    public static String getText(List<TerminalNodeImpl> terminalNodes) {
        StringBuilder builder = new StringBuilder();
        for (TerminalNodeImpl tm : terminalNodes) {
            Token token = tm.getSymbol();
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
}
