package okestro.mission1.socket;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.security.Principal;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleUserPrincipal implements Principal {
    String name;

    @Override
    public String getName() {
        return name;
    }
}