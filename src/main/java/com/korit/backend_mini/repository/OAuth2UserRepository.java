package com.korit.backend_mini.repository;

import com.korit.backend_mini.entity.OAuth2User;
import com.korit.backend_mini.mapper.OAuth2UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OAuth2UserRepository {
    private final OAuth2UserMapper oAuth2UserMapper;

    public Optional<OAuth2User> findOAuth2UserByProviderAndProviderUserId(String provider, String providerUserId) {
        return oAuth2UserMapper.findOAuth2UserByProviderAndProviderUserId(provider, providerUserId);
    }

    public int addOAuth2User (OAuth2User oAuth2User) {
        return oAuth2UserMapper.addOAuth2User(oAuth2User);
    }
}
