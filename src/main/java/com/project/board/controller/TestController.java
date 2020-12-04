package com.project.board.controller;

import com.project.board.dto.BoardDto;
import com.project.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class TestController {
    private BoardService boardService;
    /* 게시글 상세 */
    @GetMapping("/post1/{no}")
    public Map<String, Object> detail(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("boardDto", boardDTO);
        return result;
    }
}
