package com.spencer.chang.util;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * 判断java运行时版本，来源netty https://github.com/netty/netty
 * PlatformDependent.java
 *
 */
public class PlatformJavaVersion {
	private static final boolean IS_ANDROID = isAndroid0();
	private static final int JAVA_VERSION = javaVersion0();

	static ClassLoader getSystemClassLoader() {
		if (System.getSecurityManager() == null) {
			return ClassLoader.getSystemClassLoader();
		} else {
			return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
				@Override
				public ClassLoader run() {
					return ClassLoader.getSystemClassLoader();
				}
			});
		}
	}

	private static boolean isAndroid0() {
		boolean android;
		try {
			Class.forName("android.app.Application", false, getSystemClassLoader());
			android = true;
		} catch (Throwable ignored) {
			// Failed to load the class uniquely available in Android.
			android = false;
		}

		if (android) {
			System.out.println("Platform: Android");
		}
		return android;
	}

	/**
	 * Returns {@code true} if and only if the current platform is Android
	 */
	public static boolean isAndroid() {
		return IS_ANDROID;
	}

	/**
	 * Return the version of Java under which this library is used.
	 */
	public static int javaVersion() {
		return JAVA_VERSION;
	}

	private static int javaVersion0() {
		final int majorVersion;

		if (isAndroid()) {
			majorVersion = 6;
		} else {
			majorVersion = majorVersionFromJavaSpecificationVersion();
		}

		System.out.println("Java version: " + majorVersion);

		return majorVersion;
	}

	static int majorVersionFromJavaSpecificationVersion() {
		return majorVersion(SystemPropertyUtil.get("java.specification.version", "1.6"));
	}

	static int majorVersion(final String javaSpecVersion) {
		final String[] components = javaSpecVersion.split("\\.");
		final int[] version = new int[components.length];
		for (int i = 0; i < components.length; i++) {
			version[i] = Integer.parseInt(components[i]);
		}

		if (version[0] == 1) {
			assert version[1] >= 6;
			return version[1];
		} else {
			return version[0];
		}
	}
}
