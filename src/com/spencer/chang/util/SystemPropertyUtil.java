package com.spencer.chang.util;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * 判断java运行时版本，来源netty https://github.com/netty/netty
 * SystemPropertyUtil.java
 *
 */
public class SystemPropertyUtil {
	/**
	 * Returns the value of the Java system property with the specified
	 * {@code key}, while falling back to the specified default value if the
	 * property access fails.
	 *
	 * @return the property value. {@code def} if there's no such property or if
	 *         an access to the specified property is not allowed.
	 */
	public static String get(final String key, String def) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		if (key.isEmpty()) {
			throw new IllegalArgumentException("key must not be empty.");
		}

		String value = null;
		try {
			if (System.getSecurityManager() == null) {
				value = System.getProperty(key);
			} else {
				value = AccessController.doPrivileged(new PrivilegedAction<String>() {
					@Override
					public String run() {
						return System.getProperty(key);
					}
				});
			}
		} catch (SecurityException e) {
			throw new SecurityException(
					"Unable to retrieve a system property '{}'; default values will be used." + key);
		}

		if (value == null) {
			return def;
		}

		return value;
	}
}
