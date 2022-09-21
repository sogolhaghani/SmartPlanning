package ir.smartplanning.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel;

public class FilterItemTreeViewModel implements TreeViewModel {
	// private Logger logger =
	// Logger.getLogger(FilterItemTreeViewModel.class.getName());
	private final ListDataProvider<TopicSelectorItem> topicDataProvider;
	private final ListDataProvider<TopicSelectorItem> subTopicDataProvider;
	private final ListDataProvider<TopicSelectorItem> subTopic2DataProvider;
	private final Cell<TopicSelectorItem> contactCell;
	private final DefaultSelectionEventManager<TopicSelectorItem> selectionManager = DefaultSelectionEventManager
			.createCheckboxManager();
	private final SelectionModel<TopicSelectorItem> selectionModel;

	public FilterItemTreeViewModel(
			ListDataProvider<TopicSelectorItem> topicProvider,
			final SelectionModel<TopicSelectorItem> model) {
		this.selectionModel = model;
		this.topicDataProvider = topicProvider;// new
												// ListDataProvider<TopicSelectorItem>();
		this.subTopicDataProvider = new ListDataProvider<TopicSelectorItem>();
		this.subTopic2DataProvider = new ListDataProvider<TopicSelectorItem>();

		List<HasCell<TopicSelectorItem, ?>> hasCells = new ArrayList<HasCell<TopicSelectorItem, ?>>();
		hasCells.add(new HasCell<TopicSelectorItem, Boolean>() {

			private CheckboxCell cell = new CheckboxCell(true, true);

			public Cell<Boolean> getCell() {

				return cell;
			}

			@Override
			public FieldUpdater<TopicSelectorItem, Boolean> getFieldUpdater() {
				return new FieldUpdater<TopicSelectorItem, Boolean>() {

					@Override
					public void update(int index, TopicSelectorItem object,
							Boolean value) {
						object.setSelected(value);
						selectionModel.setSelected(object, value);
						List<TopicSelectorItem> topicList = null;
						byte parentLevel = object.getId().byteValue();
						Long parentId = object.getParentId();
						if (value == true) {
							while (parentLevel > CacheKeyTypes.Topic.getId()) {
								if (parentLevel == CacheKeyTypes.SubTopic1
										.getId()) {
									topicList = subTopic2DataProvider.getList();

								} else if (parentLevel == CacheKeyTypes.SubTopic
										.getId()) {
									topicList = subTopicDataProvider.getList();
								}
								if (allSelected(topicList) == true) {
									for (int i = 0; i < topicList.size(); i++) {
										selectionModel.setSelected(
												topicList.get(i), false);
										topicList.get(i).setSelected(false);
									}
									if (parentLevel == CacheKeyTypes.SubTopic1
											.getId()) {
										topicList = subTopicDataProvider
												.getList();

									} else if (parentLevel == CacheKeyTypes.SubTopic
											.getId()) {
										topicList = topicDataProvider.getList();
									}
									for (int i = 0; i < topicList.size(); i++) {
										if (topicList.get(i).getFilterItem()
												.getId().longValue() == parentId) {
											topicList.get(i).setSelected(true);
											selectionModel.setSelected(
													topicList.get(i), true);
											parentId = topicList.get(i)
													.getParentId();
											object = topicList.get(i);
											parentLevel--;
											break;
										}
									}

								} else {
									cleanAllParents(object);
									clearAllChildren(object);
									topicDataProvider.refresh();
									subTopicDataProvider.refresh();
									subTopic2DataProvider.refresh();
									return;

								}
							}
							clearAllChildren(object);
							topicDataProvider.refresh();
							subTopicDataProvider.refresh();
							subTopic2DataProvider.refresh();
						}

					}

					private void clearAllChildren(TopicSelectorItem object) {
						Set<TopicSelectorItem> selectedList = ((MultiSelectionModel<TopicSelectorItem>) selectionModel)
								.getSelectedSet();
						if (selectedList != null && selectedList.size() > 0) {
							List<Long> items = new ArrayList<Long>();
							items.add(object.getFilterItem().getId());
							HashSet<Long> children = CacheHelper.getChildTopic(
									CacheHelper.getCahceListItems(), items);
							for (TopicSelectorItem item : selectedList) {
								if (children.contains(item.getFilterItem()
										.getId())
										&& object.getFilterItem().getId()
												.longValue() != item
												.getFilterItem().getId()
												.longValue()) {
									selectionModel.setSelected(item, false);
									item.setSelected(false);
								}
							}
						}

					}

					private void cleanAllParents(TopicSelectorItem object) {
						byte parentLevel = object.getId().byteValue();
						Long parentId = object.getParentId();

						List<TopicSelectorItem> topicList = null;
						;
						if (parentLevel == CacheKeyTypes.SubTopic1.getId()) {
							topicList = subTopicDataProvider.getList();
						} else if (parentLevel == CacheKeyTypes.SubTopic
								.getId()) {
							topicList = topicDataProvider.getList();
						}
						if (topicList != null) {
							for (int i = 0; i < topicList.size(); i++) {
								if (topicList.get(i).getFilterItem().getId()
										.longValue() == parentId.longValue()) {
									topicList.get(i).setSelected(false);
									selectionModel.setSelected(
											topicList.get(i), false);

									cleanAllParents(topicList.get(i));
									return;
								}
							}

						}

					}

				};

			}

			private boolean allSelected(List<TopicSelectorItem> topicList) {
				for (int i = 0; i < topicList.size(); i++) {
					if (topicList.get(i).isSelected() == false)
						return false;
				}
				return true;
			}

			public Boolean getValue(TopicSelectorItem object) {
				selectionModel.isSelected(object);

				return object.isSelected();
			}

		});

		hasCells.add(new HasCell<TopicSelectorItem, TopicSelectorItem>() {

			private TopicCell cell = new TopicCell();

			public Cell<TopicSelectorItem> getCell() {
				return cell;
			}

			public FieldUpdater<TopicSelectorItem, TopicSelectorItem> getFieldUpdater() {
				return null;
			}

			public TopicSelectorItem getValue(TopicSelectorItem object) {
				return object;
			}

		});
		contactCell = new CompositeCell<TopicSelectorItem>(hasCells) {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					TopicSelectorItem value, SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<table style=\"text-align:right\"><tbody><tr>");
				super.render(context, value, sb);
				sb.appendHtmlConstant("</tr></tbody></table>");
			}

