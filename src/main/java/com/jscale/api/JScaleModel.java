package com.jscale.api;

import com.jscale.annotations.JScaleId;
import com.jscale.annotations.JScaleIndexed;
import com.jscale.exceptions.JScaleStructureException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JScaleModel<T> {
	private Class<T> modelClass;
	private JScaleRepository db;
	private Map<String,JScaleField> fields = new HashMap<String, JScaleField>();
	private JScaleField primaryId;
	private JScaleIdGenerator idGenerator;

	public JScaleModel(Class<T> modelClass) {
		this.modelClass = modelClass;
		readClass();
	}
	protected final void readClass() {
		for(Method m:modelClass.getMethods()) {
			String fieldName = getFieldName(m);
			
			if (fieldName == null) continue;
			
			JScaleField field = fields.get(fieldName);
			Class fieldType = getFieldType(m);
			
			if (field == null) {
				field = new JScaleField(fieldType);
				fields.put(fieldName, field);
			}
			
			if (m.getAnnotation(JScaleIndexed.class) != null) {
				field.setIndexTypes(m.getAnnotation(JScaleIndexed.class).types());
			}
			
			if (isPrimaryId(m)) {
				if (primaryId != null && !primaryId.equals(field)) {
					throw new JScaleStructureException(String.format(
							"2 or more primary ids defined in class: %s",
							modelClass.getName()));
				}
				if (primaryId == null) {
					primaryId = field;
					JScaleId scaleId = m.getAnnotation(JScaleId.class);
					try {
						idGenerator = scaleId.generator().newInstance();
					} catch (Throwable ex) {
						throw new JScaleStructureException(ex);
					}
				}
			}
			
			if (isSetter(m)) {
				field.setSetter(m);
			}
			
			if (isGetter(m)) {
				field.setGetter(m);
			}
		}
	}
	private String getFieldName(Method m) {
		if (isSetter(m) || isGetter(m)) {
			return m.getName().substring(3);
		}
		return null;
	}
	private Class getFieldType(Method m) {
		if (isSetter(m)) {
			return m.getParameterTypes()[0];
		}
		if (isGetter(m)) {
			return m.getReturnType();
		}
		return null;
	}
	private boolean isPrimaryId(Method m) {
		return m.getAnnotation(JScaleId.class) != null;
	}
	private boolean isSetter(Method m) {
		return m.getName().startsWith("set") 
				&& m.getParameterTypes().length == 1;
	}
	private boolean isGetter(Method m) {
		return m.getName().startsWith("get") 
				&& m.getParameterTypes().length == 0;
	}

	protected final void setDb(JScaleRepository db) {
		this.db = db;
	}

	protected final Map<String, JScaleField> getFields() {
		return fields;
	}

	protected final JScaleField getPrimaryId() {
		return primaryId;
	}
	
	
	public final Class<T> getModelClass() {
		return modelClass;
	}
	public final JScaleRepository getDb() {
		return db;
	}

	protected final JScaleIdGenerator getIdGenerator() {
		return idGenerator;
	}
}
