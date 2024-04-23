package com.t2m.g2nee.front.bookset.role.adaptor;

import com.t2m.g2nee.front.bookset.role.dto.RoleDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;


/**
 * 역할 adaptor interface
 *
 * @author : 신동민
 * @since : 1.0
 */
public interface RoleAdaptor {
    /**
     * 모든 역할을 조회하는 메서드
     * @return List<RoleDto.Response>
     */
    List<RoleDto.Response> getAllRole();

    /**
     * 역할 등록 메서드
     *
     * @param request 등록할 정보가 있는 객체
     */
   void registerRole(RoleDto.Request request);

    /**
     * 역할 조회 메서드
     *
     * @param page 페이지 번호
     * @return PageResponse<RoleDto.Response>
     */
    PageResponse<RoleDto.Response> getAllRole(int page);


    /**
     * 역할 수정 메서드
     *
     * @param roleId  역할 아이디
     * @param request 수정할 정보가 담긴 객체
     */
    void updateRole(Long roleId, RoleDto.Request request);

    /**
     * 역할 삭제 메서드
     *
     * @param roleId 역할 아이디
     */
    void deleteRole(Long roleId);
    }
