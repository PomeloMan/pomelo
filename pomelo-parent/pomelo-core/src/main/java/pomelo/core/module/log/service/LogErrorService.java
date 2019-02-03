package pomelo.core.module.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import pomelo.core.module.log.persistence.entity.LogError;
import pomelo.core.module.log.persistence.repo.LogErrorRepository;
import pomelo.core.module.log.service.interfaces.ILogErrorService;

@Service
public class LogErrorService implements ILogErrorService {

	@Autowired
	private LogErrorRepository logErrorRepo;

	@Override
	public void save(LogError entity) {
		Assert.notNull(entity, null);
		logErrorRepo.save(entity);
	}
}
