package com.noriteo.delinori_front.repository;

import com.noriteo.delinori_front.entity.Qna;
import com.noriteo.delinori_front.repository.search.QnaSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QnaRepository extends JpaRepository<Qna,Long>, QnaSearch {

    @Query("select q.qno, q.title, q.writer, count(qr) from Qna q left join QnaReply qr on qr.qna=q group by q")
    Page<Object[]> ex1(Pageable pageable);

}
