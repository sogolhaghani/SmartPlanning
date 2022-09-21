package ir.smartplanning.client;

public class TopicSelectorItem {
	private Long id;
	private FilterItem filterItem;
	private boolean selected;
	private Long parentId;
	private Long moduleId;

	public TopicSelectorItem() {
		this.selected = false;
	}

	public TopicSelectorItem(Long id, FilterItem filterItem, Long parentId,
			boolean isSelected, Long moduleId) {
		this.filterItem = filterItem;
		this.id = id;
		this.selected = isSelected;
		this.parentId = parentId;
		this.moduleId = moduleId;

	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FilterItem getFilterItem() {
		return filterItem;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setFilterItem(FilterItem filterItem) {
		this.filterItem = filterItem;
	}

}