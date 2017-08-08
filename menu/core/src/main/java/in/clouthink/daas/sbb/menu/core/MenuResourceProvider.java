package in.clouthink.daas.sbb.menu.core;

import in.clouthink.daas.sbb.rbac.model.DefaultResource;
import in.clouthink.daas.sbb.rbac.model.DefaultResourceChild;
import in.clouthink.daas.sbb.rbac.model.Resource;
import in.clouthink.daas.sbb.rbac.spi.ResourceProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Menu-ResourceProvider impl
 *
 * @author dz
 */
public class MenuResourceProvider implements ResourceProvider, InitializingBean {

	private static final Log logger = LogFactory.getLog(MenuResourceProvider.class);

	static final String PROVIDER_NAME = MenuResourceProvider.class.getName();

	private List<MenuPlugin> menuPluginList = new ArrayList<>();

	private List<Resource> resourceList = new ArrayList<>();

	public MenuResourceProvider() {
	}

	public MenuResourceProvider(List<MenuPlugin> menuPluginList) {
		this.menuPluginList = menuPluginList;
	}

	public void setMenuPluginList(List<MenuPlugin> menuPluginList) {
		this.menuPluginList = menuPluginList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}

	@Override
	public String getName() {
		return PROVIDER_NAME;
	}

	@Override
	public List<Resource> listResources() {
		return resourceList;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String,Menu> menuExtensionMap = menuPluginList.stream()
														  .flatMap(plugin -> plugin.getMenu().stream())
														  .filter(menu -> menu.getExtensionPoint() != null)
														  .collect(Collectors.toMap(menu -> menu.getExtensionPoint()
																								.getId(),
																					menu -> menu));


		menuPluginList.stream().forEach(plugin -> {
			if (StringUtils.isEmpty(plugin.getExtensionPointId())) {
				throw new MenuException(
						"We don't know what the extension point the plugin implements , please specify the extension point id.");
			}
			if (Menus.isExtendFromRoot(plugin)) {
				plugin.getMenu().stream().forEach(menu -> {
					DefaultResource resource = new DefaultResource();
					BeanUtils.copyProperties(menu, resource);
					resourceList.add(resource);
				});
			}
			else {
				plugin.getMenu().stream().forEach(menu -> {
					Menu extendFrom = menuExtensionMap.get(plugin.getExtensionPointId());
					if (extendFrom == null) {
						throw new MenuException(String.format(
								"The menu declared extension point[id=%s] is not found , please make sure the extension point is existed",
								plugin.getExtensionPointId()));
					}

					DefaultResourceChild resource = new DefaultResourceChild();
					BeanUtils.copyProperties(menu, resource);
					resource.setParentCode(extendFrom.getCode());
					resourceList.add(resource);
				});
			}

		});
	}

}
