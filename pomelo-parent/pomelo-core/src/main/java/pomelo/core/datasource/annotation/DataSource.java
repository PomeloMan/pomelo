package pomelo.core.datasource.annotation;

import java.lang.annotation.*;

/**
 * DataSource annotation
 * 
 * @author Pomelor
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

	public enum DataSources {
		PRIMARY, SECONDARY
	};

	// DataSources value() default DataSources.WRITE;
	DataSources value();
}
