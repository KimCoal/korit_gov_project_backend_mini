package com.korit.backend_mini.mapper;

import com.korit.backend_mini.entity.OAuth2User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface OAuth2UserMapper {
    Optional<OAuth2User> findOAuth2UserByProviderAndProviderUserId(String provider, String providerUserId);
    int addOAuth2User(OAuth2User oAuth2User);
}
