package com.t2m.g2nee.front.booklike.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLikeDto {

    private Long memberId;

    private Long bookId;

    private boolean isLiked;

}
