package com.noriteo.delinori_front.controller;

import com.noriteo.delinori_front.dto.PageRequestDTO;
import com.noriteo.delinori_front.dto.PageResponseDTO;
import com.noriteo.delinori_front.dto.QnaReplyDTO;
import com.noriteo.delinori_front.service.QnaReplyService;
import groovy.transform.ASTTest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/qna/replies")
public class QnaReplyController {

    private final QnaReplyService qnaReplyService;

    @GetMapping("/list/{qno}")
    public PageResponseDTO<QnaReplyDTO> getListOfQna(@PathVariable("qno") Long qno, PageRequestDTO pageRequestDTO) {
        return qnaReplyService.getListOfQna(qno, pageRequestDTO);
    }

    @PostMapping("")
    public PageResponseDTO<QnaReplyDTO> register(@RequestBody QnaReplyDTO qnaReplyDTO) {
        qnaReplyService.register(qnaReplyDTO);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(-1).build();

        return qnaReplyService.getListOfQna(qnaReplyDTO.getQno(), pageRequestDTO);
    }

    @DeleteMapping("/{qno}/{rno}")
    public PageResponseDTO<QnaReplyDTO> remove(
            @PathVariable("qno") Long qno,
            @PathVariable("rno") Long rno,
            PageRequestDTO pageRequestDTO) {

        return qnaReplyService.remove(qno, rno, pageRequestDTO);

    }

    @PutMapping("/{qno}/{rno}")
    public PageResponseDTO<QnaReplyDTO> modify(
            @PathVariable("qno") Long qno,
            @PathVariable("rno") Long rno,
            @RequestBody QnaReplyDTO qnaReplyDTO,
            PageRequestDTO pageRequestDTO) {

        log.info("qno: " + qno);
        log.info("rno: " + rno);
        log.info("qnaReplyDTO: " + qnaReplyDTO);

        return qnaReplyService.modify(qnaReplyDTO, pageRequestDTO);
    }
}
