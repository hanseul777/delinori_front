package com.noriteo.delinori_front.service;

import com.noriteo.delinori_front.dto.PageRequestDTO;
import com.noriteo.delinori_front.dto.PageResponseDTO;
import com.noriteo.delinori_front.dto.QnaReplyDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface QnaReplyService {

    PageResponseDTO<QnaReplyDTO> getListOfQna(Long qno, PageRequestDTO pageRequestDTO);

    Long register(QnaReplyDTO qnaReplyDTO);

    PageResponseDTO<QnaReplyDTO> remove(Long qno, Long rno, PageRequestDTO pageRequestDTO);

    PageResponseDTO<QnaReplyDTO> modify(QnaReplyDTO qnaReplyDTO, PageRequestDTO pageRequestDTO);
}
