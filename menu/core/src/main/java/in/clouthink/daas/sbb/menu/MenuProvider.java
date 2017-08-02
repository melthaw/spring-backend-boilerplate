package in.clouthink.daas.sbb.menu;

import in.clouthink.daas.sbb.rbac.model.Resource;
import in.clouthink.daas.sbb.rbac.spi.ResourceProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dz
 */
public class MenuProvider implements ResourceProvider, InitializingBean {

	static final String PROVIDER_NAME = MenuProvider.class.getName();

	@Autowired(required = false)
	private List<MenuPlugin> menuPluginList = new ArrayList<>();

	private List<Resource> resourceList = new ArrayList<>();

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
		List<Menu> rootMenuList = menuPluginList.stream()
												.filter(Menus::isExtendFromRoot)
												.map(plugin -> plugin.getMenu())
												.sorted(Menus.MENU_SORTER)
												.collect(Collectors.toList());

	}

}
