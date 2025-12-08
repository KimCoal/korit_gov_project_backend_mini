package com.korit.backend_mini.controller;

import com.korit.backend_mini.dto.AddBoardReqDto;
import com.korit.backend_mini.dto.ModifyBoardReqDto;
import com.korit.backend_mini.dto.RemoveBoardReqDto;
import com.korit.backend_mini.security.model.Principal;
import com.korit.backend_mini.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @PostMapping("/add")
    public ResponseEntity<?> addBoard(@RequestBody AddBoardReqDto addBoardReqDto,
                                      @AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(boardService.addBoard(addBoardReqDto, principal));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getBoardList() {
        return ResponseEntity.ok(boardService.getBoardList());
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardByBoardId(@PathVariable Integer boardId) {
        return ResponseEntity.ok(boardService.getBoardByBoardId(boardId));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getBoardListByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(boardService.getBoardByKeyword(keyword));
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyBoard (@RequestBody ModifyBoardReqDto modifyBoardReqDto,
                                          @AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(boardService.modifyBoard(modifyBoardReqDto, principal));
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeBoard (@RequestBody RemoveBoardReqDto removeBoardReqDto,
                                          @AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(boardService.removeBoard(removeBoardReqDto, principal));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBoardListByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(boardService.getBoardByUserId(userId));
    }
}
