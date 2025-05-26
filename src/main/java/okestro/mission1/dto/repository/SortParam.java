package okestro.mission1.dto.repository;

public enum SortParam {
    NAME_ASC, NAME_DESC,
    CREATED_AT_ASC, CREATED_AT_DESC,
    UPDATED_AT_ASC, UPDATED_AT_DESC;

    public static SortParam from(String param) {
        if (param == null || param.isEmpty()) {
            return CREATED_AT_DESC; // 기본값
        }

        return switch (param.toLowerCase()) {
            case "name-asc" -> NAME_ASC;
            case "name-desc" -> NAME_DESC;
            case "create-at-asc" -> CREATED_AT_ASC;
            case "create-at-desc" -> CREATED_AT_DESC;
            case "update-at-asc" -> UPDATED_AT_ASC;
            case "update-at-desc" -> UPDATED_AT_DESC;
            default -> CREATED_AT_DESC; // 기본값
        };
    }
}
