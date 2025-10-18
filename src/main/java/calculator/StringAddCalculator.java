package calculator;

public class StringAddCalculator {
    private StringAddCalculator(){

    }
    public static int add(String input){

        //빈 문자열이면 0 반환
        if(input==null||input.isEmpty()){
            return 0;
        }

        String[] numbers = input.split("[,:]");

        int sum = 0;
        for(String number:numbers){
            sum+=Integer.parseInt(number); //아직 입력값 검증은 안함(음수,숫자 아님 등)
        }


        return sum;
    }

}
