package com.jybeomss1.realestateauction.common.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "등급 업그레이드", description = "사용자 등급을 LITE에서 PRO로 업그레이드")
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "등급 업그레이드 성공",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "등급 업그레이드 성공 예시 응답",
                                value = "\"success\""
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "등급 업그레이드 실패",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "등급 업그레이드 실패 예시 응답",
                                value = "\"유효하지 않은 등급 업그레이드입니다.\""
                        )
                )
        )
})
public @interface UserGradeUpgradeSwaggerDoc {
} 