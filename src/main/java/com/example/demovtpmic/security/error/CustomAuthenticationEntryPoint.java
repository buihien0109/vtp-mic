package com.example.demovtpmic.security.error;

import com.example.demovtpmic.constant.Constant;
import com.example.demovtpmic.model.response.ApiResponse;
import com.example.demovtpmic.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Bạn cần phải đăng nhập")
                .build();
        ApiResponse<String, ErrorResponse> apiResponse = ApiResponse.<String, ErrorResponse>builder()
                .code(Constant.FAILED_CODE)
                .message(Constant.FAILED_MESSAGE)
                .data(null)
                .errorData(error)
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, apiResponse);
        responseStream.flush();
    }
}
