package com.noriteo.delinori_front.service;

import com.noriteo.delinori_front.dto.PageRequestDTO;
import com.noriteo.delinori_front.dto.PageResponseDTO;
import com.noriteo.delinori_front.dto.QnaReplyDTO;
import com.noriteo.delinori_front.entity.QnaReply;
import com.noriteo.delinori_front.repository.QnaReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class QnaReplyServiceImpl implements QnaReplyService{

    private final ModelMapper modelMapper;
    private final QnaReplyRepository qnaReplyRepository;

    @Override
    public PageResponseDTO<QnaReplyDTO> getListOfQna(Long qno, PageRequestDTO pageRequestDTO) {

        Pageable pageable = null;

        if(pageRequestDTO.getPage() == -1) {
            int lastPage = calcLastPage(qno, pageRequestDTO.getSize());
            if (lastPage <= 0 ) {
                lastPage = 1;
            }
            pageRequestDTO.setPage(lastPage);
        }

        pageable = PageRequest.of(pageRequestDTO.getPage() -1, pageRequestDTO.getSize());

        Page<QnaReply> result = qnaReplyRepository.getListByQno(qno, pageable);
        List<QnaReplyDTO> dtoList = result.get()
                .map(qnaReply ->  modelMapper.map(qnaReply, QnaReplyDTO.class))
                .collect(Collectors.toList());

        return new PageResponseDTO<>( pageRequestDTO, (int)result.getTotalElements(),dtoList);
    }

    @Override
    public Long register(QnaReplyDTO qnaReplyDTO) {
        QnaReply qnaReply = modelMapper.map(qnaReplyDTO, QnaReply.class);

        qnaReplyRepository.save(qnaReply);

        return qnaReply.getRno();
    }

    @Override
    public PageResponseDTO<QnaReplyDTO> remove(Long qno, Long rno, PageRequestDTO pageRequestDTO) {

        qnaReplyRepository.deleteById(rno);

        return getListOfQna(qno, pageRequestDTO);
    }

    @Override
    public PageResponseDTO<QnaReplyDTO> modify(QnaReplyDTO qnaReplyDTO, PageRequestDTO pageRequestDTO) {

        QnaReply qnaReply = qnaReplyRepository.findById(qnaReplyDTO.getRno()).orElseThrow();

        qnaReply.setText(qnaReplyDTO.getReplyText());

        qnaReplyRepository.save(qnaReply);

        return getListOfQna(qnaReplyDTO.getQno(), pageRequestDTO);
    }

    private int calcLastPage(Long qno, double size) {

        int count = qnaReplyRepository.getQnaReplyCountOfQna(qno);
        int lastPage = (int)(Math.ceil(count/size));

        return lastPage;

    }
}
