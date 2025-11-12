package ma.TeethCare.service.common.dto;

/**
 * Simple pagination request DTO to standardize paging across services.
 */
public class PageRequestDTO {

	private int page;       // 0-based
	private int size;       // items per page
	private String sort;    // e.g., "createdAt,desc"

	public PageRequestDTO() {
	}

	public PageRequestDTO(int page, int size, String sort) {
		this.page = page;
		this.size = size;
		this.sort = sort;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}


