package com.jeesite.modules.common.utils;

import com.eclipsesource.v8.V8;

public class ExcuteScriptUtil {

    public String executeAnswer(String userAnswer, String question) {
        V8 runtime = V8.createV8Runtime();
        userAnswer +=  question;
        Object executeAnswer = runtime.executeScript(userAnswer);
        System.out.println(executeAnswer);
        runtime.release();
        return executeAnswer.toString();
    }
}
