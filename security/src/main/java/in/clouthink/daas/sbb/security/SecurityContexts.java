package in.clouthink.daas.sbb.security;

import java.util.ServiceLoader;

/**
 */
public class SecurityContexts {

	private static class SecurityContextHolder {

		static SecurityContext instance;

		static {
			ServiceLoader<SecurityContext> serviceLoader = ServiceLoader.load(SecurityContext.class);
			if (serviceLoader != null) {
				instance = serviceLoader.iterator().next();
			}
		}

	}

	public static SecurityContext getContext() {
		return SecurityContextHolder.instance;
	}

	private SecurityContexts() {

	}

}
