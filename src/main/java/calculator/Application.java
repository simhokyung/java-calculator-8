package calculator;

import camp.nextstep.edu.missionutils.Console;


public class Application {
    public static void main(String[] args) {

        System.out.println("덧셈할 문자열을 입력해 주세요");

        try {
            String input = Console.readLine();
            int sum = StringAddCalculator.add(input);
            System.out.println("결과 : " + sum);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;//명시적으로 종료 의도 표기
        }


    }
}
