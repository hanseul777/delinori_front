package com.noriteo.delinori_front.repository;

import com.noriteo.delinori_front.entity.Qna;
import com.noriteo.delinori_front.entity.QnaReply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class QnaReplyRepositoryTests {

    @Autowired
    private QnaReplyRepository qnaReplyRepository;

    @Test
    public void replyInsert(){
        IntStream.rangeClosed(1,200).forEach(i ->{
            Long qno = (long)200 - (i % 5); //200,199,198,197,196
            int replyCount = (i % 5); // 0,1,2,3,4,

            Qna qna = Qna.builder().qno(qno).build();

            IntStream.rangeClosed(0,replyCount).forEach(j -> {
                QnaReply qnaReply = QnaReply.builder()
                        .replyText("==Reply==")
                        .replyer("==Replyer==")
                        .qna(qna)
                        .build();

                qnaReplyRepository.save(qnaReply);
            });

        });
    }

    @Test
    public void replyRead(){
        Long rno = 1L;

        QnaReply qnaReply = qnaReplyRepository.findById(rno).get();

        log.info(qnaReply);

        log.info(qnaReply.getQna());
    }

    @Test
    public void testByQno(){
        Long qno = 100L;

        List<QnaReply> replyList = qnaReplyRepository.findQnaReplyByQna_QnoOrderByRno(qno);

        replyList.forEach(qnaReply -> log.info(qnaReply));
    }

    @Test
    public void testListOfQna(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("rno").descending());

        Page<QnaReply> result = qnaReplyRepository.getListByQno(100L,pageable);

        log.info(result.getTotalElements());

        result.get().forEach(qnaReply -> log.info(qnaReply));
    }

    @Test
    public void testCountOfQna(){
        Long qno = 100L;

        int count = qnaReplyRepository.getQnaReplyCountOfQna(qno);

        int lastPage =(int)(Math.ceil(count/10.0));

        if (lastPage == 0){
            lastPage = 1;
        }

        Pageable pageable = PageRequest.of(lastPage -1, 10);

        Page<QnaReply> result = qnaReplyRepository.getListByQno(qno,pageable);

        log.info("Total : " + result.getTotalElements());
        log.info(result.getTotalPages());

        result.get().forEach(qnaReply -> {
            log.info(qnaReply);
        });
    }
}
