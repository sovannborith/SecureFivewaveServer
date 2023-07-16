package com.securefivewave.util.http;
import java.util.Objects;

import org.springframework.web.server.ServerWebExchange;

import com.securefivewave.dto.ServerInfoDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpUtil {



    public ServerInfoDTO getServerInfo(HttpServletRequest request) {
        /* String ip;
        String device;
        for (String header: IP_HEADERS) {
            String value = request.getHeader(header);

            if (value == null || value.isEmpty()) {
                continue;
            }
            String[] parts = value.split("\\s*,\\s*");
            ip = parts[0];
        } */

        return new ServerInfoDTO(request.getRemoteAddr(), request.getRemoteHost());
    }

    public static String getRemoteIpaddress(ServerWebExchange request) {
        return Objects.requireNonNull(request.getRequest().getRemoteAddress()).getAddress().getHostAddress();
    }

    /* private static final List<String> IP_HEADERS = List.of(
      "X-Forwarded-For",
      "HTTP_FORWARDED",
      "HTTP_FORWARDED_FOR",
      "HTTP_X_FORWARDED",
      "HTTP_X_FORWARDED_FOR",
      "HTTP_CLIENT_IP",
      "HTTP_VIA",
      "HTTP_X_CLUSTER_CLIENT_IP",
      "Proxy-Client-IP",
      "WL-Proxy-Client-IP",
      "REMOTE_ADDR"
  ); */
    
}
