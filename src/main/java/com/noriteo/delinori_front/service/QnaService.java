package com.noriteo.delinori_front.service;

import com.noriteo.delinori_front.dto.PageRequestDTO;
import com.noriteo.delinori_front.dto.PageResponseDTO;
import com.noriteo.delinori_front.dto.QnaDTO;
import com.noriteo.delinori_front.dto.QnaListDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface QnaService {

    PageResponseDTO<QnaDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<QnaListDTO> getListWithReplyCount(PageRequestDTO pageRequestDTO);

    Long register(QnaDTO qnaDTO);

    QnaDTO read(Long qno);

    void modify(QnaDTO qnaDTO);

}
