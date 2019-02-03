package pomelo.core.module.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import pomelo.core.module.log.persistence.entity.Log;
import pomelo.core.module.log.persistence.repo.LogRepository;
import pomelo.core.module.log.service.interfaces.ILogService;

@Service
public class LogService implements ILogService {

	@Autowired
	private LogRepository logRepo;

	@Override
	public void save(Log entity) {
		Assert.notNull(entity, null);
		logRepo.save(entity);
	}
}
