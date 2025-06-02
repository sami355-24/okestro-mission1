package okestro.mission1.annotation.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.annotation.customannotaion.RequestMember;
import okestro.mission1.exception.NotExistException;
import okestro.mission1.repository.MemberRepository;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static okestro.mission1.util.Message.*;

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
        String userId = request.getHeader("memberId");

        if (userId == null || userId.isEmpty()) {
            throw new NotExistException(ERROR_NOT_FOUND_MEMBER_IN_HEADER.getMessage());
        }

        int id = Integer.parseInt(userId);
        return memberRepository.findById(id).orElseThrow(() -> new NotExistException(ERROR_NOT_FOUND_MEMBER_IN_DB.getMessage()));
    }
}
