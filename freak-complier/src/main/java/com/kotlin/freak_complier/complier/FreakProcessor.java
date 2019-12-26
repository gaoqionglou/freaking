package com.kotlin.freak_complier.complier;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public class FreakProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        final Set<String> types = new LinkedHashSet<>();
        return false;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotation() {
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
//        annotations.add(EntryG)

        return annotations;
    }
}
