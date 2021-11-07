package com.noriteo.delinori_front.repository;

import com.noriteo.delinori_front.dto.QnaDTO;
import com.noriteo.delinori_front.entity.Qna;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class QnaRepositoryTests {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testSearch1(){
        char[] typeArr = null;
        String keyword = null;
        Pageable pageable = PageRequest.of(0,10, Sort.by("qno").descending());

        Page<Qna> result = qnaRepository.search1(typeArr,keyword,pageable);

        result.get().forEach(qna -> {
            log.info(qna);
            log.info("=========testSearch1============");

            QnaDTO qnaDTO = modelMapper.map(qna, QnaDTO.class);

            log.info(qnaDTO);
        });
    }

    @Test
    public void testEx1(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("qno").descending());

        Page<Object[]> result = qnaRepository.ex1(pageable);

        log.info(result);

        result.get().forEach(element ->{
            Object[] arr = (Object[])element;

            log.info(Arrays.toString(arr));

        });
    }

    @Test
    public void testSelect2(){
        Long qno = 1L;

        Optional<Qna> qnaOptional = qnaRepository.findById(qno);

        Qna qna = qnaOptional.orElseThrow();

        QnaDTO dto = modelMapper.map(qna, QnaDTO.class);

        log.info(dto);
    }
}
