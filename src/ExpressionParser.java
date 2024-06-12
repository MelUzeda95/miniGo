import java.util.Map;

public class ExpressionParser {

    private Map<String, Variable> variables;

    public ExpressionParser(Map<String, Variable> variables) {
        this.variables = variables;
    }

    public int evaluate(String expression) {
        String[] tokens = expression.split(" ");
        if (tokens.length == 1) {
            return getValue(tokens[0]);
        } else {
            int left = getValue(tokens[0]);
            String operator = tokens[1];
            int right = getValue(tokens[2]);
            switch (operator) {
                case "+":
                    return left + right;
                case "-":
                    return left - right;
                case "*":
                    return left * right;
                case "/":
                    return left / right;
                default:
                    throw new IllegalArgumentException("Operador desconocido: " + operator);
            }
        }
    }

    private int getValue(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            Variable var = variables.get(token);
            if (var == null) {
                throw new IllegalArgumentException("Variable no declarada: " + token);
            }
            return var.getValue();
        }
    }
}
