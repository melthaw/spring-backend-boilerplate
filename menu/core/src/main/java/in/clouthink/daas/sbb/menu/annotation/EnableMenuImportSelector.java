package in.clouthink.daas.sbb.menu.annotation;

import in.clouthink.daas.sbb.menu.core.DefaultMenuPlugin;
import in.clouthink.daas.sbb.menu.core.MenuExtensionPoint;
import in.clouthink.daas.sbb.rbac.model.Action;
import in.clouthink.daas.sbb.rbac.model.DefaultAction;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

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
public class EnableMenuImportSelector implements ImportSelector {

	protected AnnotationAttributes enableMenu;

	@Override
	public String[] selectImports(AnnotationMetadata metadata) {
		MultiValueMap<String,Object> attributes = metadata.getAllAnnotationAttributes(EnableMenu.class.getName(),
																					  false);
		String pluginId = attributes == null ? null : (String) attributes.getFirst("pluginId");

		if (StringUtils.isEmpty(pluginId)) {
			return new String[0];

		}
		return new String[]{MenuPluginBeanRegistrar.class.getName()};
	}

	public static class MenuPluginBeanRegistrar implements ImportBeanDefinitionRegistrar {
		@Override
		public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
			AnnotationAttributes enableMenu = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EnableMenu.class
																													.getName(),
																											false));

			if (enableMenu != null) {
				BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DefaultMenuPlugin.class);
				AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();

				MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
				mutablePropertyValues.add("extensionPointId", enableMenu.getString("extensionPointId"));
				mutablePropertyValues.add("pluginId", enableMenu.getString("pluginId"));
				mutablePropertyValues.add("menu", toMenu(enableMenu.getAnnotationArray("menu")));
				beanDefinition.setPropertyValues(mutablePropertyValues);

				registry.registerBeanDefinition("menuPlugin:" + enableMenu.getString("pluginId"), beanDefinition);
			}
		}


		//		public MenuPlugin menuPlugin(AnnotationAttributes enableMenu) {
		//			DefaultMenuPlugin menuPlugin = new DefaultMenuPlugin();
		//			menuPlugin.setExtensionPointId(enableMenu.getString("extensionPointId"));
		//			menuPlugin.setPluginId(enableMenu.getString("pluginId"));
		//			menuPlugin.setMenu(toMenu(enableMenu.getAnnotationArray("menu")));
		//			return menuPlugin;
		//		}

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

}
