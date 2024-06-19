package com.licenta.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonHelper {

    public static String createEmptyJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        try {
            return mapper.writeValueAsString(rootNode);
        } catch (Exception e) {
            return "";
        }
    }
}
