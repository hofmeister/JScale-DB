package com.jscale.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class JScaleIdGenerator<T> {
	abstract protected T getIdFor(JScaleModel model);
	
	public static class INCREMENTAL extends JScaleIdGenerator<Integer> {
		private Map<Class,Integer> counters = new HashMap<Class, Integer>();
		
		@Override
		protected Integer getIdFor(JScaleModel model) {
			Integer current = counters.get(model.getModelClass());
			if (current == null)
				current = 1;
			else
				current++;
			counters.put(model.getModelClass(), current);
			return current;
		}
	}
	public static class GUID extends JScaleIdGenerator<String> {
		
		@Override
		protected String getIdFor(JScaleModel model) {
			return UUID.randomUUID().toString();
		}
	}
	public static class NONE extends JScaleIdGenerator<String> {
		
		@Override
		protected String getIdFor(JScaleModel model) {
			throw new UnsupportedOperationException(String.format(
					"%s has id generator = NONE",
					model.getModelClass().getName()));
		}
	}
}
