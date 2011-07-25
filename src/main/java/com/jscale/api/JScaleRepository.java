package com.jscale.api;

import com.jscale.exceptions.JScaleQueryException;
import com.jscale.server.JScaleCluster;
import com.jscale.server.protocol.JScaleRequest.DataQuery;
import com.jscale.server.protocol.JScaleResponse;
import com.jscale.server.protocol.JScaleResponse.RowResult;
import com.jscale.server.protocol.JScaleResponse.SingleResult;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class JScaleRepository implements Serializable {
	private JScaleCluster cluster;
	private String name;
	private Set<JScaleModel<?>> models = new HashSet<JScaleModel<?>>();
	private Map<Class<?>,JScaleModel<?>> modelClassMap = new HashMap<Class<?>, JScaleModel<?>>();

	public JScaleRepository(JScaleCluster cluster, String name) {
		this.cluster = cluster;
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	protected final Set<JScaleModel<?>> getModels() {
		return models;
	}

	public final <T extends JScaleModel<?>> T addModel(T model) {
		if (models.add(model)) {
			model.setDb(this);
			modelClassMap.put(model.getModelClass(), model);
			return model;
		}
		return null;
	}
	protected final <T> JScaleModel<T> getModel(Class<T> modelClass) {
		return (JScaleModel<T>) modelClassMap.get(modelClass);
	}
	protected final <T> JScaleModel<T> getModel(Object document) {
		return (JScaleModel<T>) modelClassMap.get(document.getClass());
	}
	
	public final <T> JScaleModel<T> addModel(Class<T> modelClass) {
		return addModel(new JScaleModel(modelClass));
	}

	public void insert(Object document) {
		JScaleModel<Object> model = getModel(document);
		Object id = model.getPrimaryId().get(document);
		if (id == null)
			id = model.getIdGenerator().getIdFor(model);
		
	}

	public <T> T getDocument(Class<T> modelClass,Object id) {
		JScaleModel<T> model = getModel(modelClass);
		return null;
	}
	public <T> long count(Class<T> modelClass) {
		return 0;
	}

	public void update(Object document) {
		JScaleModel<Object> model = getModel(document);
		Object id = model.getPrimaryId().get(document);
		if (id == null)
			throw new JScaleQueryException(String.format(
					"Cannot update %s without id: %s",
					document.getClass().getName(),document));
	}

	public Object fetchRow(String query,Object ... args) {
		return fetchRow(Object.class,query, args);
	}
	public <T> T fetchRow(Class<T> modelClass,String query,Object ... args) {
		SingleResult<T> request = cluster.request(new DataQuery(query,args),JScaleResponse.SingleResult.class);
		return request.getRow();
	}
	public Collection<Object> fetchRows(String query,Object ... args) {
		return fetchRows(Object.class,query, args);
	}
	public <T> Collection<T> fetchRows(Class<T> modelClass,String query,Object ... args) {
		RowResult<T> request = cluster.request(new DataQuery(query,args),JScaleResponse.RowResult.class);
		return request.getRows();
	}
	

}
