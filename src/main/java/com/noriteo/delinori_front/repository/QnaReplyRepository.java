package com.noriteo.delinori_front.repository;

import com.noriteo.delinori_front.entity.Qna;
import com.noriteo.delinori_front.entity.QnaReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QnaReplyRepository extends JpaRepository<QnaReply,Long> {

    List<QnaReply> findQnaReplyByQna_QnoOrderByRno(Long qno);

    @Query("select r from QnaReply r where r.qna.qno = :qno")
    Page<QnaReply> getListByQno(Long qno, Pageable pageable);

    @Query("select count(r) from QnaReply r where r.qna.qno = :qno")
    int getQnaReplyCountOfQna(Long qno);
}
