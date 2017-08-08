package in.clouthink.daas.sbb.apidoc;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

@Configuration
@EnableSwagger2
public class SpringfoxConfiguration {

	@Autowired
	private TypeResolver typeResolver;

	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
													  .select()
													  .apis(RequestHandlerSelectors.any())

													  .paths(PathSelectors.any())
													  .build()
													  .pathMapping("/")
													  .directModelSubstitute(LocalDate.class, String.class)
													  .genericModelSubstitutes(ResponseEntity.class)
													  .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(
															  DeferredResult.class,
															  typeResolver.resolve(ResponseEntity.class,
																				   WildcardType.class)),
																									 typeResolver.resolve(
																											 WildcardType.class)))
													  .useDefaultResponseMessages(false);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boilerplate APIs")
								   .termsOfServiceUrl("https://github.com/melthaw/spring-backend-boilerplate")
								   .contact("https://github.com/melthaw/spring-backend-boilerplate")
								   .version("1.0.0")
								   .build();
	}
}
