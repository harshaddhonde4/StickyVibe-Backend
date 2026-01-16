package com.eazybytes.StickyVibe.scopes;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
@Setter
@Slf4j
public class RequestScopedBean
{
    private String UserName;
    public RequestScopedBean()
    {
        log.info("RequestScopedBean is created");
    }
}
