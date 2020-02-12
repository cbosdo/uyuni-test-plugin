package com.suse.manager.test;

import com.suse.manager.plugin.MenuExtensionPoint;
import com.suse.manager.webui.menu.MenuItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuProvider implements MenuExtensionPoint {

	@Override
	public Map<String, List<MenuItem>> getMenuItems(Map<String, Boolean> adminRoles, boolean authenticated) {
		Map<String, List<MenuItem>> items = new HashMap<>();

		items.put("External Links", Arrays.asList(
				new MenuItem("test.test_plugin").withPrimaryUrl("https://github.com/cbosdo/uyuni-test-plugin")));

		if (authenticated) {
			items.put("", Arrays.asList(
					new MenuItem("test.hackweek").withIcon("spacewalk-icon-virtual-guest")
						.addChild(new MenuItem("test.site").withPrimaryUrl("https://hackweek.suse.com"))));
		}

		return items;
	}
}
