package in.clouthink.daas.sbb.storage.controller;

import in.clouthink.daas.fss.mongodb.model.FileObject;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.security.SecurityContexts;
import in.clouthink.daas.sbb.storage.dto.DefaultFileObjectQueryParameter;
import in.clouthink.daas.sbb.storage.support.AdvancedFileObjectQueryRestSupport;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api("查询上传的文件")
@RestController
@RequestMapping(value = "/api")
public class AdvancedFileObjectRestController {

	@Autowired
	private AdvancedFileObjectQueryRestSupport advancedFileObjectQueryRestSupport;

	@RequestMapping(value = "/files/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable String id) throws IOException {
		User user = (User) SecurityContexts.getContext().requireUser();
		advancedFileObjectQueryRestSupport.deleteById(id, user);
	}

	@RequestMapping(value = "/files", method = RequestMethod.GET)
	@ResponseBody
	public Page<FileObject> listFileObject(DefaultFileObjectQueryParameter queryParameter) {
		User user = (User) SecurityContexts.getContext().requireUser();
		return advancedFileObjectQueryRestSupport.listFileObject(queryParameter, user);
	}

}
