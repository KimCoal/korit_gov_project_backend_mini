package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.*;
import com.korit.backend_mini.entity.Board;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.repository.BoardRepository;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.model.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public ApiRespDto<?> addBoard (AddBoardReqDto addBoardReqDto, Principal principal) {
        if (!addBoardReqDto.getUserId().equals(principal.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다.", null);
        }
        Optional<User> foundUser = userRepository.findUserByUserId(addBoardReqDto.getUserId());
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않은 회원정보입니다", null);
        }

        int result = boardRepository.addBoard(addBoardReqDto.toEntity());
        if (result != 1) {
            return new ApiRespDto<>("failed", "추가 실패", null);
        }
        return new ApiRespDto<>("success", "추가 성공", null);
    }

    public ApiRespDto<?> getBoardList() {
        return new ApiRespDto<>("success", "게시물 전체 조회 성공", boardRepository.getBoardList());
    }

    public ApiRespDto<?> getBoardByBoardId (Integer boardId) {
        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(boardId);
        if (foundBoard.isEmpty()) {
            return new ApiRespDto<>("failed", "해당 게시물이 존재하지 않습니다", null);
        }

        return new ApiRespDto<>("success", "게시물 상세조회 완료", foundBoard.get());
    }

    public ApiRespDto<?> getBoardByKeyword (String keyword) {
        return new ApiRespDto<>("success", "키워드 검색 성공", boardRepository.getBoardListByKeyword(keyword));
    }

    public ApiRespDto<?> modifyBoard(ModifyBoardReqDto modifyBoardReqDto, Principal principal) {
        if (!modifyBoardReqDto.getUserId().equals(principal.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다",null);
        }
        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(modifyBoardReqDto.getBoardId());
        if (foundBoard.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 게시물입니다", null);
        }
        int result = boardRepository.modifyBoard(modifyBoardReqDto.toEntity());
        if (result != 1) {
            return new ApiRespDto<>("failed", "수정 실패", null);
        }
        return new ApiRespDto<>("success", "수정 성공", null);

    }

    public ApiRespDto<?> removeBoard(RemoveBoardReqDto removeBoardReqDto, Principal principal) {
        if (!removeBoardReqDto.getUserId().equals(principal.getUserId()) && principal.getUserRoles().stream().noneMatch(userRole -> userRole.getRole().getRoleId() == 1)) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다", null);
        }

        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(removeBoardReqDto.getBoardId());
        if (foundBoard.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는", null);
        }

        int result = boardRepository.removeBoard(removeBoardReqDto.getBoardId());
        if (result != 1) {
            return new ApiRespDto<>("failed", "삭제 실패", null);
        }
        return new ApiRespDto<>("success", "삭제 성공", null);
    }

    public ApiRespDto<?> getBoardByUserId (Integer userId) {
        Optional<User> foundUser = userRepository.findUserByUserId(userId);
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 회원정보", null);
        }

        List<BoardRespDto> boardRespDtoList = boardRepository.getBoardListByUserId(userId);

        return new ApiRespDto<>("success", "조회 완료", boardRespDtoList);
    }

}
