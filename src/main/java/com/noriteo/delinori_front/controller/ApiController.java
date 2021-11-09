package com.noriteo.delinori_front.controller;

import com.noriteo.delinori_front.dto.PageRequestDTO;
import com.noriteo.delinori_front.dto.PageResponseDTO;
import com.noriteo.delinori_front.dto.QnaDTO;
import com.noriteo.delinori_front.service.QnaService;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class ApiController {

    private final QnaService qnaService;

    @PostMapping("/qna/register")
    public Map<String, Long> register(@RequestBody QnaDTO qnaDTO){

        return null;
    }

    @GetMapping("/qna/list")
    public PageResponseDTO<QnaDTO> getList(PageRequestDTO pageRequestDTO){
        log.info("===========pageRequestDTO===========");
        log.info(pageRequestDTO);

        return qnaService.list(pageRequestDTO);
    }


}
