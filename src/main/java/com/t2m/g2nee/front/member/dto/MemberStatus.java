package com.t2m.g2nee.front.member.dto;

/**
 * Member의 상태를 위한 enum
 *
 * @author : 정지은
 * @since : 1.0
 */
public enum MemberStatus {

    ACTIVE("활동"), QUIT("탈퇴"), INACTIVE("휴면");

    private final String name;

    MemberStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}