package com.noriteo.delinori_front.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaReplyDTO {

    private Long rno;
    private String replyText;
    private String replyer;
    private Long qno;
    private LocalDateTime replyDate;
}
