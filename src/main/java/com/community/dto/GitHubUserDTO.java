package com.community.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by 舒先亮 on 2019/8/21.
 */
@Component
@Data
public class GitHubUserDTO {
    private Long id;
    private String name;
    private String bio;
    private String avatarUrl;

    /*public Long getId() {
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }*/
}
