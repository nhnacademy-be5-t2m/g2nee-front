package com.t2m.g2nee.front.booklike.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLikeDto {

    private Long memberId;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    private Long bookId;


}
