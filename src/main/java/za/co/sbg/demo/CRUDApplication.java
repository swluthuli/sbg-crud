package za.co.sbg.demo;

import za.co.sbg.demo.controller.AuthController;
import za.co.sbg.demo.controller.ProductController;
import za.co.sbg.demo.controller.UserController;
import za.co.sbg.demo.handler.CustomExceptionMapper;
import za.co.sbg.demo.security.AuthFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class CRUDApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(AuthController.class);
        classes.add(AuthFilter.class);
        classes.add(ProductController.class);
        classes.add(UserController.class);
        classes.add(CustomExceptionMapper.class);
        return classes;
    }
}