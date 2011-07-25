package com.jscale.store;

import com.jscale.api.JScaleModel;
import com.jscale.api.JScaleQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract public class JScaleStore {
	abstract public <T> void write(JScaleModel<T> model,T document);
	abstract public <T> T read(JScaleModel<T> model,Object id);
	abstract public List<Object> read(JScaleQuery query);
	
}
