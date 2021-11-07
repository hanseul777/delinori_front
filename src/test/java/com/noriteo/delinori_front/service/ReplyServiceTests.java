package com.noriteo.delinori_front.service;

import com.noriteo.delinori_front.dto.PageRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {

    @Autowired
    private QnaReplyService qnaReplyService;

    @Test
    public void testList(){
        Long qno = 200L;

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(-1)
                .build();

        log.info(qnaReplyService.getListOfQna(qno, pageRequestDTO));
    }
}
