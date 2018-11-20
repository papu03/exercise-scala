package it.maior.icaroxt



import org.springframework.http.{HttpMethod, HttpStatus}
import org.springframework.stereotype.Component
import org.springframework.web.server.{ServerWebExchange, WebFilter, WebFilterChain}
import reactor.core.publisher.Mono

@Component
class CorsFilter extends WebFilter {
  override def filter(serverWebExchange: ServerWebExchange, webFilterChain: WebFilterChain): Mono[Void] = { // Adapted from https://sandstorm.de/de/blog/post/cors-headers-for-spring-boot-kotlin-webflux-reactor-project.html
    serverWebExchange.getResponse.getHeaders.add("Access-Control-Allow-Origin", "*")
    serverWebExchange.getResponse.getHeaders.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS")
    serverWebExchange.getResponse.getHeaders.add("Access-Control-Allow-Headers", "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range")
    if (serverWebExchange.getRequest.getMethod eq HttpMethod.OPTIONS) {
      serverWebExchange.getResponse.getHeaders.add("Access-Control-Max-Age", "1728000")
      serverWebExchange.getResponse.setStatusCode(HttpStatus.NO_CONTENT)
      Mono.empty()
    }
    else {
      serverWebExchange.getResponse.getHeaders.add("Access-Control-Expose-Headers", "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range")
      webFilterChain.filter(serverWebExchange)
    }
  }
}
