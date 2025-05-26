package okestro.mission1.dto.controller.request;

import okestro.mission1.exception.custom.InvalidDataException;

public enum PageSize {
    FIVE(5),
    TEN(10),
    TWENTY(20);

    private final int value;

    public static PageSize convertToPageSize(int size) {
        return switch (size) {
            case 5 -> PageSize.FIVE;
            case 10 -> PageSize.TEN;
            case 20 -> PageSize.TWENTY;
            default -> throw new InvalidDataException("유효하지 않은 페이지 크기입니다. 5, 10, 20으로만 입력해주세요. 입력값 = " + size);
        };
    }

    PageSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}