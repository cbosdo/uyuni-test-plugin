package com.suse.manager.test;

import org.apache.log4j.Logger;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class TestPlugin extends Plugin {

	private static final Logger LOG = Logger.getLogger(TestPlugin.class);

	public TestPlugin(PluginWrapper wrapperIn) {
		super(wrapperIn);
	}

	@Override
	public void start() {
		LOG.warn("Test plugin started");
	}

	@Override
	public void stop() {
		LOG.warn("Test plugin stopped");
	}
}
