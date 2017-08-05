package in.clouthink.daas.sbb.menu.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * load resource from json file
 */
public class MenuJsonLoader implements MenuLoader {

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final TypeReference<List<Menu>> typeReference = new TypeReference<List<Menu>>() {
	};

	@Override
	public List<Menu> load(InputStream inputStream) {
		try {
			return objectMapper.readValue(inputStream, typeReference);
		}
		catch (Exception e) {
			throw new MenuException("加载菜单出错", e);
		}
	}

	@Override
	public List<Menu> load(File file) {
		try {
			return load(new FileInputStream(file));
		}
		catch (IOException e) {
			throw new MenuException(String.format("加载菜单文件'%s'出错", file.getName()), e);
		}
	}

}
