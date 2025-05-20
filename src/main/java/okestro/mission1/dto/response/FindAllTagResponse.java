package okestro.mission1.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import okestro.mission1.entity.Tag;

import java.util.List;

@Data
@NoArgsConstructor
public class FindAllTagResponse {

    public List<String> tags;

    public FindAllTagResponse(List<Tag> tags) {
        this.tags = tags.stream().map(Tag::getTitle).toList();
    }
}
