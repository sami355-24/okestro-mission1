package okestro.mission1.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import okestro.mission1.entity.Tag;

import java.util.List;

@Data
@NoArgsConstructor
public class FindAllTagResponse {

    public List<FindTagResponse> tags;

    public FindAllTagResponse(List<Tag> tags) {
        this.tags = tags.stream().map(tag -> new FindTagResponse(tag.getId(), tag.getTitle())).toList();
    }

    @AllArgsConstructor
    private class FindTagResponse {
        @JsonProperty("id")
        private int id;
        @JsonProperty("title")
        private String title;
    }
}
