package in.clouthink.daas.sbb.rbac.support.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.clouthink.daas.sbb.rbac.model.DefaultResourceWithChildren;
import in.clouthink.daas.sbb.rbac.model.ResourceWithChildren;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * load resource from json file
 */
public class ResourceJsonLoader implements ResourceLoader {

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final TypeReference<List<DefaultResourceWithChildren>> typeReference = new TypeReference<List<DefaultResourceWithChildren>>() {
	};

	@Override
	public List<ResourceWithChildren> load(InputStream inputStream) {
		try {
			List<DefaultResourceWithChildren> parsedData = objectMapper.readValue(inputStream, typeReference);

			parsedData.stream().forEach(resourceWithChildren -> linkParentAndChildren(resourceWithChildren));

			List<ResourceWithChildren> result = new ArrayList<>();
			parsedData.stream().forEach(resource -> result.add(resource));
			return result;
		}
		catch (Exception e) {
			throw new ResourceLoadException("加载资源出错", e);
		}
	}

	private void linkParentAndChildren(DefaultResourceWithChildren parent) {
		if (!parent.hasChildren()) {
			return;
		}

		parent.getChildren().forEach(child -> {
			((DefaultResourceWithChildren) child).setParent(parent);
			linkParentAndChildren((DefaultResourceWithChildren) child);
		});
	}

	@Override
	public List<ResourceWithChildren> load(File file) {
		try {
			return load(new FileInputStream(file));
		}
		catch (IOException e) {
			throw new ResourceLoadException(String.format("加载资源文件'%s'出错", file.getName()), e);
		}
	}

}
