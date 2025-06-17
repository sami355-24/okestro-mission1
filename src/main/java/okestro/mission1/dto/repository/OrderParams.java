package okestro.mission1.dto.repository;

public enum OrderParams {
    NAME_ASC, NAME_DESC,
    CREATED_AT_ASC, CREATED_AT_DESC,
    UPDATED_AT_ASC, UPDATED_AT_DESC;

    public static OrderParams of(String orderParam) {
        if (orderParam == null || orderParam.isEmpty()) {
            return NAME_ASC;
        }

        return switch (orderParam.toLowerCase()) {
            case "name-asc" -> NAME_ASC;
            case "name-desc" -> NAME_DESC;
            case "create-at-asc" -> CREATED_AT_ASC;
            case "create-at-desc" -> CREATED_AT_DESC;
            case "update-at-asc" -> UPDATED_AT_ASC;
            case "update-at-desc" -> UPDATED_AT_DESC;
            default -> throw new IllegalArgumentException("정렬 기준이 잘못되었습니다. " + orderParam);
        };
    }
}