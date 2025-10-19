package calculator;

import camp.nextstep.edu.missionutils.Console;


public class Application {
    public static void main(String[] args) {

        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = Console.readLine();

        // 예외를 잡아 출력하지 말 것! (채점기는 예외 전파/종료를 기대)
        int sum = StringAddCalculator.add(input);
        System.out.println("결과 : " + sum);


    }
}
