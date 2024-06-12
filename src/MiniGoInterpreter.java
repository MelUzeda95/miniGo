import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MiniGoInterpreter {
    private Map<String, Variable> variables;
    private ExpressionParser expressionParser;

    public MiniGoInterpreter() {
        variables = new HashMap<>();
        expressionParser = new ExpressionParser(variables);
    }

    public void interpret(String code) {
        String[] lines = code.split("\n");
        for (String line : lines) {
            processLine(line.trim());
        }
    }

    private void processLine(String line) {
        if (line.startsWith("var ")) {
            declareVariables(line);
        } else if (line.startsWith("print")) {
            printStatement(line);
        } else if (line.contains("=")) {
            assignVariable(line);
        } else if (!line.trim().isEmpty()) {
            evaluateExpression(line);
        }
    }

    private void declareVariables(String line) {
        line = line.substring(4).trim(); // Remove "var "
        String[] declarations = line.split(",");
        for (String declaration : declarations) {
            String[] parts = declaration.split("=");
            String varName = parts[0].trim();
            if (parts.length > 1) {
                int value = expressionParser.evaluate(parts[1].trim());
                variables.put(varName, new Variable(varName, value));
            } else {
                variables.put(varName, new Variable(varName, 0));
            }
        }
    }

    private void assignVariable(String line) {
        String[] parts = line.split("=");
        String varName = parts[0].trim();
        int value = expressionParser.evaluate(parts[1].trim());
        Variable var = variables.get(varName);
        if (var == null) {
            throw new IllegalArgumentException("Variable no declarada: " + varName);
        }
        var.setValue(value);
    }

    private void evaluateExpression(String expression) {
        int value = expressionParser.evaluate(expression);
        System.out.println("Resultado: " + value);
    }

    private void printStatement(String line) {
        String text = line.substring(6).trim(); // Remove "print"
        System.out.println(text);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MiniGoInterpreter interpreter = new MiniGoInterpreter();
        System.out.println("Escriba el código (termine con una línea vacía):");

        StringBuilder code = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            if (!line.trim().isEmpty() && !line.startsWith("Escriba")) {
                code.append(line).append("\n");
            }
        }

        interpreter.interpret(code.toString());
    }
}
