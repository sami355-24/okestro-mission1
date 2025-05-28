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

    static Map<String, WebSocketSession> memberSessions = new ConcurrentHashMap<>();
    String HEADER_NAME = "memberId";

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String memberId = getMemberIdFromHeader(session);
        addMemberToServerSession(session, memberId);

        log.info(SUCCESS_WEBSOCKET_CONNECT_WITH_SESSION.getMessage().formatted(memberId, session.getId()));
        session.sendMessage(new TextMessage(SUCCESS_WEBSOCKET_CONNECT.getMessage().formatted(memberId)));
    }

    private String getMemberIdFromHeader(WebSocketSession session) {
        String memberId = session.getHandshakeHeaders().getFirst(HEADER_NAME);
        if (memberId == null) throw new NotExistException(ERROR_NOT_FOUND_MEMBER_IN_HEADER.getMessage());
        return memberId;
    }

    private void addMemberToServerSession(WebSocketSession session, String memberId) throws IOException {
        WebSocketSession existingSession = memberSessions.get(memberId);
        if (existingSession != null && existingSession.isOpen()) {
            log.info(SUCCESS_WEBSOCKET_DUPLICATE_SESSION.getMessage().formatted(memberId) );
            existingSession.close(CloseStatus.NORMAL.withReason(SUCCESS_WEBSOCKET_REPLACE_SESSION.getMessage()));
        }

        memberSessions.put(memberId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(SUCCESS_WEBSOCKET_DISCONNET.getMessage());
    }

    public void sendMessageToUser(String userId, String message) throws IOException {
        WebSocketSession userSession = memberSessions.get(userId);
        if (userSession == null) return;
        userSession.sendMessage(new TextMessage(message));
    }
}
