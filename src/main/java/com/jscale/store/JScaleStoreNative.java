package com.jscale.store;

import com.jscale.api.JScaleModel;
import com.jscale.api.JScaleQuery;
import java.io.File;
import java.util.List;

public class JScaleStoreNative extends JScaleStore {
	private File path;

	public JScaleStoreNative(File path) {
		this.path = path;
	}

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
