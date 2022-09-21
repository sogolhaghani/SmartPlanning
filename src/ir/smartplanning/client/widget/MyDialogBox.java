package ir.smartplanning.client.widget;

import ir.smartplanning.client.CacheHelper;
import ir.smartplanning.client.CacheKey;
import ir.smartplanning.client.CacheKeyTypes;
import ir.smartplanning.client.FilterItem;
import ir.smartplanning.client.FilterItemTreeViewModel;
import ir.smartplanning.client.Messages;
import ir.smartplanning.client.MyGateKeeper;
import ir.smartplanning.client.TopicHepler;
import ir.smartplanning.client.TopicSelectorItem;
import ir.smartplanning.shared.proxies.nonpersists.PlanItemProxy;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class MyDialogBox extends DialogBox {

	private CellBrowser cellBrowser;
	private Label topicName;
	private ListBox moduleGroup;
	private ListBox module;
	private TextBox duration;
	private TextBox testNo;
	private TextBox incorrectNo;
	private Label description;

	private HTMLPanel panel;
	private Button save;
	private Button cancel;
	private HTMLPanel firstRow;
	private HTMLPanel secondRow;
	private HTMLPanel thirdRow;
	private HTMLPanel forthRow;
	private ListDataProvider<TopicSelectorItem> dataProvider;
	private MultiSelectionModel<TopicSelectorItem> selectionModel;
	private PlanItemProxy planItemProxy;
	private TextBox correctNo;

	public MyDialogBox() {
		definition();
		setStyle();
		prepareDialogBox();
		handler();
	}

	private void setStyle() {
		this.panel.addStyleName("my-popup ");
		this.duration.addStyleName(" width_15 form-control");
		this.testNo.addStyleName("width_15 form-control ");
		this.incorrectNo.addStyleName("width_15 form-control ");
		this.correctNo.addStyleName("width_15 form-control ");

		this.firstRow.addStyleName("form-group group");
		this.secondRow.addStyleName("form-group group");
		this.thirdRow.addStyleName("form-group group");
		this.forthRow.addStyleName("form-group group btn-panel");

		this.save.addStyleName("btn btn-success btn-margin");
		this.cancel.addStyleName("btn btn-danger btn-margin");
		this.topicName.addStyleName("topic-name");

	}

	protected void onModuleChange() {
		if (selectionModel.getSelectedSet().size() > 0) {
			Messages.ShowNoty(
					"مواردی که از کتاب قبلی انتخاب شده بود حذف می شود.",
					Messages.STANDARD);
		}
		refreshTopicDataProvider();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void definition() {
		this.dataProvider = new ListDataProvider<TopicSelectorItem>();
		this.selectionModel = new MultiSelectionModel<TopicSelectorItem>(
				new ProvidesKey<TopicSelectorItem>() {

					@Override
					public Object getKey(TopicSelectorItem item) {
						return item.getFilterItem().getId();
					}
				});
		this.cellBrowser = new CellBrowser.Builder(new FilterItemTreeViewModel(
				dataProvider, selectionModel), null).build();
		this.cellBrowser.setAnimationEnabled(true);
		this.cellBrowser.setDefaultColumnWidth(250);
		this.cellBrowser.setHeight("200px");
		this.setAnimationEnabled(true);
		this.setAutoHideEnabled(false);
		this.setGlassEnabled(true);
		this.setModal(true);
		this.setAnimationType(AnimationType.ROLL_DOWN);
		this.topicName = new Label();
		this.module = new ListBox();
		this.moduleGroup = new ListBox();
		this.duration = new TextBox();
		this.duration.getElement().setAttribute("type", "Number");
		this.testNo = new TextBox();
		this.testNo.getElement().setAttribute("type", "Number");
		this.incorrectNo = new TextBox();
		this.incorrectNo.getElement().setAttribute("type", "Number");
		this.correctNo=new TextBox();
		this.correctNo.getElement().setAttribute("type", "Number");
		this.description = new Label();
		this.panel = new HTMLPanel("");
		this.save = new Button();
		this.cancel = new Button();
		this.firstRow = new HTMLPanel("");
		this.secondRow = new HTMLPanel("");
		this.thirdRow = new HTMLPanel("");
		this.forthRow = new HTMLPanel("");
	}

	private void prepareDialogBox() {
		firstRow.add(moduleGroup);
		firstRow.add(module);

		secondRow.add(new HTML("<label>مدت زمان مطالعه (دقیقه)</label>"));
		secondRow.add(duration);
		secondRow
				.add(new HTML(
						"<label>با توجه به مدت زمان مطالعه برای هر درس میشه به کیفیت درس خوندن و همچنین نقاط ضعف و قوت پی برد.</label>"));

		thirdRow.add(new HTML("<label>تعداد تست حل شده</label>"));
		thirdRow.add(testNo);
		thirdRow.add(new HTML(
				"<label>دونستن تعداد تست یا تمرین حل شده به مشاور کمک میکنه تا بهتر بهت کمک کنه</label>"));
		thirdRow.add(new HTML("<br/><label>تعداد تست درست</label>"));
		thirdRow.add(correctNo);
		thirdRow.add(new HTML("<br/><label>تعداد تست غلط</label>"));
		thirdRow.add(incorrectNo);
	
		forthRow.add(save);
		forthRow.add(cancel);

		save.setText("ثبت");
		cancel.setText("انصراف");

		panel.add(firstRow);
		panel.add(cellBrowser);
		panel.add(topicName);
		panel.add(secondRow);
		panel.add(thirdRow);
		panel.add(description);
		panel.add(forthRow);
		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		this.add(panel);
	}

	private void refreshTopicDataProvider() {
		List<TopicSelectorItem> topicSelectorList = dataProvider.getList();
		topicSelectorList.clear();
		String value = module.getSelectedValue();
		if (value != null && value.trim().equals("null") == false) {
			long moduleId = Long.parseLong(value.trim());
			List<FilterItem> items = CacheHelper.getCahceListItems().get(
					new CacheKey(CacheKeyTypes.Topic, moduleId));
			if (items != null)
				for (FilterItem filterItem : items) {
					TopicSelectorItem topicSelectorItem = new TopicSelectorItem(
							(long) CacheKeyTypes.Topic.getId(), filterItem,
							null, false, moduleId);
					topicSelectorList.add(topicSelectorItem);
				}
		}
		selectionModel.clear();
		dataProvider.refresh();
	}

	private void handler() {
		this.moduleGroup.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				String value = moduleGroup.getSelectedValue().trim();
				Long moduleGroupId = null;
				if (value.trim().equals("null") == false) {
					moduleGroupId = Long.parseLong(value);
				}
				populateModule(moduleGroupId);
			}
		});
		this.module.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				onModuleChange();
			}
		});
		selectionModel.addSelectionChangeHandler(new Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				long moduleId;
				String topics = "";

				if (selectionModel.getSelectedSet().size() > 0) {
					for (TopicSelectorItem topicSelectorItem : selectionModel
							.getSelectedSet()) {
						if (topics.length() > 0) {
							topics += ",";
						}
						topics += topicSelectorItem.getFilterItem().getId();
					}
				}
				if (topics.length() > 0) {
					moduleId = getId(topics, CacheKeyTypes.Module);
					topicName.setText(TopicHepler.getSelectedTopicName(
							moduleId, topics));
				}
			}
		});
	}

	protected Long getId(String topics, CacheKeyTypes cacheKeyTypes) {
		if (topics == null || topics.trim().equals("") == true) {
			return null;
		}
		String[] topicIds = topics.trim().split(",");
		Long topicId = null;
		try {
			topicId = Long.parseLong(topicIds[0]);
		} catch (Exception e) {
			return null;
		}
		if (topicId == null || topicId.longValue() < -1) {
			return null;
		}
		long majorId = MyGateKeeper.getUser().getMajorId();
		List<FilterItem> moduleGroupItems = CacheHelper.getCahceListItems()
				.get(new CacheKey(CacheKeyTypes.ModuleGroup, majorId));
		if (moduleGroupItems != null) {
			for (FilterItem moduleGroup : moduleGroupItems) {
				byte grade = MyGateKeeper.getUser().getGrade();
				List<FilterItem> moduleItems = CacheHelper.getCahceListItems()
						.get(new CacheKey(CacheKeyTypes.Module, moduleGroup
								.getId().longValue(), grade, majorId));
				if (moduleItems != null) {
					for (FilterItem moduleItem : moduleItems) {
						List<FilterItem> topicItems = CacheHelper
								.getCahceListItems()
								.get(new CacheKey(CacheKeyTypes.Topic,
										moduleItem.getId().longValue()));
						if (topicItems != null) {
							for (FilterItem filterItem : topicItems) {
								if (filterItem.getId().longValue() == topicId
										.longValue()) {
									if (cacheKeyTypes.getId() == CacheKeyTypes.ModuleGroup
											.getId()) {
										return moduleGroup.getId();
									} else if (cacheKeyTypes.getId() == CacheKeyTypes.Module
											.getId()) {
										return moduleItem.getId();
									} else if (cacheKeyTypes.getId() == CacheKeyTypes.Topic
											.getId()) {
										return filterItem.getId();
									}

								}
							}
							for (FilterItem filterItem : topicItems) {
								List<FilterItem> subIopicItems = CacheHelper
										.getCahceListItems().get(
												new CacheKey(
														CacheKeyTypes.SubTopic,
														filterItem.getId()
																.longValue()));
								if (subIopicItems != null) {
									for (FilterItem filterItem2 : subIopicItems) {
										if (filterItem2.getId().longValue() == topicId
												.longValue()) {
											if (cacheKeyTypes.getId() == CacheKeyTypes.ModuleGroup
													.getId()) {
												return moduleGroup.getId();
											} else if (cacheKeyTypes.getId() == CacheKeyTypes.Module
													.getId()) {
												return moduleItem.getId();
											} else if (cacheKeyTypes.getId() == CacheKeyTypes.Topic
													.getId()) {
												return filterItem.getId();
											} else if (cacheKeyTypes.getId() == CacheKeyTypes.SubTopic
													.getId()) {
												return filterItem2.getId();
											}
										}
									}

									for (FilterItem filterItem2 : subIopicItems) {
										List<FilterItem> subIopic1Items = CacheHelper
												.getCahceListItems()
												.get(new CacheKey(
														CacheKeyTypes.SubTopic1,
														filterItem2.getId()
																.longValue()));
										if (subIopic1Items != null) {
											for (FilterItem filterItem3 : subIopic1Items) {
												if (filterItem3.getId()
														.longValue() == topicId
														.longValue()) {
													if (cacheKeyTypes.getId() == CacheKeyTypes.ModuleGroup
															.getId()) {
														return moduleGroup
																.getId();
													} else if (cacheKeyTypes
															.getId() == CacheKeyTypes.Module
															.getId()) {
														return moduleItem
																.getId();
													} else if (cacheKeyTypes
															.getId() == CacheKeyTypes.Topic
															.getId()) {
														return filterItem
																.getId();
													} else if (cacheKeyTypes
															.getId() == CacheKeyTypes.SubTopic
															.getId()) {
														return filterItem2
																.getId();
													} else if (cacheKeyTypes
															.getId() == CacheKeyTypes.SubTopic1
															.getId()) {
														filterItem3.getId();
													}
												}
											}
										}
									}

								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	private void populateModule(Long moduleGroupId) {
		module.clear();
		long majorId = MyGateKeeper.getUser().getMajorId();
		byte grade = MyGateKeeper.getUser().getGrade();
		List<FilterItem> cachItems = CacheHelper.getCahceListItems().get(
				new CacheKey(CacheKeyTypes.Module, moduleGroupId, grade,
						majorId));
		module.insertItem("درس", "null", 0);
		if (cachItems != null) {
			for (int i = 0; i < cachItems.size(); i++) {
				module.insertItem(cachItems.get(i).getName(), cachItems.get(i)
						.getId() + "", i + 1);
			}
		}
	}

	private void populateModuleGroup() {
		module.clear();
		if (moduleGroup.getItemCount() > 0) {
			moduleGroup.setSelectedIndex(0);
		} else {
			List<FilterItem> cachedItems = CacheHelper.getCahceListItems().get(
					new CacheKey(CacheKeyTypes.ModuleGroup, MyGateKeeper
							.getUser().getMajorId()));
			moduleGroup.insertItem("گروه درسی", "null", 0);
			if (cachedItems != null) {
				for (int i = 1; i <= cachedItems.size(); i++) {
					moduleGroup.insertItem(cachedItems.get(i - 1).getName(),
							cachedItems.get(i - 1).getId() + "", i);
				}
			}

		}
	}

	public void setPlanItem(PlanItemProxy planItemProxy, boolean hasDefultPlan) {
		this.planItemProxy = planItemProxy;
		topicName.setText("");
		if (hasDefultPlan == false) {
			setVisibleModuleAndModuleGroupListBox(true);
			populateModuleGroup();
		} else {
			setVisibleModuleAndModuleGroupListBox(false);
		}
		List<TopicSelectorItem> topicSelectorList = dataProvider.getList();
		long moduleId = planItemProxy.getModuleId();
		long moduleGroupId = getModuleGroupId(moduleId);
		setModuleGrouId(moduleGroupId);
		setModuleId(moduleId, moduleGroupId);
		refreshTopicDataProvider();
		topicSelectorList.clear();
		selectionModel.clear();
		if (planItemProxy.getStudyFeedbackTopics() != null) {
			String topics = planItemProxy.getStudyFeedbackTopics();
			if (topics != null) {
				setTopics(topicSelectorList, moduleId, topics);
			}
			topicName.setText(TopicHepler
					.getSelectedTopicName(moduleId, topics));
		}

		if (planItemProxy.getDuration() != null) {
			this.duration.setText(planItemProxy.getDuration() + "");
		} else {
			this.duration.setText("");
		}
		if (planItemProxy.getIncorrectNo() != null) {
			this.incorrectNo.setText(planItemProxy.getIncorrectNo() + "");
		} else {
			this.incorrectNo.setText("");
		}
		if (planItemProxy.getTestNo() != null) {
			this.testNo.setText(planItemProxy.getTestNo() + "");
		} else {
			this.testNo.setText("");
		}
		if (planItemProxy.getCorrectNo() != null) {
			this.correctNo.setText(planItemProxy.getCorrectNo() + "");
		} else {
			this.correctNo.setText("");
		}

		dataProvider.refresh();
	}

	private void setTopics(List<TopicSelectorItem> topicSelectorList,
			long moduleId, String topics) {
		refreshTopicDataProvider();
		String[] topicIds = topics.trim().split(",");
		List<Long> topicList = new ArrayList<Long>();
		if (topicIds.length > 0) {
			for (String string : topicIds) {
				try {
					long topicId = Long.parseLong(string.trim());
					if (topicId > 0) {
						topicList.add(topicId);
					}
				} catch (Exception e) {
				}
			}
		}
		List<FilterItem> items = CacheHelper.getCahceListItems().get(
				new CacheKey(CacheKeyTypes.Topic, moduleId));
		if (items != null) {
			for (FilterItem filterItem : items) {
				boolean isSelected = false;
				if (topicList.contains(filterItem.getId().longValue())) {
					isSelected = true;
					topicList.remove(filterItem.getId().longValue());
				}
				TopicSelectorItem topicSelectorItem = new TopicSelectorItem(
						(long) CacheKeyTypes.Topic.getId(), filterItem, null,
						isSelected, moduleId);
				topicSelectorList.add(topicSelectorItem);
				if (isSelected == true) {
					selectionModel.setSelected(topicSelectorItem, true);
				}
			}
		}

		if (topicList.size() > 0) {
			for (Long id : topicList) {
				FilterItem filterItem = getTopicItem(id, moduleId);
				TopicSelectorItem topicSelectorItem = new TopicSelectorItem(
						(long) getTopicLevel(id, moduleId).getId(), filterItem,
						getParentTopicId(id, moduleId), true, moduleId);
				selectionModel.setSelected(topicSelectorItem, true);
			}
		}

	}

	private void setModuleId(long moduleId, long moduleGroupId) {
		populateModule(moduleGroupId);
		for (int i = 0; i < module.getItemCount(); i++) {
			if (module.getValue(i).trim().equals("" + moduleId)) {
				module.setSelectedIndex(i);
				break;
			}
		}

	}

	private void setModuleGrouId(long moduleGroupId) {
		for (int i = 0; i < moduleGroup.getItemCount(); i++) {
			if (moduleGroup.getValue(i).trim().equals("" + moduleGroupId)) {
				moduleGroup.setSelectedIndex(i);
				break;
			}
		}
	}

	private long getModuleGroupId(long moduleId) {
		List<FilterItem> cachedItems = CacheHelper.getCahceListItems().get(
				new CacheKey(CacheKeyTypes.ModuleGroup, MyGateKeeper.getUser()
						.getMajorId()));
		if (cachedItems != null) {
			for (FilterItem filterItem : cachedItems) {
				List<FilterItem> moduleItems = CacheHelper.getCahceListItems()
						.get(new CacheKey(CacheKeyTypes.Module, filterItem
								.getId(), MyGateKeeper.getUser().getGrade(),
								MyGateKeeper.getUser().getMajorId()));
				if (moduleItems != null) {
					for (FilterItem filterItem2 : moduleItems) {
						if (filterItem2.getId().longValue() == moduleId) {
							return filterItem.getId();
						}
					}
				}
			}
		}
		return 0;
	}

	private void setVisibleModuleAndModuleGroupListBox(boolean visible) {
		if (visible) {
			this.firstRow.removeStyleName("display_none");
		} else {
			this.firstRow.addStyleName("display_none");
		}
	}

	public Button getSave() {
		return save;
	}

	public void hidePanel() {
		this.hide();
	}

	public void showPanel() {
		this.show();
		this.center();
	}

	private Long getParentTopicId(Long topicId, Long moduleId) {
		List<FilterItem> topicItems = CacheHelper.getCahceListItems().get(
				new CacheKey(CacheKeyTypes.Topic, moduleId));
		if (topicItems != null) {
			for (FilterItem filterItem : topicItems) {
				if (filterItem.getId() == topicId) {
					return moduleId;
				}
			}
			for (FilterItem filterItem : topicItems) {
				List<FilterItem> subIopicItems = CacheHelper
						.getCahceListItems().get(
								new CacheKey(CacheKeyTypes.SubTopic, filterItem
										.getId()));
				if (subIopicItems != null) {
					for (FilterItem filterItem2 : subIopicItems) {
						if (filterItem2.getId() == topicId) {
							return filterItem.getId();
						}
					}

					for (FilterItem filterItem2 : subIopicItems) {
						List<FilterItem> subIopic1Items = CacheHelper
								.getCahceListItems().get(
										new CacheKey(CacheKeyTypes.SubTopic1,
												filterItem2.getId()));
						if (subIopic1Items != null) {
							for (FilterItem filterItem3 : subIopic1Items) {
								if (filterItem3.getId() == topicId) {
									return filterItem2.getId();
								}
							}
						}
					}

				}
			}
		}
		return moduleId;

	}

	private FilterItem getTopicItem(Long topicId, Long moduleId) {
		List<FilterItem> topicItems = CacheHelper.getCahceListItems().get(
				new CacheKey(CacheKeyTypes.Topic, moduleId.longValue()));
		if (topicItems != null) {
			for (FilterItem filterItem : topicItems) {
				if (filterItem.getId().longValue() == topicId.longValue()) {
					return filterItem;
				}
			}
			for (FilterItem filterItem : topicItems) {
				List<FilterItem> subIopicItems = CacheHelper
						.getCahceListItems().get(
								new CacheKey(CacheKeyTypes.SubTopic, filterItem
										.getId().longValue()));
				if (subIopicItems != null) {
					for (FilterItem filterItem2 : subIopicItems) {
						if (filterItem2.getId().longValue() == topicId
								.longValue()) {
							return filterItem2;
						}
					}

					for (FilterItem filterItem2 : subIopicItems) {
						List<FilterItem> subIopic1Items = CacheHelper
								.getCahceListItems().get(
										new CacheKey(CacheKeyTypes.SubTopic1,
												filterItem2.getId().longValue()));
						if (subIopic1Items != null) {
							for (FilterItem filterItem3 : subIopic1Items) {
								if (filterItem3.getId().longValue() == topicId.longValue()) {
									return filterItem3;
								}
							}
						}
					}

				}
			}
		}
		return new FilterItem();
	}

	private CacheKeyTypes getTopicLevel(Long topicId, Long moduleId) {
		List<FilterItem> topicItems = CacheHelper.getCahceListItems().get(
				new CacheKey(CacheKeyTypes.Topic, moduleId));
		if (topicItems != null) {
			for (FilterItem filterItem : topicItems) {
				if (filterItem.getId() == topicId) {
					return CacheKeyTypes.Topic;
				}
			}
			for (FilterItem filterItem : topicItems) {
				List<FilterItem> subIopicItems = CacheHelper
						.getCahceListItems().get(
								new CacheKey(CacheKeyTypes.SubTopic, filterItem
										.getId()));
				if (subIopicItems != null) {
					for (FilterItem filterItem2 : subIopicItems) {
						if (filterItem2.getId() == topicId) {
							return CacheKeyTypes.SubTopic;
						}
					}
					for (FilterItem filterItem2 : subIopicItems) {
						List<FilterItem> subIopic1Items = CacheHelper
								.getCahceListItems().get(
										new CacheKey(CacheKeyTypes.SubTopic1,
												filterItem2.getId()));
						if (subIopic1Items != null) {
							for (FilterItem filterItem3 : subIopic1Items) {
								if (filterItem3.getId() == topicId) {
									return CacheKeyTypes.SubTopic1;
								}
							}
						}
					}
				}
			}
		}
		return CacheKeyTypes.Module;
	}

	public int getDuration() {
		String durationString = this.duration.getText().trim();
		if (durationString.trim().equals("")) {
			return 0;
		}
		try {
			return Integer.parseInt(durationString.trim());
		} catch (Exception e) {
			return -1;
		}
	}

	public int getTesNo() {
		String testNoString = this.testNo.getText().trim();
		if (testNoString.trim().equals("")) {
			return 0;
		}
		try {
			return Integer.parseInt(testNoString.trim());
		} catch (Exception e) {
			return -1;
		}
	}

	public byte getInCorrectNo() {
		String testNoString = this.incorrectNo.getText().trim();
		if (testNoString.trim().equals("")) {
			return 0;
		}
		try {
			return Byte.parseByte(testNoString.trim());
		} catch (Exception e) {
			return -1;
		}
	}

	public String getTopics() {
		if (selectionModel.getSelectedSet().size() > 0) {
			String ids = "";
			for (TopicSelectorItem selectorItem : selectionModel
					.getSelectedSet()) {
				if (ids.trim().length() > 0) {
					ids += ",";
				}
				ids += selectorItem.getFilterItem().getId();
			}
			return ids;
		} else {
			return "-1";
		}
	}

	public byte getDayOfWeek() {
		return planItemProxy.getDayOfWeek();
	}

	public byte getOrder() {
		return planItemProxy.getOrder();
	}

	public Long getStudyFeedBackId() {
		return planItemProxy.getStudyFeedbackId();
	}

	public byte getCorrectNo() {
		String testNoString = this.correctNo.getText().trim();
		if (testNoString.trim().equals("")) {
			return 0;
		}
		try {
			return Byte.parseByte(testNoString.trim());
		} catch (Exception e) {
			return -1;
		}
	}
}
