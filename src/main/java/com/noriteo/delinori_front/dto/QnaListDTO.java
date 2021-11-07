package com.noriteo.delinori_front.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaListDTO {

    private Long qno;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private long replyCount;

    private List<QnaPictureDTO> pictures;

}
