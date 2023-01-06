package br.com.wallet.util;

import br.com.wallet.exceptions.RetryExhaustedException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;

@Slf4j
@UtilityClass
public class RetryBackoffUtil {

    private static final Duration FIXED_DELAY_IN_SECONDS = Duration.ofSeconds(3);
    private static final int MAX_ATTEMPTS = 3;
    private static final double JITTER_FACTOR = 0.75;

    public static RetryBackoffSpec getRetryBackoffSpecification() {
        return Retry.fixedDelay(RetryBackoffUtil.MAX_ATTEMPTS, RetryBackoffUtil.FIXED_DELAY_IN_SECONDS)
                .jitter(RetryBackoffUtil.JITTER_FACTOR)
                .filter(RetryBackoffUtil::allowRetry)
                .doBeforeRetry(retrySignal -> logFormattedMessageError(retrySignal.totalRetries(), retrySignal.failure()))
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> getThrowRetryExhaustedException(retrySignal.totalRetries(), retrySignal.failure()));
    }

    private static boolean allowRetry(final Throwable ex) {
        return isEqualToBadGatewayExceptionFilter(ex)
                || isEqualToServiceUnavailableExceptionFilter(ex)
                || isEqualToGatewayTimeoutExceptionFilter(ex);
    }

    private static boolean isEqualToBadGatewayExceptionFilter(final Throwable ex) {
        return ex instanceof WebClientResponseException.BadGateway;
    }

    private static boolean isEqualToServiceUnavailableExceptionFilter(final Throwable ex) {
        return ex instanceof WebClientResponseException.ServiceUnavailable;
    }

    private static boolean isEqualToGatewayTimeoutExceptionFilter(final Throwable ex) {
        return ex instanceof WebClientResponseException.GatewayTimeout;
    }

    private static void logFormattedMessageError(final long totalRetries, final Throwable failure) {
        String message = String.format("Retrying: %s/%s", totalRetries + 1, MAX_ATTEMPTS);
        log.error(message, failure);
    }

    private static Throwable getThrowRetryExhaustedException(final long totalRetries, final Throwable failure) {
        String message = String.format("Retry exhausted: %s", totalRetries);
        return new RetryExhaustedException(message, failure);
    }

}
