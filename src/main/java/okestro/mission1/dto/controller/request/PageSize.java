package okestro.mission1.dto.controller.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import okestro.mission1.exception.InvalidDataException;

import static okestro.mission1.util.Message.ERROR_PAGE_SIZE;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum PageSize {
    FIVE(5),
    TEN(10),
    TWENTY(20);

    int value;

    public static PageSize of(int size) {
        return switch (size) {
            case 5 -> PageSize.FIVE;
            case 10 -> PageSize.TEN;
            case 20 -> PageSize.TWENTY;
            default -> throw new InvalidDataException(ERROR_PAGE_SIZE.getMessage(size));
        };
    }
}