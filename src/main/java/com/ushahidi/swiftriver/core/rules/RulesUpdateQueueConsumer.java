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
package com.ushahidi.swiftriver.core.rules;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.util.ErrorHandler;

import com.rabbitmq.client.Channel;
import com.ushahidi.swiftriver.core.model.Rule;

/**
 * This class listens for rule(add/delete/update) messages sent on the 
 * RULES_UPDATE_QUEUE and propagates the updates to the {@link RulesRegistry}
 * object
 *  
 * @author ekala
 *
 */
public class RulesUpdateQueueConsumer implements ChannelAwareMessageListener, ErrorHandler {
	
	private ConcurrentMap<Long, List<Object>> dropRulesMap;
	
	private RulesRegistry rulesRegistry;
	
	private ObjectMapper objectMapper;
	
	static final Logger logger = LoggerFactory.getLogger(RulesUpdateQueueConsumer.class);

	public ConcurrentMap<Long, List<Object>> getDropRulesMap() {
		return dropRulesMap;
	}

	public void setDropRulesMap(ConcurrentMap<Long, List<Object>> dropRulesMap) {
		this.dropRulesMap = dropRulesMap;
	}

	public RulesRegistry getRulesRegistry() {
		return rulesRegistry;
	}

	public void setRulesRegistry(RulesRegistry rulesRegistry) {
		this.rulesRegistry = rulesRegistry;
	}
	
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.amqp.core.MessageListener#onMessage(org.springframework.amqp.core.Message)
	 */
	public synchronized void onMessage(Message message, Channel channel) throws Exception, 
		JsonParseException, JsonMappingException, IOException {

		// Get the routing key
		String routingKey = message.getMessageProperties().getReceivedRoutingKey();

		// Deserialize the JSON message
		Rule rule = objectMapper.readValue(new String(message.getBody()), Rule.class);
		
		if (routingKey.equals("web.river.rules.add")) {
			rulesRegistry.addRule(rule);
		} else if (routingKey.equals("web.river.rules.delete")) {
			rulesRegistry.deleteRule(rule);
		} else if (routingKey.equals("web.river.rules.update")) {
			rulesRegistry.updateRule(rule);
		}
	}

	public void handleError(Throwable t) {
		logger.error("An error occurred during rules processing", t);
	}

}
