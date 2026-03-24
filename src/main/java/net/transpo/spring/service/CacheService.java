package net.transpo.spring.service;

/**
 * @author shoebakib
 * @since 11/01/25
 */

public interface CacheService {

    public void addMessage(String user,String message);

    public List<String> listMessages(String user);

}