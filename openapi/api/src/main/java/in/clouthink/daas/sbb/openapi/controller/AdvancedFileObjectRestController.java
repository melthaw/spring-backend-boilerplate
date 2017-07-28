package in.clouthink.daas.sbb.openapi.controller;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.openapi.dto.DefaultFileObjectQueryParameter;
import in.clouthink.daas.sbb.openapi.support.AdvancedFileObjectQueryRestSupport;
import in.clouthink.daas.sbb.security.SecurityContexts;
import in.clouthink.daas.fss.mongodb.model.FileObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api")
public class AdvancedFileObjectRestController {

	@Autowired
	private AdvancedFileObjectQueryRestSupport advancedFileObjectQueryRestSupport;

	@RequestMapping(value = "/files/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable String id) throws IOException {
		AppUser user = (AppUser) SecurityContexts.getContext().requireUser();
		advancedFileObjectQueryRestSupport.deleteById(id, user);
	}

	@RequestMapping(value = "/files", method = RequestMethod.GET)
	@ResponseBody
	public Page<FileObject> listFileObject(DefaultFileObjectQueryParameter queryParameter) {
		AppUser user = (AppUser) SecurityContexts.getContext().requireUser();
		return advancedFileObjectQueryRestSupport.listFileObject(queryParameter, user);
	}

}
