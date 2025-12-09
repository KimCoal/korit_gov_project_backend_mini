package com.korit.backend_mini.mapper;

import com.korit.backend_mini.dto.response.BoardRespDto;
import com.korit.backend_mini.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    int addBoard(Board board);
    List<BoardRespDto> getBoardList();
    List<BoardRespDto> getBoardListByKeyword(String keyword);
    List<BoardRespDto> getBoardListByUserId (Integer userId);
    Optional<BoardRespDto> getBoardByBoardId (Integer boardId);
    int modifyBoard(Board board);
    int removeBoard(Integer boardId);
}
