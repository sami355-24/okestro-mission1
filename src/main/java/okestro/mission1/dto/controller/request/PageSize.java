package okestro.mission1.dto.controller.request;

public enum PageSize {
    FIVE(5),
    TEN(10),
    TWENTY(20);

    private final int value;

    PageSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}