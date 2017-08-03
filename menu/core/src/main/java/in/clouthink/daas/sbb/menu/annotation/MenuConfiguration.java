package in.clouthink.daas.sbb.menu.annotation;

import in.clouthink.daas.sbb.menu.core.DefaultMenuPlugin;
import in.clouthink.daas.sbb.menu.core.MenuExtensionPoint;
import in.clouthink.daas.sbb.menu.core.MenuPlugin;
import in.clouthink.daas.sbb.rbac.model.Action;
import in.clouthink.daas.sbb.rbac.model.DefaultAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dz
 */
@Configuration
public class MenuConfiguration implements ImportAware {

	protected AnnotationAttributes enableMenu;

	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.enableMenu = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableMenu.class.getName(),
																							  false));
		Assert.notNull(this.enableMenu,
					   "@EnableMenu is not present on importing class " + importMetadata.getClassName());
	}

	@Bean
	public MenuPlugin menuPlugin() {
		DefaultMenuPlugin menuPlugin = new DefaultMenuPlugin();
		menuPlugin.setExtensionPointId(enableMenu.getString("extensionPointId"));
		menuPlugin.setPluginId(enableMenu.getString("pluginId"));
		menuPlugin.setMenu(toMenu(enableMenu.getAnnotationArray("menu")));
		return menuPlugin;
	}

	private List<in.clouthink.daas.sbb.menu.core.Menu> toMenu(AnnotationAttributes[] menu) {
		return Stream.of(menu).map(item -> toMenu(item)).collect(Collectors.toList());
	}

	private in.clouthink.daas.sbb.menu.core.Menu toMenu(AnnotationAttributes menu) {
		in.clouthink.daas.sbb.menu.core.Menu result = new in.clouthink.daas.sbb.menu.core.Menu();
		result.setVirtual(menu.getBoolean("virtual"));
		result.setOpen(menu.getBoolean("open"));
		result.setCode(menu.getString("code"));
		result.setName(menu.getString("name"));
		result.setOrder(menu.getNumber("order"));
		result.setPatterns(Arrays.asList(menu.getStringArray("patterns")));
		result.setActions(toAction(menu.getAnnotationArray("actions")));
		result.setMetadata(toMetadata(menu.getAnnotationArray("metadata")));
		result.setExtensionPoint(toExtensionPoint(menu.getAnnotationArray("extensionPoint")));
		return result;
	}

	private List<Action> toAction(AnnotationAttributes[] actions) {
		return Stream.of(actions).map(item -> toAction(item)).collect(Collectors.toList());
	}

	private Action toAction(AnnotationAttributes action) {
		DefaultAction result = new DefaultAction();
		result.setCode(action.getString("code"));
		result.setName(action.getString("name"));
		return result;
	}

	private Map<String,Object> toMetadata(AnnotationAttributes[] metadatas) {
		if (metadatas == null) {
			return new HashMap<>();
		}

		return Stream.of(metadatas).collect(Collectors.toMap(item -> item.getString("key"), item -> {
			Metadata.ValueType valueType = item.getEnum("type");
			switch (valueType) {
				case Boolean:
					return item.getBoolean("value");
				case Short:
				case Integer:
				case Long:
					return item.getNumber("value");
				case BigDecimal:
					return new BigDecimal(item.getString("value"));
				default:
					return item.getString("value");
			}
		}));
	}

	private MenuExtensionPoint toExtensionPoint(AnnotationAttributes[] extensionPoints) {
		if (extensionPoints == null) {
			return null;
		}

		return Stream.of(extensionPoints).findFirst().map(item -> {
			MenuExtensionPoint result = new MenuExtensionPoint();
			result.setId(item.getString("id"));
			result.setDescription(item.getString("description"));
			return result;
		}).orElse(null);
	}

}
