package calculator;

import java.util.regex.Pattern;

public class StringAddCalculator {
    private StringAddCalculator(){}


    public static int add(String input){

        //빈 문자열이면 0 반환
        if(input==null||input.isEmpty()){
            return 0;
        }

        // 문자열 리터럴로 입력받은 경우(\n을 실제 줄바꿈으로 변환)
        input = input.replace("\\n", "\n");

        String delimiter = "[,:]"; //기본 구분자
        String numbers = input;

        if(input.startsWith("//")){
            int newlineIndex = input.indexOf("\n");
            if(newlineIndex==-1){
                throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다.");
            }


            // "//"와 "\n" 사이를 커스텀 구분자로 사용 (정규식 이스케이프)
            delimiter = Pattern.quote(input.substring(2, newlineIndex));            numbers = input.substring(newlineIndex+1);
        }

        String[] tokens = numbers.split(delimiter);
        int sum=0;
        for(String token:tokens){
            sum+=Integer.parseInt(token);
        }
        return sum;
    }

}
