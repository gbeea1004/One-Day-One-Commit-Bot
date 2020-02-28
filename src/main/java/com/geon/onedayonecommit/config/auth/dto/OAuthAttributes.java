package com.geon.onedayonecommit.config.auth.dto;

import com.geon.onedayonecommit.domain.user.Role;
import com.geon.onedayonecommit.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private Integer id;
    private String name;
    private String picture;
    private String githubId;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, Integer id, String name, String picture, String githubId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.githubId = githubId;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofKakao(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return OAuthAttributes.builder()
                              .id((Integer) attributes.get("id"))
                              .name((String) properties.get("nickname"))
                              .picture((String) properties.get("profile_image"))
                              .attributes(attributes)
                              .nameAttributeKey(userNameAttributeName)
                              .build();
    }

    public User toEntity() {
        return User.builder()
                   .id(id)
                   .name(name)
                   .picture(picture)
                   .role(Role.GUEST)
                   .build();
    }
}
