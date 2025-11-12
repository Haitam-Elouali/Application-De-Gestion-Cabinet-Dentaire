package ma.TeethCare.service.common.dto;

import java.util.List;

/**
 * Simple pagination response DTO to standardize paging across services.
 */
public class PageResponseDTO<T> {

	private List<T> content;
	private int page;            // 0-based
	private int size;            // items per page
	private long totalElements;  // total items across all pages
	private int totalPages;      // total pages

	public PageResponseDTO() {
	}

	public PageResponseDTO(List<T> content, int page, int size, long totalElements, int totalPages) {
		this.content = content;
		this.page = page;
		this.size = size;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
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

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}


