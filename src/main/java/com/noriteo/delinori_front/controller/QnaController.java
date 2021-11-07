package com.noriteo.delinori_front.controller;

import com.noriteo.delinori_front.dto.PageRequestDTO;
import com.noriteo.delinori_front.dto.PageResponseDTO;
import com.noriteo.delinori_front.dto.QnaDTO;
import com.noriteo.delinori_front.dto.QnaListDTO;
import com.noriteo.delinori_front.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("============controller===================");

        model.addAttribute("responseDTO", qnaService.getListWithReplyCount(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String registerPost(QnaDTO qnaDTO, RedirectAttributes redirectAttributes){
        Long qno = qnaService.register(qnaDTO);
        redirectAttributes.addFlashAttribute("result",qno);
        return "redirect:/qna/list";
    }

    @GetMapping(value = {"/read","/modify"})
    public void read(Long qno, PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("dto",qnaService.read(qno));
    }

    @PostMapping("/modify")
    public String modifyPost(QnaDTO qnaDTO, PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes){

        qnaService.modify(qnaDTO);

        redirectAttributes.addFlashAttribute("result","modified");

        return "redirect:/qna/modify";
    }

}
