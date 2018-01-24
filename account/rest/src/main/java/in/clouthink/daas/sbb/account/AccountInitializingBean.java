package in.clouthink.daas.sbb.account;

import in.clouthink.daas.sbb.account.domain.model.Gender;
import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.rest.dto.SaveUserParameter;
import in.clouthink.daas.sbb.account.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dz
 */
public class AccountInitializingBean implements InitializingBean {

    private static final Log logger = LogFactory.getLog(AccountInitializingBean.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AdministratorAccountProperties administratorAccountProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        tryCreateAdministrator();
    }

    private void tryCreateAdministrator() {
        if (StringUtils.isEmpty(administratorAccountProperties.getUsername())) {
            logger.debug("The administrator user is not pre-configured, we will skip it");
            return;
        }

        // initialize System Administrator
        User adminUser = accountService.findByUsername(administratorAccountProperties.getUsername());
        if (adminUser != null) {
            logger.debug("The administrator user is created before, we will skip it");
            return;
        }

        SaveUserParameter saveSysUserParameter = new SaveUserParameter();
        saveSysUserParameter.setUsername(administratorAccountProperties.getUsername());
        saveSysUserParameter.setCellphone(administratorAccountProperties.getCellphone());
        saveSysUserParameter.setEmail(administratorAccountProperties.getEmail());
        saveSysUserParameter.setPassword(administratorAccountProperties.getPassword());
        saveSysUserParameter.setGender(Gender.MALE);
        accountService.createAccount(saveSysUserParameter, SysRole.ROLE_USER, SysRole.ROLE_ADMIN);
    }

}
