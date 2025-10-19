package calculator;

import java.util.regex.Pattern;


public class StringAddCalculator {

    // ===== 상수 =====
    private static final String DEFAULT_DELIMITER_REGEX = "[,:]"; // 기본 구분자 정규식: 쉼표 또는 콜론
    private static final String POSITIVE_INT_REGEX = "^[1-9]\\d*$"; // 양의 정수(0 불가) 정규식


    private StringAddCalculator() {
    }


    /**
     * 문자열 덧셈 함수
     */
    public static int add(String input) {
        // 0) 빈 입력은 합 0
        if (input == null || input.isEmpty()) {
            return 0;
        }

        // 1) 입력 정규화: 콘솔에서 사용자가 리터럴 "\n" 두 글자를 입력한 경우만 실제 개행으로 치환
        //    그 외 백슬래시 시퀀스는 변환하지 않음(요구사항 범위 내 정규화)
        final String normalized = normalizeInput(input);

        // 2) 구분자/본문 파싱: 기본(, :) 또는 커스텀(//x\n; 커스텀은 단일 문자만 허용)
        final Parsed parsed = parseDelimitersAndBody(normalized);

        // 3) 토큰 분리: 마지막 빈 토큰 보존(예: "1," → ["1", ""])으로 빈 값 검출
        final String[] tokens = splitTokens(parsed.body, parsed.delimiterRegex);

        // 4) 유효성 검증: 빈 값 금지, 공백 문자 금지, 양의 정수만 허용
        validateTokens(tokens);

        // 5) 합산: 개별 숫자 범위/합계 오버플로 모두 IllegalArgumentException으로 일관 처리
        return sumTokens(tokens);
    }

    // ===== 1) 입력 정규화 =====

    private static String normalizeInput(String s) {
        return s.replace("\\n", "\n");
    }

    // ===== 2) 구분자/본문 파싱 =====

    /**
     * 기본 구분자(, :) 또는 커스텀 구분자(//x\n)를 파싱한다. 커스텀 구분자는 한 글자만 허용. 특수문자 구분자는 Pattern.quote로 안전하게 처리.
     */
    private static Parsed parseDelimitersAndBody(String s) {
        // 기본 구분자 모드
        if (!s.startsWith("//")) {
            return new Parsed(DEFAULT_DELIMITER_REGEX, s);
        }

        // 커스텀 구분자 모드
        final int nl = s.indexOf('\n');
        if (nl < 0) {
            throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다.");
        }

        // "//"와 '\n' 사이 문자열이 커스텀 구분자
        final String raw = s.substring(2, nl);
        if (raw.isEmpty()) {
            throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다.");
        }
        if (raw.length() != 1) {
            throw new IllegalArgumentException("커스텀 구분자는 1글자만 허용됩니다.");
        }

        // 특수문자도 문자 그대로 인식되도록 정규식 이스케이프
        final String delimiterRegex = Pattern.quote(raw);
        final String body = s.substring(nl + 1);
        return new Parsed(delimiterRegex, body);
    }

    // ===== 3) 토큰 분리 =====

    private static String[] splitTokens(String body, String delimiterRegex) {
        return body.split(delimiterRegex, -1); // -1: 마지막 빈 토큰 보존
    }

    // ===== 4) 유효성 검증 =====

    /**
     * 검증 규칙 - 빈 토큰 금지 - 공백 문자(스페이스/탭/개행 등) 포함 금지 - 양의 정수만 허용 (0/음수/숫자 외 문자 불가)
     */
    private static void validateTokens(String[] tokens) {
        for (String token : tokens) {
            // 빈 값
            if (token.isEmpty()) {
                throw new IllegalArgumentException("빈 값이 포함되어 있습니다.");
            }
            // 공백 문자(스페이스/탭/개행 등) 포함 금지
            if (!token.equals(token.trim()) || token.matches(".*\\s.*")) {
                throw new IllegalArgumentException("공백이 포함되어 있습니다.");
            }
            // 양의 정수만 허용
            if (!token.matches(POSITIVE_INT_REGEX)) {
                throw new IllegalArgumentException("양의 정수만 입력할 수 있습니다.");
            }
        }
    }

    // ===== 5) 합산 =====

    /**
     * 각 토큰을 정수로 변환하여 합산한다. 1. 각 토큰을 int로 변환한다. (정수가 아닌 경우 NumberFormatException 발생) 2. 합산 시 int 범위를 초과하면
     * ArithmeticException 발생
     */
    private static int sumTokens(String[] tokens) {
        int sum = 0;
        for (String token : tokens) {
            try {
                int value = Integer.parseInt(token); // 개별 토큰을 정수로 변환
                sum = Math.addExact(sum, value); // 오버플로 시 ArithmeticException 발생
            } catch (ArithmeticException e) {
                throw new IllegalArgumentException("합계가 정수 범위를 초과했습니다.");
            }
        }
        return sum;
    }

    // ===== 파싱 결과 전달 =====

    /**
     * 파싱 결과(구분자 정규식과 본문 문자열)를 보관하는 내부 전용 불변 클래스. add 메서드 내부에서만 사용되며, 외부 노출 목적이 없다.
     */
    private static class Parsed {
        final String delimiterRegex;
        final String body;

        Parsed(String delimiterRegex, String body) {
            this.delimiterRegex = delimiterRegex;
            this.body = body;
        }
    }
}
