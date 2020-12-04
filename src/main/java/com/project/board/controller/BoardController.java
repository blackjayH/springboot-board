package com.project.board.controller;

import com.project.board.dto.BoardDto;
import com.project.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;

    /* 게시글 목록 */
    @GetMapping("/board")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        return "board/list.html";
    }


    /* 게시글 상세 */
    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/detail.html";
    }

    /* 게시글 쓰기 화면 */
    @GetMapping("/post")
    public String write() {
        return "/board/write.html";
    }

    /* 게시글 쓰기 */
    @PostMapping("/post")
    public String write(BoardDto boardDto, Principal principal) {
        boardDto.setWriter(principal.getName());
        boardService.savePost(boardDto);
        return "redirect:/board";
    }

    /* 게시글 수정 화면 */
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model, Principal principal) {
        BoardDto boardDTO = boardService.getPost(no);
        if (!boardDTO.getWriter().equals(principal.getName())) {
            return "redirect:/board";
        }
        model.addAttribute("boardDto", boardDTO);
        return "board/update.html";
    }

    /* 게시글 수정 */
    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);
        return "redirect:/board";
    }

    /* 게시글 삭제 */
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no, Principal principal) {
        BoardDto boardDTO = boardService.getPost(no);
        if (!boardDTO.getWriter().equals(principal.getName())) {
            return "redirect:/board";
        }
        boardService.deletePost(no);
        return "redirect:/board";
    }

    /* 검색 */
    @GetMapping("/board/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);
        model.addAttribute("boardList", boardDtoList);
        return "board/list.html";
    }
}
