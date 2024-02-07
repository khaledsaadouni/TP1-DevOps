package com.garkclub.config;

import com.garkclub.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private String extractTokenFromRequest(String query) {
        if (query != null) {
            String[] queryParams = query.split("&");
            for (String param : queryParams) {
                if (param.startsWith("token=")) {
                    return param.substring("token=".length());
                }
            }
        }
        return null;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("web socket openning -------------------------------------------");
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
//                .setInterceptors(new HandshakeInterceptor() {
//                    @Override
//                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//                        // Retrieve the JWT token from the request (You need to implement this)
//                        String jwt = extractTokenFromRequest(request.getURI().getQuery());
//                        String userEmail = jwtService.extractUsername(jwt);
//                        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//                        if (jwtService.isTokenValid(jwt, userDetails)) {
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    }
//
//                    @Override
//                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//                        // No need to implement anything here
//                    }
//                });
    }

    //    registerStompEndpoints(StompEndpointRegistry registry): This method configures WebSocket endpoints for STOMP (Simple Text Oriented Messaging Protocol). In this example, an endpoint is added with the path "/ws". The setAllowedOriginPatterns("*") method allows connections from any origin, and withSockJS() enables the fallback mechanism using SockJS.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/chatroom", "/user");
        registry.setUserDestinationPrefix("/user");
    }
}
