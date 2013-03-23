/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/agpl.html>
 * 
 * Copyright (C) Ushahidi Inc. All Rights Reserved.
 */
package com.ushahidi.swiftriver.core.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Rule POJO
 * 
 * @author ekala
 */
public class Rule {

	private long id;
	
	@JsonProperty("river_id")
	private long riverId;
	
	private String name;
		
	private List<RuleCondition> conditions;
	
	private List<RuleAction> actions;

	@JsonProperty("all_conditions")
	private boolean matchAllConditions;

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getRiverId() {
		return riverId;
	}

	public void setRiverId(Long riverId) {
		this.riverId = riverId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RuleCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<RuleCondition> conditions) {
		this.conditions = conditions;
	}

	public List<RuleAction> getActions() {
		return actions;
	}

	public void setActions(List<RuleAction> actions) {
		this.actions = actions;
	}
	

	public boolean isMatchAllConditions() {
		return matchAllConditions;
	}

	public void setMatchAllConditions(boolean matchAllConditions) {
		this.matchAllConditions = matchAllConditions;
	}


	public static class RuleCondition {
		private String field;
		
		private String operator;
		
		private String value;

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	public static class RuleAction {
		
		private boolean markAsRead;
		
		private boolean removeFromRiver;
		
		private Long addToBucket;

		public boolean isMarkAsRead() {
			return markAsRead;
		}

		public void setMarkAsRead(boolean markAsRead) {
			this.markAsRead = markAsRead;
		}

		public boolean isRemoveFromRiver() {
			return removeFromRiver;
		}

		public void setRemoveFromRiver(boolean removeFromRiver) {
			this.removeFromRiver = removeFromRiver;
		}

		public Long getAddToBucket() {
			return addToBucket;
		}

		public void setAddToBucket(Long addToBucket) {
			this.addToBucket = addToBucket;
		}
		
		
	}
	
}
