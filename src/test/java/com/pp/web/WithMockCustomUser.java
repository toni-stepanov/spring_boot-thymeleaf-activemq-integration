package com.pp.web;

import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * Created by astepanov
 */
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "test";

    String name() default "Rob Winch";
}