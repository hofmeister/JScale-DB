package com.jscale.server.protocol;

import com.jscale.api.JScaleQuery;
import java.io.Serializable;

public class JScaleRequest extends JScaleMessage implements Serializable {
	public static class DataQuery extends JScaleRequest {
		private JScaleQuery query;

		public DataQuery(JScaleQuery query) {
			this.query = query;
		}

		public DataQuery(String query,Object ... args) {
			this.query = JScaleQuery.fromString(query,args);
		}

		public JScaleQuery getQuery() {
			return query;
		}	
	}
	public static class AdminCommand extends JScaleRequest {
		private AdminCommandType type;
		private Object[] args;

		public AdminCommand(AdminCommandType type, Object ... args) {
			this.type = type;
			this.args = args;
		}

		public Object[] getArgs() {
			return args;
		}

		public AdminCommandType getType() {
			return type;
		}
		
		public static enum AdminCommandType {
			STOPNODE,STARTNODE
		}
	}
}
