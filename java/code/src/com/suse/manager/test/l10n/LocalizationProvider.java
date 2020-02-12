package com.suse.manager.test.l10n;

import com.suse.manager.plugin.LocalizationExtensionPoint;

import org.pf4j.Extension;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Provides localization messages as an extension
 */
@Extension
public class LocalizationProvider implements LocalizationExtensionPoint {

	private final static String BUNDLE_NAME = "com.suse.manager.test.l10n.Messages";

	@Override
	public String getMessage(String messageId, Locale locale, Object... args) throws MissingResourceException {
		ResourceBundle bundle = getBundle(locale);
		String message = bundle.getString(messageId);
		return String.format(message, args);
	}

	@Override
	public Enumeration<String> getKeys() {
		ResourceBundle bundle = getBundle(Locale.US);
		return bundle.getKeys();
	}

	private ResourceBundle getBundle(Locale locale) {
		return ResourceBundle.getBundle(BUNDLE_NAME, locale, LocalizationProvider.class.getClassLoader());
	}
}
