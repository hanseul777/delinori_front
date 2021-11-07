package com.noriteo.delinori_front.repository.search;

import com.noriteo.delinori_front.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaSearch {

    Page<Qna> search1(char[] typeArr, String keyword, Pageable pageable);

    Page<Object[]> searchWithReplyCount(char[] typeArr, String keyword, Pageable pageable);

    Page<Object[]> getSearchList(Pageable pageable);
}
