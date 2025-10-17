package calculator;

import camp.nextstep.edu.missionutils.Console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        System.out.println("덧셈할 문자열을 입력해주세요");

        try{
            String input = Console.readLine();
            int sum = StringAddCalculator.add(input);
            System.out.println("결과 : "+sum);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println("알 수 없는 오류가 발생했습니다.");
        }


    }
}
