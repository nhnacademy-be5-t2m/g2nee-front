package com.t2m.g2nee.front.point.dto;

public enum ChangeReason {
    REVIEW("리뷰작성 적립"), SIGNUP("회원가입 적립"), PURCHASE("구매 적립"), RETIRE("구매 적립금 회수(반품)"), RETURN("사용 포인트 반환(반품)"),
    USE("포인트 사용");

    private final String name;

    ChangeReason(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}