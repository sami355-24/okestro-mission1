package okestro.mission1.dto.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;
    private String recipient; // 개인 메시지용
    private String timestamp;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}

