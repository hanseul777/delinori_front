package com.noriteo.delinori_front.service;

import com.noriteo.delinori_front.dto.PageResponseDTO;
import com.noriteo.delinori_front.dto.QnaDTO;
import com.noriteo.delinori_front.dto.QnaPictureDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class QnaServiceTests {

    @Autowired
    private QnaService qnaService;

    @Test
    public void testRegister(){
        IntStream.rangeClosed(1,20).forEach(i ->{
            QnaDTO qnaDTO = QnaDTO.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .writer("Writer " + (i%10))
                    .build();

            Long qno = qnaService.register(qnaDTO);
            log.info("QNO : "+ qno);
        });
    }

    @Test
    public void testPictureRegister(){
        List<QnaPictureDTO> pictures = IntStream.rangeClosed(1,3).mapToObj(j -> {

            QnaPictureDTO picture = QnaPictureDTO.builder()
                    .uuid(UUID.randomUUID().toString())
                    .savePath("2021/10/30")
                    .fileName("img" + j + ".jpg")
                    .idx(j)
                    .build();

            return picture;
        }).collect(Collectors.toList());

        QnaDTO dto = QnaDTO.builder()
                .title("title....")
                .content("content...")
                .writer("writer...")
                .pictures(pictures)
                .build();

        qnaService.register(dto);
    }

    @Test
    public void testRead(){
        Long qno = 100L;

        QnaDTO dto = qnaService.read(qno);

        log.info(dto);

        log.info(dto.getPictures().size());

        dto.getPictures().forEach(qnaPictureDTO -> log.info(qnaPictureDTO));
    }
}
