package in.clouthink.daas.sbb.passcode.repository;


import in.clouthink.daas.sbb.passcode.model.Passcode;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;

/**
 * @author dz
 */
public interface PasscodeRepository extends AbstractRepository<Passcode> {

	Passcode findByCellphoneAndCategory(String cellphone, String category);

}
