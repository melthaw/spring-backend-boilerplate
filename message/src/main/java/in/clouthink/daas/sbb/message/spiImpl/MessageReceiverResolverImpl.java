package in.clouthink.daas.sbb.message.spiImpl;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.repository.AppUserRepository;
import in.clouthink.daas.bm.core.BusinessMessageTarget;
import in.clouthink.daas.bm.core.MessageReceiver;
import in.clouthink.daas.bm.core.MessageReceiverResolver;
import in.clouthink.daas.bm.core.MessageReceiverVisitable;
import in.clouthink.daas.bm.core.impl.MessageReceiverImpl;
import in.clouthink.daas.bm.exception.UnsupportedTargetTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 */
public class MessageReceiverResolverImpl implements MessageReceiverResolver {

	@Autowired
	private AppUserRepository appUserRepository;

	public MessageReceiverResolverImpl() {
	}

	@Override
	public MessageReceiverVisitable resolve(BusinessMessageTarget target) {
		if (BusinessMessageTarget.TYPE_ALL.equalsIgnoreCase(target.getType())) {
			return visitor -> {
				int i = 0;
				Page<AppUser> appUserPage = appUserRepository.queryPageByEnabled(new PageRequest(i++, 100));
				while (appUserPage.hasContent()) {
					appUserPage.getContent().stream().forEach(user -> visitor.visit(convert(user)));

					appUserPage = appUserRepository.queryPageByEnabled(new PageRequest(i++, 100));
				}
			};
		}

		if (BusinessMessageTarget.TYPE_OR.equalsIgnoreCase(target.getType())) {
			return visitor -> {
				int i = 0;
				Page<AppUser> appUserPage = appUserRepository.queryPageByOrTag(target.getValues(),
																			   new PageRequest(i++, 100));
				while (appUserPage.hasContent()) {
					appUserPage.getContent().stream().forEach(user -> visitor.visit(convert(user)));

					appUserPage = appUserRepository.queryPageByOrTag(target.getValues(), new PageRequest(i++, 100));
				}
			};
		}

		if (BusinessMessageTarget.TYPE_AND.equalsIgnoreCase(target.getType())) {
			return visitor -> {
				int i = 0;
				Page<AppUser> appUserPage = appUserRepository.queryPageByAndTag(target.getValues(),
																				new PageRequest(i++, 100));
				while (appUserPage.hasContent()) {
					appUserPage.getContent().stream().forEach(user -> visitor.visit(convert(user)));

					appUserPage = appUserRepository.queryPageByAndTag(target.getValues(), new PageRequest(i++, 100));
				}
			};
		}

		if (BusinessMessageTarget.TYPE_ALIAS.equalsIgnoreCase(target.getType())) {
			for (String id : target.getValues()) {
				AppUser appUser = appUserRepository.findById(id);
				return visitor -> visitor.visit(convert(appUser));
			}
		}

		throw new UnsupportedTargetTypeException(String.format("Unsupported target type '%s'", target.getType()));
	}

	public MessageReceiver convert(AppUser appUser) {
		if (appUser == null) {
			return null;
		}

		MessageReceiverImpl result = new MessageReceiverImpl();
		result.setId(appUser.getId());
		result.setType("appuser");
		result.setName(appUser.getUsername());
		return result;
	}

}
