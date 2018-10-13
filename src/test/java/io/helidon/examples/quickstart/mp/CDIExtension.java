package io.helidon.examples.quickstart.mp;

import static java.util.Arrays.asList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.inject.Qualifier;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public class CDIExtension implements TestInstancePostProcessor {

   

    private static final Predicate<Annotation> IS_QUALIFIER = a -> a.annotationType().isAnnotationPresent(Qualifier.class);

    @Override

    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws IllegalAccessException {

    	 final SeContainer CONTAINER = (SeContainer)CDI.current();
        for (Field field : getFields(testInstance.getClass())) {

            if (field.getAnnotation(Inject.class) != null) {

                Annotation[] qualifiers = Stream.of(field.getAnnotations())

                        .filter(IS_QUALIFIER)

                        .toArray(Annotation[]::new);

                Object injected = CONTAINER.select(field.getType(), qualifiers).get();

                field.setAccessible(true);

                field.set(testInstance, injected);

            }

        }

    }

    private List<Field> getFields(Class<?> clazzInstance) {

        List<Field> fields = new ArrayList<>();

        if (!clazzInstance.getSuperclass().equals(Object.class)) {

            fields.addAll(getFields(clazzInstance.getSuperclass()));

        } else {

            fields.addAll(asList(clazzInstance.getDeclaredFields()));

        }

        return fields;

    }

}