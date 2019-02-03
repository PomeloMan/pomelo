package pomelo.core.module.log.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import pomelo.core.module.log.persistence.entity.LogError;

@Transactional(readOnly = true)
public interface ILogErrorService {

	@Transactional(readOnly = false)
	void save(LogError entity);
}
