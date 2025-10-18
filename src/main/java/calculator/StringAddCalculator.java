package calculator;

public class StringAddCalculator {
    private StringAddCalculator(){}


    public static int add(String input){

        //빈 문자열이면 0 반환
        if(input==null||input.isEmpty()){
            return 0;
        }

        String delimiter = "[,:]"; //기본 구분자
        String numbers = input;

        if(input.startsWith("//")){
            int newlineIndex = input.indexOf("\n");
            if(newlineIndex==-1){
                throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다.");
            }

            //   "//"와 "\n" 사이의 문자를 구분자로 사용
            delimiter = input.substring(2, newlineIndex);
            numbers = input.substring(newlineIndex+1);
        }

        String[] tokens = numbers.split(delimiter);
        int sum=0;
        for(String token:tokens){
            sum+=Integer.parseInt(token);
        }
        return sum;
    }

}
