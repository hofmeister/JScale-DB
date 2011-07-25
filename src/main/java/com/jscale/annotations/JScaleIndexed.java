package com.jscale.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JScaleIndexed {
	public IndexType[] types() default {IndexType.KEY};
	
	public static enum IndexType {
		KEY,FULLTEXT,RANGE
	}
}
