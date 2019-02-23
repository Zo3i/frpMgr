package com.jeesite.modules.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String id) {
        return (T) applicationContext.getBean(id);
    }

    public static <T> T getBean(Class<T> c) {
        return applicationContext.getBean(c);
    }

    public static final String PROFILE_DEV = "local";
    public static final String PROFILE_TEST = "test";
    public static final String PROFILE_PRODUCE = "produce";

    public static String getActiveProfile() {
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        return activeProfiles != null && activeProfiles.length > 0 ? activeProfiles[0] : null;
    }

    public static boolean isProduce() {
        return PROFILE_PRODUCE.equals(getActiveProfile());
    }

    public static boolean isDevOrTest() {
        String activeProfile = getActiveProfile();
        return PROFILE_DEV.equals(activeProfile) || PROFILE_TEST.equals(activeProfile);
    }

}
