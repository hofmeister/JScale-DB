package com.jscale.annotations;

import com.jscale.api.JScaleIdGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JScaleId {
	public Class<? extends JScaleIdGenerator> generator() default JScaleIdGenerator.INCREMENTAL.class;
}
