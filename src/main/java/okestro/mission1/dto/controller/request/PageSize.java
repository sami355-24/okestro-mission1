package okestro.mission1.dto.controller.request;

import okestro.mission1.exception.custom.InvalidDataException;

import static okestro.mission1.util.Message.ERROR_PAGE_SIZE;

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
            default -> throw new InvalidDataException(ERROR_PAGE_SIZE.getMessage(size));
        };
    }

    PageSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}