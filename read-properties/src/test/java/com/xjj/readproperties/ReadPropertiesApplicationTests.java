package com.xjj.readproperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj.readproperties.config.MyProps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReadPropertiesApplicationTests {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MyProps myProps;

    @Test
    public void propsTest() throws JsonProcessingException {
        System.out.println("simpleProp: " + myProps.getSimpleProp());
        System.out.println("arrayProps: " + objectMapper.writeValueAsString(myProps.getArrayProps()));
        System.out.println("listProp1: " + objectMapper.writeValueAsString(myProps.getListProp1()));
        System.out.println("listProp2: " + objectMapper.writeValueAsString(myProps.getListProp2()));
        System.out.println("mapProps: " + objectMapper.writeValueAsString(myProps.getMapProps()));
    }

}
