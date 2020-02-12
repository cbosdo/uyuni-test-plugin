package com.suse.manager.test;

import com.redhat.rhn.frontend.nav.NavNode;

import com.suse.manager.plugin.NavMenuExtensionPoint;

import org.pf4j.Extension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Extension
public class NavMenuProvider implements NavMenuExtensionPoint {

	@Override
	public Map<String, List<NavNode>> getNodes(String menu) {
		Map<String, List<NavNode>> nodes = new HashMap<>();
		if (menu.equals("system_detail")) {
			NavNode testNode = new NavNode();
			testNode.setName("test.nav_title");
			testNode.addPrimaryURL("/rhn/manager/systems/test/${sid}");
			nodes.put("Virtualization", Arrays.asList(testNode));
		}
		return nodes;
	}

}
