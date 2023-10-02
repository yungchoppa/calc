import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static Map<String, Integer> romanToArabic = new HashMap<String, Integer>();

    static String[] romanNumerals = {
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
            "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
            "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX",
            "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
            "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L",
            "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
            "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
            "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
            "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
            "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"
    };

    public static void main(String[] args) throws Exception{

        Integer value = 1;
        for(String num: romanNumerals){ romanToArabic.put(num, value++); }

        Scanner console = new Scanner(System.in);
        String input = console.nextLine().replaceAll("\\s", "");
        String oneToTenRomNum = String.join("|", Arrays.copyOfRange(romanNumerals, 0, 10));
        String regex = "^(" + oneToTenRomNum + "|[1-9]|10)([+\\-*/])(" + oneToTenRomNum + "|[1-9]|10)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        String firstNumber = null;
        String operator = null;
        String secondNumber = null;

        if(matcher.find()){
            firstNumber = matcher.group(1);
            operator = matcher.group(2);
            secondNumber = matcher.group(3);
            if(oneToTenRomNum.contains(firstNumber) && oneToTenRomNum.contains(secondNumber)) {
                System.out.println(roman(firstNumber, operator, secondNumber));
            } else if (!oneToTenRomNum.contains(firstNumber) && !oneToTenRomNum.contains(secondNumber)) {
                System.out.println(arabic(firstNumber, operator, secondNumber));
            }
            else {
                throw new Exception("Разный тип цифр");
            }
        }
        else{
            throw new Exception("Неверное выражение или число");
        }
    }

    public static String arabic(String firstNumber, String operator, String secondNumber){
        int x = Integer.parseInt(firstNumber);
        int y = Integer.parseInt(secondNumber);
        return String.valueOf(calc(operator, x, y));
    }
    public static String roman(String firstNumber, String operator, String secondNumber) throws Exception{
        int x = romanToArabic.get(firstNumber);
        int y = romanToArabic.get(secondNumber);
        int result = calc(operator, x, y);
        if(result < 1) throw new Exception("Римские цифры только положительные");
        String key = null;
        for (Map.Entry<String, Integer> entry : romanToArabic.entrySet()) {
            if(entry.getValue() == result) {
                key = entry.getKey();
            }
        }
        return key;
    }

    public static int calc(String operator, int x, int y){
        int result = 0;
        switch (operator){
            case "+": result = x + y; break;
            case "-": result = x - y; break;
            case "*": result = x * y; break;
            case "/": result = x / y; break;
        }
        return result;
    }
}