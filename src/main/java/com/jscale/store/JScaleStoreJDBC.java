package com.jscale.store;

import com.jscale.api.JScaleModel;
import com.jscale.api.JScaleQuery;
import java.util.List;

abstract public class JScaleStoreJDBC extends JScaleStore {

	@Override
	public <T> void write(JScaleModel<T> model, T document) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public <T> T read(JScaleModel<T> model, Object id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<Object> read(JScaleQuery query) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
