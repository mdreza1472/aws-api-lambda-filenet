package com.rezatechie.context;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class StageContextHolder {

    private static final ThreadLocal<Map<String, String>> stageVariables = new ThreadLocal<>();

    public void setStageVariables(Map<String, String> variables) {
        stageVariables.set(variables);
    }

    public Map<String, String> getStageVariables() {
        return stageVariables.get();
    }

    public String get(String key) {
        Map<String, String> vars = stageVariables.get();
        return (vars != null) ? vars.get(key) : null;
    }

    public void clear() {
        stageVariables.remove();
    }
}
