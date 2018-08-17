package pt.hospetall.web.util;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableImpl implements Pageable {

	private int total, size, page;

	public PageableImpl(int total, int size, int page){
		this.total = total;
		this.size = size;
		this.page = page;
	}

	@Override
	public int getPageNumber() {
		return page;
	}

	@Override
	public int getPageSize() {
		return size;
	}

	@Override
	public long getOffset() {
		return page * size;
	}

	@Override
	public Sort getSort() {
		return null;
	}

	@Override
	public Pageable next() {
		return new PageableImpl(total, size, page);
	}

	@Override
	public Pageable previousOrFirst() {
		if(hasPrevious())
			return new PageableImpl(total, size, page - 1);
		return this;
	}

	@Override
	public Pageable first() {
		return new PageableImpl(total, size, 0);
	}

	@Override
	public boolean hasPrevious() {
		return page > 0;
	}
}
