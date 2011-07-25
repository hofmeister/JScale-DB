package com.jscale.api;

import com.jscale.annotations.JScaleIndexed.IndexType;
import com.jscale.exceptions.JScaleStructureException;
import java.lang.reflect.Method;

class JScaleField<T> {
	private Class<T> type;
	private Method setter;
	private Method getter;
	private IndexType[] indexTypes;
	
	protected JScaleField(Class<T> type) {
		this.type = type;
	}

	protected IndexType[] getIndexTypes() {
		return indexTypes;
	}

	protected void setIndexTypes(IndexType[] indexTypes) {
		this.indexTypes = indexTypes;
	}

	protected Class<T> getType() {
		return type;
	}
	

	protected Method getGetter() {
		return getter;
	}

	protected void setGetter(Method getter) {
		this.getter = getter;
	}

	protected Method getSetter() {
		return setter;
	}

	protected void setSetter(Method setter) {
		this.setter = setter;
	}
	protected void set(Object document,T value) {
		try {
			setter.invoke(document,value);
		} catch (Throwable ex) {
			throw new JScaleStructureException(ex);
		} 
	}
	protected T get(Object document) {
		try {
			return (T) getter.invoke(document);
		} catch (Throwable ex) {
			throw new JScaleStructureException(ex);
		}
	}
}
