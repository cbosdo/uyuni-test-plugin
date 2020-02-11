package com.suse.manager.test;

import static com.suse.manager.webui.utils.SparkApplicationHelper.withCsrfToken;
import static com.suse.manager.webui.utils.SparkApplicationHelper.withUser;
import static com.suse.manager.webui.utils.SparkApplicationHelper.withUserPreferences;
import static spark.Spark.get;

import com.redhat.rhn.common.hibernate.LookupException;
import com.redhat.rhn.domain.server.Server;
import com.redhat.rhn.domain.user.User;
import com.redhat.rhn.manager.rhnset.RhnSetDecl;
import com.redhat.rhn.manager.system.SystemManager;

import com.suse.manager.plugin.WebExtensionPoint;
import com.suse.manager.webui.errors.NotFoundException;

import org.apache.log4j.Logger;
import org.pf4j.Extension;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.jade.JadeTemplateEngine;

@Extension
public class TestController implements WebExtensionPoint{

	private static final Logger LOG = Logger.getLogger(TestController.class);
	private static final String TEMPLATE_ROOT = "com/suse/manager/test/templates/";

	@Override
	public void addRoutes(JadeTemplateEngine jade) {
		LOG.debug("Adding test routes");
		get("/manager/systems/test/:sid",
                withUserPreferences(withCsrfToken(withUser(TestController::show))), jade);
	}


	@Override
	public Reader getTemplate(String name, String encoding) throws IOException {
		InputStream stream = TestController.class.getClassLoader().getResourceAsStream(TEMPLATE_ROOT + name);
		Reader reader = null;
		if (stream != null) {
			reader = new InputStreamReader(stream, encoding);
		}
		return reader;
	}

	/**
     * Displays the virtual storages page.
     *
     * @param request the request
     * @param response the response
     * @param user the user
     * @return the ModelAndView object to render the page
     */
    public static ModelAndView show(Request request, Response response, User user) {
    	Map<String, Object> data = new HashMap<>();

    	Long serverId;
        Server server;

        try {
            serverId = Long.parseLong(request.params("sid"));
        }
        catch (NumberFormatException e) {
            throw new NotFoundException();
        }

        try {
            server = SystemManager.lookupByIdAndUser(serverId, user);
        }
        catch (LookupException e) {
            throw new NotFoundException();
        }

        /* For system-common.jade */
        data.put("server", server);
        data.put("inSSM", RhnSetDecl.SYSTEMS.get(user).contains(serverId));

        return new ModelAndView(data, "show.jade");
    }
}
