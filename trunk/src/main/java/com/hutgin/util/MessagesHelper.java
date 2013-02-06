package com.hutgin.util;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author Alexei.Gubanov@gmail.com
 *         Date: 25.11.11
 */
public interface MessagesHelper {

    MessageSource getMessageSource();

    String getMessage(String code, Locale locale);

    String getMessage(String code, String locale);

    String getMessage(String code, String defaultMsg, Locale locale);

    String getMessage(String code, String defaultMsg, String locale);

    String getMessage(String code, Object[] args, Locale locale);

    String getMessage(String code, Object[] args, String locale);

    String getMessage(String code, Object[] args, String defaultMsg, Locale locale);

    String getMessage(String code, Object[] args, String defaultMsg, String locale);
}