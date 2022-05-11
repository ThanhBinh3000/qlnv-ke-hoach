package com.tcdt.qlnvkhoach.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Messages {
	public static final String BUNDLE_MESSAGE_NAME = "messages";

	@SuppressWarnings("unused")
	private ResourceBundle resourceBundle;

	private static HashMap<String, ResourceBundle> resourceBundles = new HashMap<>();

	private static ResourceBundle getInstance(Locale locale) {
		ResourceBundle resourceBundle = resourceBundles.get(locale.getLanguage());
		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle(BUNDLE_MESSAGE_NAME, locale);
			resourceBundles.put(locale.getLanguage(), resourceBundle);
		}
		return resourceBundle;
	}

	public static String get(final String language, final String key, final String... arguments) {
		if (key == null || key.isEmpty()) {
			return StringUtils.EMPTY;
		}

		String keyTrim = key.trim();

		String lang = StringUtils.lowerCase(language);
		if (StringUtils.isBlank(lang)) {
			lang = "vi";
		}

		Locale locale = org.apache.commons.lang3.LocaleUtils.toLocale(lang);
		ResourceBundle resourceBundle = getInstance(locale);

		try {
			String value = resourceBundle.getString(keyTrim).trim();
			return arguments == null || arguments.length == 0 ? value : MessageFormat.format(value, arguments);
		} catch (MissingResourceException mre) {
			log.error("Message key not found: " + keyTrim);
			return '!' + keyTrim + '!';
		}
	}

	public static String get(final String key, final String... arguments) {
		return get("vi", key, arguments);
	}

	private Messages(String baseName, Locale locale) {
		this.resourceBundle = ResourceBundle.getBundle(baseName, locale);
	}

	public static Messages buildMessages(String baseName, Locale locale) {
		return new Messages(baseName, locale);
	}
}
