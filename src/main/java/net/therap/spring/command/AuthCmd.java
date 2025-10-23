package net.therap.spring.command;

import net.therap.spring.entity.AuthenticationInfo;

import javax.validation.Valid;

/**
 * @author shoebakib
 * @since 5/2/24
 */
public class AuthCmd extends Command {

    private static final long serialVersionUID = 1L;

    @Valid
    private AuthenticationInfo authenticationInfo;

    public AuthenticationInfo getAuthenticationInfo() {
        return authenticationInfo;
    }

    public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

}
