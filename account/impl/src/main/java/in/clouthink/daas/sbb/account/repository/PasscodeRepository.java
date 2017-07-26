package in.clouthink.daas.sbb.account.repository;


import in.clouthink.daas.sbb.account.domain.model.Passcode;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;

/**
 * @author dz
 */
public interface PasscodeRepository extends AbstractRepository<Passcode> {

	Passcode findByCellphoneAndCategory(String cellphone, String category);

}
