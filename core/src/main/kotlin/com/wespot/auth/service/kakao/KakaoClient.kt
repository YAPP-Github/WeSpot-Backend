package com.wespot.auth.service.kakao

import com.wespot.auth.dto.kakao.KakaoRevokeResult
import com.wespot.auth.dto.kakao.KakaoUserInfoResult
import com.wespot.auth.service.SocialHeaderConfiguration
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "kakao", url = "https://kapi.kakao.com", configuration = [SocialHeaderConfiguration::class])
interface KakaoClient {

    /**
     * 사용자 정보 가져오기
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
     */
    @GetMapping("/v2/user/me", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserInfo(
        @RequestHeader("Authorization") authorization: String
    ): KakaoUserInfoResult


    /**
     * 연결 끊기
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-unlink
     */
    @PostMapping("/v1/user/unlink", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun unlink(
        @RequestHeader("Authorization") adminKey: String,
        @RequestParam("target_id_type") targetIdType: String,
        @RequestParam("target_id") targetId: String
    ): KakaoRevokeResult
}