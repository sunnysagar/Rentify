package com.sunny.Rentify.config;

import com.sunny.Rentify.component.JwtUtil;
import com.sunny.Rentify.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.*;

// to handle real-time communication btw frontend and backend
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);
//
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
//        registry.addEndpoint("/websocket");
    }



    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                log.info("Header: {}", accessor);

                assert accessor != null;

                if(StompCommand.CONNECT.equals(accessor.getCommand())){
                    String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
                    assert authorizationHeader != null;
                    String token = authorizationHeader.substring(7);
                    String username = jwtUtil.getUsername(token);
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    accessor.setUser(usernamePasswordAuthenticationToken);
                }

                return message;
            }
        });
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        // Additional configuration if needed
    }
}

//2

//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(new CustomWebSocketHandler(), "/websocket")
//                .setAllowedOrigins("http://localhost:3000")
//                .addInterceptors(new HttpSessionHandshakeInterceptor())
//                .withSockJS();
//    }
//
//    @Bean
//    public SimpMessagingTemplate simpMessagingTemplate(SimpUserRegistry simpUserRegistry) {
//        return new SimpMessagingTemplate((MessageChannel) simpUserRegistry);
//    }
//
////    @Bean
////    public SimpUserRegistry simpUserRegistry() {
////        return new SimpleSimpUserRegistry();
////    }
//
//}
