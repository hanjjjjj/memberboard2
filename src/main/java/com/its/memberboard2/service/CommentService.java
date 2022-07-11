package com.its.memberboard2.service;

import com.its.memberboard2.dto.CommentDTO;
import com.its.memberboard2.entity.BoardEntity;
import com.its.memberboard2.entity.CommentEntity;
import com.its.memberboard2.entity.MemberEntity;
import com.its.memberboard2.repository.BoardRepository;
import com.its.memberboard2.repository.CommentRepository;
import com.its.memberboard2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        Optional<MemberEntity> optionalMemberEntity =
                memberRepository.findByMemberEmail(commentDTO.getCommentWriter());
        Optional<BoardEntity> optionalBoardEntity =
                boardRepository.findById(commentDTO.getBoardNumber());
        if(optionalMemberEntity.isPresent() && optionalBoardEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            BoardEntity boardEntity = optionalBoardEntity.get();
            Long id = commentRepository.save(CommentEntity.entitySave(commentDTO, boardEntity, memberEntity)).getId();
            return id;
        } else {
            return null;
        }

    }
    public CommentDTO findById(Long id){
        Optional<CommentEntity> oc = commentRepository.findById(id);
        if(oc.isPresent()){
            CommentEntity commentEntity = oc.get();
            return CommentDTO.findDTO(commentEntity);
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long id) {
        List<CommentEntity> commentEntityList = commentRepository.findByBoardNumber(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity commentEntity: commentEntityList) {
            commentDTOList.add(CommentDTO.findDTO(commentEntity));
        }
        return commentDTOList;
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
