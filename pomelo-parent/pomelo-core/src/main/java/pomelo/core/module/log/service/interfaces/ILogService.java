package pomelo.core.module.log.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import pomelo.core.module.log.persistence.entity.Log;

@Transactional(readOnly = true)
public interface ILogService {

	@Transactional(readOnly = false)
	void save(Log log);

}
