package com.noriteo.delinori_front.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "uuid")
public class QnaPicture implements Comparable<QnaPicture>{

    private String uuid;
    private String fileName;
    private String savePath;
    private int idx;

    @Override
    public int compareTo(QnaPicture o) {
        return this.idx - o.idx;
    }
}
