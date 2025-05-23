package okestro.mission1.dto.controller.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * okestro.mission1.dto.controller.response.QFindFilterVmResponse is a Querydsl Projection type for FindFilterVmResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QFindFilterVmResponse extends ConstructorExpression<FindFilterVmResponse> {

    private static final long serialVersionUID = -1558009867L;

    public QFindFilterVmResponse(com.querydsl.core.types.Expression<Integer> vmId, com.querydsl.core.types.Expression<String> vmName, com.querydsl.core.types.Expression<? extends java.util.List<String>> tags, com.querydsl.core.types.Expression<String> privateIp) {
        super(FindFilterVmResponse.class, new Class<?>[]{int.class, String.class, java.util.List.class, String.class}, vmId, vmName, tags, privateIp);
    }

}

