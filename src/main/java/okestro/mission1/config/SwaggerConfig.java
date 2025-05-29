package okestro.mission1.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Mission 1 API",
                description = "가상머신 자원 관리 API입니다.")
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Builder
    public GroupedOpenApi sampleOpenApi(){
        String[] paths = {"/vm/**"};
        return GroupedOpenApi.builder()
                .group("vm")
                .pathsToMatch(paths)
                .build();
    }
}
