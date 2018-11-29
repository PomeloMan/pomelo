package pomelo.core.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import pomelo.core.common.util.PageableUtil;

public abstract class AbstractView<P> {

	Pageable pageable;

	int page;
	int size;

	String order;
	String dir;

	String search;

	protected P entity;

	public P getEntity() {
		return entity;
	}

	public void setEntity(P entity) {
		this.entity = entity;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Pageable getPageable() {
		Sort sort = null;
		if (pageable == null) {
			if (StringUtils.isNotEmpty(getOrder()) && StringUtils.isNoneEmpty(getDir())) {
				sort = new Sort(Direction.fromString(getDir()), getOrder());
			}
		}
		pageable = PageableUtil.getPageRequest(getPage(), getSize(), sort);
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

}
