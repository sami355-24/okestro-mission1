package okestro.mission1.util;

import lombok.Getter;

@Getter
public enum Message {
    /**
     * 리소스를 찾지 못할때
     */
    ERROR_NOT_FOUND_MEMBER_IN_HEADER("헤더에 유저 정보가 존재하지 않습니다."),
    ERROR_NOT_FOUND_MEMBER_IN_DB("DB에 유저 정보가 존재하지 않습니다."),
    ERROR_NOT_FOUND_NETWORK_IN_DB("DB에 네트워크 정보가 존재하지 않습니다."),
    ERROR_NOT_FOUND_VM_IN_DB("DB에 VM 정보가 존재하지 않습니다."),
    ERROR_NOT_EXIST_TAG_IN_DB("DB에 태그 정보가 존재하지 않습니다."),
    ERROR_CONSTRAINT_VIOLATION("예외 객체로부터 메시지를 추출하는데 실패했습니다."),
    ERROR_NOT_FOUND_URI("URI를 확인해주세요."),

    /**
     * 검증에 실패 했을때
     */
    ERROR_PAGE_SIZE("유효하지 않은 페이지 크기입니다. 5, 10, 20으로만 입력해주세요. 입력값 = %d"),
    ERROR_VALIDATE_ARGUMENT("요청 인자 검증에 실패했습니다."),
    ERROR_DUPLICATE_TAG_NAME("중복된 태그 이름입니다."),
    ERROR_BLANK_TAG_NAME("태그 이름이 공백입니다."),
    ERROR_TAG_ID_IS_NULL("태그 id가 null입니다."),
    ERROR_DUPLICATE_VM_NAME("이미 존재한는 VM이름입니다."),
    ERROR_BLANK_NETWORK_ID("네트워크 ID가 비어 있습니다."),

    /**
     * 그 외 서버 오류
     */
    ERROR_WEBSOCKET("웹소켓 메시지 전송 중 오류 발생"),
    ERROR_INTERNAL_SERVER("서버 내부에 문제가 발생했습니다."),

    /**
     * 성공 했을때
     */
    SUCCESS("Success"),
    SUCCESS_VM_CREATE("새로운 VM 생성: 상태 = %s, VM ID: %s"),
    SUCCESS_VM_LISTENER("VM 상태 변경 감지: %s -> %s, VM ID: %d"),
    SUCCESS_WEBSOCKET_CONNECT("서버에 연결되었습니다. userId: %s"),
    SUCCESS_WEBSOCKET_CONNECT_WITH_SESSION("사용자 연결됨 - userId: %s, 세션 ID: %s"),
    SUCCESS_WEBSOCKET_DUPLICATE_SESSION("이미 연결된 사용자 세션이 있습니다. 기존 세션을 종료합니다. UserId: %s"),
    SUCCESS_WEBSOCKET_REPLACE_SESSION("새 연결에 의해 대체됨"),
    SUCCESS_WEBSOCKET_DISCONNET("소켯 연결 끊어짐");


    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(int pageSize) {
        return String.format(message, pageSize);
    }

    public String getMessage(String message) {
        return String.format(message);
    }

    public String getMessage(String message, int id) {
        return String.format(message, id);
    }

    public String getMessage(String message1, String message2) {
        return String.format(message1, message2);
    }

    public String getMessage(String message1, String message2, int id) {
        return String.format(message1, message2, id);
    }
}
