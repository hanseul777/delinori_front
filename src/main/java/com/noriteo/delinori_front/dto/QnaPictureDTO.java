package com.noriteo.delinori_front.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid")
public class QnaPictureDTO {

    private String uuid;
    private String fileName;
    private String savePath;
    private int idx;

    public String getLink(){
        return savePath + "/s" + uuid + "_" + fileName;
    }
}
