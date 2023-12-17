public class SemanticAnalysis {
    static String expression;
    static String[] expressionArray;
    static int count = 1;
    static String answer;

    public SemanticAnalysis(String content) {

        expression = content;
        // Split the expression by space while considering quoted strings
        expressionArray = expression.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        start();
        semanticCheck();

    }

    public static String semanticCheck() {
        return answer;
    }

    public static void start() {
        if (expressionArray.length == 2) {
            checkNoValue();
        }
        if (expressionArray.length > 2) {
            if (expressionArray.length > 4) {
                trim();
            }
            checkWithValue();
        }
        count++;
    }

    private static void trim() {
        StringBuilder indexFour = new StringBuilder();
        for (int i = 3; i < expressionArray.length; i++) {
            indexFour.append(expressionArray[i]);
        }
        expressionArray[3] = indexFour.toString();
    }

    private static void checkWithValue() {
        if (checkIndexOne() &&
                checkIndexTwo() &&
                checkIndexThree() &&
                checkIndexFour()) {
            answer = "Semantically Correct!";
        } else {
            answer = "Semantically Incorrect!";
        }
    }

    private static void checkNoValue() {
        if (checkIndexOne() && expressionArray[1].endsWith(";")) {
            answer = "Semantically Correct!";
        } else {
            answer = "Semantically Incorrect!";
        }
    }

    private static boolean checkIndexOne() {
        return (expressionArray[0].equals("int") ||
                expressionArray[0].equals("String") ||
                expressionArray[0].equals("Double") ||
                expressionArray[0].equals("char")) &&
                checkIndexTwo();
    }

    private static boolean checkIndexTwo() {
        return (!expressionArray[1].matches("^[0-9\\W].*"));
    }

    private static boolean checkIndexThree() {
        return (expressionArray[2].equals("="));
    }

    private static boolean checkIndexFour() {
        return ((expressionArray[0].equals("int") && expressionArray[3].matches(".*[0-9].*") &&
                !expressionArray[3].matches("^\\d+$")) && expressionArray[3].endsWith(";")) ||


                (expressionArray[0].equals("String") &&
                        expressionArray[3].contains("\"") && expressionArray[3].endsWith(";")) ||

                (expressionArray[0].equals("char") &&
                        expressionArray[3].contains("\'") && expressionArray[3].endsWith(";") ||


                (expressionArray[0].equals("Double") &&
                        expressionArray[3].matches(".*[0-9].*") && expressionArray[3].contains(".")
                        && expressionArray[3].endsWith(";")));
    }
}
