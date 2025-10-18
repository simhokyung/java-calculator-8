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
            delimiter = Pattern.quote(input.substring(2, newlineIndex));
            numbers = input.substring(newlineIndex+1);
        }

        String[] tokens = numbers.split(delimiter,-1);  // -1: 빈 토큰(예: "1,"의 마지막)까지 보존

        //검증 로직 추가
        for(String token:tokens){
            //1) 빈 토큰 금지(예: "1,,2" 또는 "1:" 등)
            if(token.isEmpty()){
                throw new IllegalArgumentException("빈 값이 포함되어 있습니다.");
            }

            //2) 공백 금지(앞뒤/중간 공백 모두 차단)
            // 앞뒤 공백: token.equals(token.trim())로 판별
            // 중간 공백: 문자 그대로 ' ' 포함 여부 추가 체크
            if(!token.equals(token.trim())||token.contains(" ")){
                throw new IllegalArgumentException("공백이 포함되어 있습니다.");
            }

            //3) 양의 정수만 허용: 0,음수,숫자가 아닌것 모두 차단
            if(!token.matches("^[1-9]\\d*$")){
                throw new IllegalArgumentException("양의 정수만 입력할 수 있습니다.");
            }


        }



        //합산(검증 통과 후에만 파싱)
        int sum=0;
        for(String token:tokens){
            sum+=Integer.parseInt(token);
        }
        return sum;
    }

}
