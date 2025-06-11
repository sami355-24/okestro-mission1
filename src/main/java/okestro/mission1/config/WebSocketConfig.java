package okestro.mission1.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okestro.mission1.repository.MemberRepository;
import okestro.mission1.socket.SimpleUserPrincipal;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final MemberRepository memberRepository;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/noti")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(handshakeHandler())
                .withSockJS()
                .setInterceptors(handshakeInterceptor());
    }

    private HandshakeInterceptor handshakeInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                           WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                String memberId = UriComponentsBuilder.fromUri(request.getURI())
                        .build()
                        .getQueryParams()
                        .getFirst("memberId");

                log.info("WebSocket Handshake - memberId from query parameter: '{}'", memberId); // 로그 메시지 일관성 유지

                if (!isValidMemberId(memberId, response, attributes)) {
                    return false;
                }

                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Exception exception) {
                if (exception == null) {
                    log.info("WebSocket Handshake Succeeded.");
                } else {
                    log.error("WebSocket Handshake Failed with exception: {}", exception.getMessage());
                }
            }
        };
    }

    private boolean isValidMemberId(String memberId, ServerHttpResponse response, Map<String, Object> attributes) {
        if (memberId == null || memberId.trim().isEmpty()) {
            log.warn("WebSocket Handshake Failed: 'memberId' query parameter is missing or empty.");
            response.setStatusCode(org.springframework.http.HttpStatus.BAD_REQUEST);
            return false;
        }

        int parsedMemberId;
        try {
            parsedMemberId = Integer.parseInt(memberId);
        } catch (NumberFormatException e) {
            log.warn("WebSocket Handshake Failed: Invalid memberId format: {}", memberId);
            response.setStatusCode(org.springframework.http.HttpStatus.BAD_REQUEST);
            return false;
        }

        try {
            memberRepository.findById(parsedMemberId).orElseThrow(() -> new IllegalArgumentException("Member not found for ID: " + memberId));
            attributes.put("memberId", memberId);
            return true;
        } catch (IllegalArgumentException e) {
            log.warn("WebSocket Handshake Failed: {}", e.getMessage());
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return false;
        }
    }

    private DefaultHandshakeHandler handshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                String memberId = (String) attributes.get("memberId");
                log.info("HandshakeHandler: memberId from attributes: '{}'", memberId);

                if (memberId != null && !memberId.trim().isEmpty()) {
                    Principal principal = new SimpleUserPrincipal(memberId);
                    log.info("HandshakeHandler: Created Principal with name: '{}'", principal.getName());
                    return principal;
                }
                log.warn("HandshakeHandler: Could not determine WebSocket Principal. memberId not found or empty in attributes.");
                return null;
            }
        };
    }

}