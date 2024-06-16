package com.modernframework.core.utils;

import com.modernframework.core.func.Predicates;
import com.modernframework.core.func.Streams;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AnnotationUtils {

    public static boolean contains(Collection<Annotation> annotations, Class<? extends Annotation> annotationType) {
        if (annotations == null || annotations.isEmpty()) {
            return false;
        }
        boolean contained = false;
        for (Annotation annotation : annotations) {
            if (Objects.equals(annotationType, annotation.annotationType())) {
                contained = true;
                break;
            }
        }
        return contained;
    }

    static boolean isSameType(Annotation annotation, Class<? extends Annotation> annotationType) {
        if (annotation == null || annotationType == null) {
            return false;
        }
        return Objects.equals(annotation.annotationType(), annotationType);
    }

    public static List<Annotation> getDeclaredAnnotations(AnnotatedElement annotatedElement,
                                                          Predicate<Annotation>... annotationsToFilter) {
        if (annotatedElement == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(annotatedElement.getAnnotations())
                .filter(Predicates.and(annotationsToFilter))
                .collect(Collectors.toList());
    }

    public static Set<Class<?>> getAllInheritedTypes(Class<?> type, Predicate<Class<?>>... typeFilters) {
        // Add all super classes
        Set<Class<?>> types = new LinkedHashSet<>(ClassUtils.getAllSuperClasses(type, typeFilters));
        // Add all interface classes
        types.addAll(ClassUtils.getAllInterfaces(type, typeFilters));
        return Collections.unmodifiableSet(types);
    }

    public static List<Annotation> getAllDeclaredAnnotations(Class<?> type, Predicate<Annotation>... annotationsToFilter) {

        if (type == null) {
            return Collections.emptyList();
        }
        List<Annotation> allAnnotations = new LinkedList<>();
        Set<Class<?>> allTypes = new LinkedHashSet<>();
        allTypes.add(type);
        allTypes.addAll(getAllInheritedTypes(type, t -> !Object.class.equals(t)));
        for (Class<?> t : allTypes) {
            allAnnotations.addAll(getDeclaredAnnotations(t));
        }
        return allAnnotations.stream()
                .filter(Predicates.and(annotationsToFilter))
                .collect(Collectors.toList());
    }

    public static List<Annotation> getAllDeclaredAnnotations(AnnotatedElement annotatedElement,
                                                             Predicate<Annotation>... annotationsToFilter) {
        if (annotatedElement instanceof Class) {
            return getAllDeclaredAnnotations((Class) annotatedElement, annotationsToFilter);
        } else {
            return getDeclaredAnnotations(annotatedElement, annotationsToFilter);
        }
    }

    public static <A extends Annotation> A findAnnotation(AnnotatedElement annotatedElement, Class<A> annotationType) {
        return findAnnotation(annotatedElement, a -> isSameType(a, annotationType));
    }

    public static <A extends Annotation> A findAnnotation(AnnotatedElement annotatedElement,
                                                          Predicate<Annotation>... annotationFilters) {
        return (A) Streams.filterFirst(getAllDeclaredAnnotations(annotatedElement), annotationFilters);
    }

    public static boolean hasAnnotation(AnnotatedElement annotationEle, Class<? extends Annotation> annotationType) {
        return null != findAnnotation(annotationEle, annotationType);
    }

}
