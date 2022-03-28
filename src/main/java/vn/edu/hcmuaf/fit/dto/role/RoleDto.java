package vn.edu.hcmuaf.fit.dto.role;

import vn.edu.hcmuaf.fit.entity.Role;

public class RoleDto {
    private Long id;
    private String name;

    public RoleDto() {
    }

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RoleDto createFromEntity(Role src) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(src.getId());
        roleDto.setName(src.getName());
        return roleDto;
    }
}
