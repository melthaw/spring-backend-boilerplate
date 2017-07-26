package in.clouthink.daas.sbb.rbac.impl.test;

import in.clouthink.daas.sbb.rbac.model.ResourceWithChildren;
import in.clouthink.daas.sbb.rbac.support.loader.ResourceJsonLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 */
public class ResourceLoaderTest {

	@Test
	public void testLoadJsonFile() {
		InputStream inputStream = ResourceJsonLoader.class.getClassLoader()
														  .getResourceAsStream(
																  "in.clouthink.daas.appt.rbac.impl.sample.json");
		List<ResourceWithChildren> result = new ResourceJsonLoader().load(inputStream);
		Assert.assertEquals(8, result.size());
		Assert.assertFalse(result.get(0).isVirtual());
		Assert.assertTrue(result.get(7).isVirtual());
	}

}


