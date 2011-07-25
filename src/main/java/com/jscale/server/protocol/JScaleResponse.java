package com.jscale.server.protocol;

import java.util.Collection;

abstract public class JScaleResponse extends JScaleMessage {

	abstract public void append(JScaleResponse response);
	
	public static class SingleResult<T> extends JScaleResponse {
		private T row;

		public SingleResult(T row) {
			this.row = row;
		}

		public T getRow() {
			return row;
		}

		@Override
		public void append(JScaleResponse response) {
			throw new UnsupportedOperationException("Cannot append single results.");
		}
		
	}
	public static class RowResult<T> extends JScaleResponse {
		private Collection<T> rows;
		private SortKey[] sortKeys;
		private GroupKey[] groupKeys;

		public RowResult(Collection<T> rows) {
			this(rows, null);
		}
		
		public RowResult(Collection<T> rows, SortKey[] sortKeys) {
			this(rows, sortKeys, null);
		}
		public RowResult(Collection<T> rows, SortKey[] sortKeys, GroupKey[] groupKeys) {
			this.rows = rows;
			this.sortKeys = sortKeys;
			this.groupKeys = groupKeys;
		}

		
		public GroupKey[] getGroupKeys() {
			return groupKeys;
		}
		
		public Collection<T> getRows() {
			return rows;
		}

		public SortKey[] getSortKeys() {
			return sortKeys;
		}

		@Override
		public void append(JScaleResponse response) {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}
	
	public static class GroupKey {
		private String field;

		public GroupKey(String groupField) {
			this.field = groupField;
		}

		public String getField() {
			return field;
		}
	}
	
	public static class SortKey {
		private String field;
		private boolean descending;

		public SortKey(String sortField, boolean descending) {
			this.field = sortField;
			this.descending = descending;
		}

		public boolean isDescending() {
			return descending;
		}

		public String getField() {
			return field;
		}
	}
}
