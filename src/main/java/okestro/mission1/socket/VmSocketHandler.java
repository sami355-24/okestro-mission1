package okestro.mission1.socket;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okestro.mission1.exception.custom.NotExistException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static okestro.mission1.util.Message.*;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VmSocketHandler extends TextWebSocketHandler {

    static Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    String HEADER_NAME = "memberId";

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserIdFromHeader(session);
        addUserToServerSession(session, userId);

        log.info(SUCCESS_WEBSOCKET_CONNECT_WITH_SESSION.getMessage().formatted(userId, session.getId()));
        session.sendMessage(new TextMessage(SUCCESS_WEBSOCKET_CONNECT.getMessage().formatted(userId)));
    }

    private String getUserIdFromHeader(WebSocketSession session) {
        String userId = session.getHandshakeHeaders().getFirst(HEADER_NAME);
        if (userId == null) throw new NotExistException(ERROR_NOT_FOUND_MEMBER_IN_HEADER.getMessage());
        return userId;
    }

    private void addUserToServerSession(WebSocketSession session, String userId) throws IOException {
        WebSocketSession existingSession = userSessions.get(userId);
        if (existingSession != null && existingSession.isOpen()) {
            log.info(SUCCESS_WEBSOCKET_DUPLICATE_SESSION.getMessage().formatted(userId) );
            existingSession.close(CloseStatus.NORMAL.withReason(SUCCESS_WEBSOCKET_REPLACE_SESSION.getMessage()));
        }

        userSessions.put(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(SUCCESS_WEBSOCKET_DISCONNET.getMessage());
    }

    public void sendMessageToUser(String userId, String message) throws IOException {
        WebSocketSession userSession = userSessions.get(userId);
        if (userSession == null) return;
        userSession.sendMessage(new TextMessage(message));
    }
}
