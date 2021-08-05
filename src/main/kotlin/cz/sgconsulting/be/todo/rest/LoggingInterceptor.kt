package cz.sgconsulting.be.todo.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpRequest
import org.springframework.http.client.*
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import org.springframework.web.client.RestTemplate
import java.nio.charset.Charset


@Configuration
class RestTemplateLogConfiguration {

    @Bean
    fun logRestTemplate(loggingInterceptor: LoggingInterceptor): RestTemplate {
        val restTemplate = RestTemplate(BufferingClientHttpRequestFactory(SimpleClientHttpRequestFactory()))
        restTemplate.interceptors = listOf(loggingInterceptor)
        restTemplate.messageConverters = listOf(ByteArrayHttpMessageConverter(), StringHttpMessageConverter())
        return restTemplate
    }
}

@Component
class LoggingInterceptor : ClientHttpRequestInterceptor {
    val log: Logger = LoggerFactory.getLogger(LoggingInterceptor::class.java)

    @Override
    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        logRequest(request, body)
        val response = execution.execute(request, body)
        logResponse(response)
        return response
    }

    fun logRequest(request: HttpRequest, body: ByteArray) {

        if (log.isDebugEnabled) {
            log.debug("===========================request begin================================================");
            log.debug("URI         :  ${request.uri}")
            log.debug("Method      : {}", request.method)
            log.debug("Headers     : {}", request.headers)
            log.debug("Request body: {}", String(body, Charset.forName("UTF-8")))
            log.debug("==========================request end================================================");
        }
    }

    fun logResponse(response: ClientHttpResponse) {

        if (log.isDebugEnabled) {
            log.debug("============================response begin==========================================");
            log.debug("Status code  : {}", response.statusCode);
            log.debug("Status text  : {}", response.statusText);
            log.debug("Headers      : {}", response.headers);
            log.debug("Response body: {}", StreamUtils.copyToString(response.body, Charset.defaultCharset()));
            log.debug("=======================response end=================================================");
        }
    }
}