			@Override
			protected Element getContainerElement(Element parent) {
				// Return the first TR element in the table.
				return parent.getFirstChildElement().getFirstChildElement()
						.getFirstChildElement();
			}

			@Override
			protected <X> void render(Context context, TopicSelectorItem value,
					SafeHtmlBuilder sb, HasCell<TopicSelectorItem, X> hasCell) {
				Cell<X> cell = hasCell.getCell();
				sb.appendHtmlConstant("<td>");
				cell.render(context, hasCell.getValue(value), sb);
				sb.appendHtmlConstant("</td>");
			}

		};

	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			return new DefaultNodeInfo<TopicSelectorItem>(topicDataProvider,
					contactCell, selectionModel, selectionManager, null);
		} else {
			TopicSelectorItem topicSelectorItem = (TopicSelectorItem) value;
			List<TopicSelectorItem> categoryList;
			if (topicSelectorItem.getId().byteValue() == CacheKeyTypes.Topic
					.getId()) {
				categoryList = subTopicDataProvider.getList();
			} else {
				categoryList = subTopic2DataProvider.getList();
			}

			List<FilterItem> items = CacheHelper.getCahceListItems().get(
					new CacheKey(CacheKeyTypes
							.getEnum((byte) (topicSelectorItem.getId()
									.byteValue() + 1)), topicSelectorItem
							.getFilterItem().getId()));

			categoryList.clear();
			if (items != null) {
				for (FilterItem filterItem : items) {
					TopicSelectorItem selectorItem = new TopicSelectorItem(
							topicSelectorItem.getId().longValue() + 1,
							filterItem, topicSelectorItem.getFilterItem()
									.getId(), false,
							topicSelectorItem.getModuleId());
					selectorItem.setSelected(selectionModel
							.isSelected(selectorItem));
					categoryList.add(selectorItem);
				}
			}
			if (topicSelectorItem.getId().byteValue() == CacheKeyTypes.Topic
					.getId()) {
				return new DefaultNodeInfo<TopicSelectorItem>(
						subTopicDataProvider, contactCell, selectionModel,
						selectionManager, null);
			} else {
				return new DefaultNodeInfo<TopicSelectorItem>(
						subTopic2DataProvider, contactCell, selectionModel,
						selectionManager, null);
			}

		}
	}

	@Override
	public boolean isLeaf(Object value) {
		if (value == null)
			return true;
		TopicSelectorItem topicSelectorItem = (TopicSelectorItem) value;
		if (topicSelectorItem.getId().byteValue() == CacheKeyTypes.SubTopic1
				.getId())
			return true;
		return false;
	}

	private static class TopicCell extends AbstractCell<TopicSelectorItem> {
		@Override
		public void render(Context context, TopicSelectorItem value,
				SafeHtmlBuilder sb) {
			if (value != null) {
				sb.appendEscaped(value.getFilterItem().getName());
			}
		}
	}

}