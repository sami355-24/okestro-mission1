package okestro.mission1.annotation.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.annotation.customannotaion.RequestMember;
import okestro.mission1.exception.custom.NotExistException;
import okestro.mission1.repository.MemberRepository;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestUserArgumentResolver implements HandlerMethodArgumentResolver {

    MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(RequestMember.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String userId = request.getHeader("userId");

        if (userId == null || userId.isEmpty()) {
            throw new NotExistException("헤더에 유저 정보가 존재하지 않습니다.");
        }

        int id = Integer.parseInt(userId);
        return memberRepository.findById(id).orElseThrow(() -> new NotExistException("DB에 유저 정보가 존재하지 않습니다."));
    }
}
