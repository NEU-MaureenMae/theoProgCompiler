import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalSyntaxChecker {
    static String[] array, arrayOrig;
    static String sourceLanguage, output = "", concatenate = "";
    static String numbers = "1234567890";
    static String regex = "\\d+";
    static Pattern pattern;
    static Matcher matcher;

    public LexicalSyntaxChecker(String content) {
        sourceLanguage = content;
        output = "";
        split();
        assessValue();
        semanticCheck();
        semanticCheck();
    }

    public static void split() {
        arrayOrig = sourceLanguage.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        if (arrayOrig.length < 3) {
            array = new String[2];
            for (int i = 0; i < arrayOrig.length; i++) {
                array[i] = arrayOrig[i];
            }
        }
        if (arrayOrig.length > 2) {
            array = new String[4];
            for (int i = 0; i < arrayOrig.length; i++) {
                if (i > 2) {
                    concatenate += arrayOrig[i];
                } else {
                    array[i] = arrayOrig[i];
                }
            }
            array[3] = concatenate;
        }
    }

    static void assessValue() {

        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(numbers);
        int i=0;
        while (i<array.length+1) {
            if (array[i].equalsIgnoreCase("int") ||
                    array[i].equalsIgnoreCase("double") ||
                    array[i].equalsIgnoreCase("String") ||
                    array[i].equalsIgnoreCase("char"))
            {
                output += "<data_type>";
                i++;
            }
            if (!array[i].contains("\"") || !array[i].contains("\'") ||
             !array[i].contains(";") || !array[i].contains(".") ||
             !(array[i].matches("^\\d+$"))) {
                output += "<identifier>";
                i++;
            }

            if (array.length < 3) {
                if (array[i].contains(";")) {
                    output += "<delimiter>";
                    i++;
                }
            }
            if (array.length > 3) {
                if (array[i] != null && array[i].contains("=")) {
                    output += "<assignment_operator>";
                    i++;
                }
                if (array[i] != null && (array[i].contains(";") ||
                        (array[i].matches("^\\d+$")) || (array[i].contains("."))
                        || array[i].contains("\"") || array[i].contains("\'"))) {
                    output += "<value>";
                    i++;
                }
                if (array[i-1].contains(";")) {
                    output += "<delimiter>";
                    i++;
                }
            }
        }
    }

    static String lexicalPrint(){
        return output;
    }

    static String semanticCheck() {
        if (output.contains("<data_type><identifier><assignment_operator><value><delimiter>")) {
            return "Syntax Correct!";
        }
        if (output.contains("<data_type><identifier><delimiter>")) {
            return "Syntax Correct!";
        } else {
            return "Syntax Incorrect!";
        }
    }
}