package com.t2m.g2nee.front.bookset.role.service;

import com.t2m.g2nee.front.bookset.role.adaptor.RoleAdaptor;
import com.t2m.g2nee.front.bookset.role.dto.RoleDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import org.springframework.stereotype.Service;


/**
 * 역할 관리 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
public class RoleService {

    private final RoleAdaptor roleAdaptor;

    public RoleService(RoleAdaptor roleAdaptor) {
        this.roleAdaptor = roleAdaptor;
    }

    public List<RoleDto.Response> getAllRole() {

        return roleAdaptor.getAllRole();
    }

    /**
     * 역할 등록 메서드
     *
     * @param request 등록할 정보가 있는 객체
     */
    public void registerRole(RoleDto.Request request) {

        roleAdaptor.registerRole(request);

    }

    /**
     * 역할 조회 메서드
     *
     * @param page 페이지 번호
     * @return PageResponse<RoleDto.Response>
     */
    public PageResponse<RoleDto.Response> getAllRole(int page) {

        return roleAdaptor.getAllRole(page);
    }

    /**
     * 역할 수정 메서드
     *
     * @param roleId  역할 아이디
     * @param request 수정할 정보가 담긴 객체
     */
    public void updateRole(Long roleId, RoleDto.Request request) {
        roleAdaptor.updateRole(roleId, request);

    }

    /**
     * 역할 삭제 메서드
     *
     * @param roleId 역할 아이디
     */
    public void deleteRole(Long roleId) {
        roleAdaptor.deleteRole(roleId);
    }
}
