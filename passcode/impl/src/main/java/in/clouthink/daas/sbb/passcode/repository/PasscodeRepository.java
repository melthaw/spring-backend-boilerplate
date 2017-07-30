package in.clouthink.daas.sbb.passcode.repository;


import in.clouthink.daas.sbb.passcode.model.Passcode;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasscodeRepository extends AbstractRepository<Passcode> {

	Passcode findByCellphoneAndCategory(String cellphone, String category);

}
