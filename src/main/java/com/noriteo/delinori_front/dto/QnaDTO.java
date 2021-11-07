package com.noriteo.delinori_front.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaDTO {

    private Long qno;

    private String title, content,writer;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private List<QnaPictureDTO> pictures;

}
