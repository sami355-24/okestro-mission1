package okestro.mission1.component;

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

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VmSocketHandler extends TextWebSocketHandler {

    static Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserIdFromHeader(session);
        addUserToServerSession(session, userId);

        log.info("사용자 연결됨 - userId: {}, 세션 ID: {}", userId, session.getId());
        session.sendMessage(new TextMessage("서버에 연결되었습니다. userId: " + userId));
    }

    private String getUserIdFromHeader(WebSocketSession session) {
        String userId = session.getHandshakeHeaders().getFirst("userId");
        if (userId == null) throw new NotExistException("헤더에 유저 정보가 존재하지 않습니다.");
        return userId;
    }

    private void addUserToServerSession(WebSocketSession session, String userId) throws IOException {
        WebSocketSession existingSession = userSessions.get(userId);
        if (existingSession != null && existingSession.isOpen()) {
            log.info("이미 연결된 사용자 세션이 있습니다. 기존 세션을 종료합니다. UserId: {}", userId);
            existingSession.close(CloseStatus.NORMAL.withReason("새 연결에 의해 대체됨"));
        }

        userSessions.put(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("소켓 연결 끊어짐");
    }

    public void sendMessageToUser(String userId, String message) throws IOException {
        WebSocketSession userSession = userSessions.get(userId);
        if (userSession == null) return;
        userSession.sendMessage(new TextMessage(message));
    }
}
