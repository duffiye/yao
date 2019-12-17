package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.pojo.R;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author y3tu
 * @date 2019-05-09
 */
@Slf4j
@Component
public class GateWayExceptionHandlerAdvice {


    public R handle(ConnectTimeoutException ex) {
        log.error("connect timeout exception:{}", ex.getMessage());
        return R.error("GATEWAY-ERROR-002", "连接超时");
    }

    public R handle(NotFoundException ex) {
        log.error("not found exception:{}", ex.getMessage());
        return R.error("404", ex.getMessage());
    }

    public R handle(RuntimeException ex) {
        log.error("runtime exception:{}", ex.getMessage());
        return R.error();
    }

    public R handle(NoPermissionException ex) {
        log.error("NoPermissionException exception:{}", ex.getMessage());
        return R.error("403", ex.getMessage());
    }


    public R handle(BaseException ex) {
        log.error("BaseException exception:{}", ex.getMessage());
        return R.error(ex.getCode(), ex.getMessage());
    }

    public R handle(Exception ex) {
        log.error("exception:{}", ex.getMessage());
        return R.error();
    }

    public Mono<ServerResponse> handle(Throwable throwable) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        R result = R.error();

        if (throwable instanceof ConnectTimeoutException) {
            status = HttpStatus.GATEWAY_TIMEOUT.value();
            result = handle((ConnectTimeoutException) throwable);
        } else if (throwable instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND.value();
            result = handle((NotFoundException) throwable);
        } else if (throwable instanceof NoPermissionException) {
            status = HttpStatus.FORBIDDEN.value();
            result = handle((NoPermissionException) throwable);
        } else if (throwable instanceof BaseException) {
            status = HttpStatus.OK.value();
            result = handle((BaseException) throwable);
        } else if (throwable instanceof RuntimeException) {
            result = handle((RuntimeException) throwable);
        } else if (throwable instanceof Exception) {
            result = handle((Exception) throwable);
        }

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(result));
    }
}
