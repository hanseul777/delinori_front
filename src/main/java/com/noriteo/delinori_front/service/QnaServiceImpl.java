package com.noriteo.delinori_front.service;

import com.noriteo.delinori_front.dto.PageRequestDTO;
import com.noriteo.delinori_front.dto.PageResponseDTO;
import com.noriteo.delinori_front.dto.QnaDTO;
import com.noriteo.delinori_front.dto.QnaListDTO;
import com.noriteo.delinori_front.entity.Qna;
import com.noriteo.delinori_front.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor

public class QnaServiceImpl implements QnaService {

    private final ModelMapper modelMapper;
    private final QnaRepository qnaRepository;

    @Override
    public PageResponseDTO<QnaDTO> list(PageRequestDTO pageRequestDTO) {

        log.info("================service==================");

        char[] typeArr = pageRequestDTO.getTypes(); //검색조건
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("qno").descending());

        Page<Qna> result = qnaRepository.search1(typeArr,keyword,pageable);

        List<QnaDTO> dtoList = result.get().map(qna -> modelMapper.map(qna,QnaDTO.class)).collect(Collectors.toList());
        long totalCount = result.getTotalElements();

        return new PageResponseDTO<>(pageRequestDTO,(int)totalCount,dtoList);
    }

    @Override
    public PageResponseDTO<QnaListDTO> getListWithReplyCount(PageRequestDTO pageRequestDTO) {

        char[] typeArr = pageRequestDTO.getTypes(); //검색조건
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("qno").descending());

        Page<Object[]> result = qnaRepository.searchWithReplyCount(typeArr, keyword,pageable);

        List<QnaListDTO> dtoList = result.get().map(objects -> {
            QnaListDTO listDTO = QnaListDTO.builder()
                    .qno((Long) objects[0])
                    .title((String) objects[1])
                    .writer((String) objects[2])
                    .regDate((LocalDateTime) objects[3])
                    .replyCount((Long) objects[4])
                    .build();
            return listDTO;
        }).collect(Collectors.toList());

        return new PageResponseDTO<>(pageRequestDTO,(int)result.getTotalElements(),dtoList);
    }

    @Override
    public Long register(QnaDTO qnaDTO) {

        Qna qna = modelMapper.map(qnaDTO, Qna.class);

        log.info(qna);
        log.info(qna.getPictures());

        qnaRepository.save(qna);

        return qna.getQno();
    }

    @Override
    public QnaDTO read(Long qno) {
//        Optional<Qna> result = qnaRepository.findById(qno);

//        if (result.isEmpty()){
//            throw new RuntimeException("NOT FOUND");
//        }

//        Qna qna = optionalQna.orElseThrow();

//        QnaDTO dto = modelMapper.map(result.get(), QnaDTO.class);

//        return modelMapper.map(qna, QnaDTO.class);

        Optional<Qna> optionalDiary = qnaRepository.findById(qno);

        Qna qna = optionalDiary.orElseThrow();

        QnaDTO dto = modelMapper.map(qna, QnaDTO.class);

        return dto;
    }

    @Override
    public void modify(QnaDTO qnaDTO) {
        Optional<Qna> result = qnaRepository.findById(qnaDTO.getQno());

        if (result.isEmpty()){
            throw new RuntimeException("NOT FOUND");
        }

        Qna qna = result.get();
        qna.change(qnaDTO.getTitle(), qnaDTO.getContent());

        qnaRepository.save(qna);
    }

}
