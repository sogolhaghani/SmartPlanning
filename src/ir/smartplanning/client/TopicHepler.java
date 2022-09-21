package ir.smartplanning.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TopicHepler {

	public  static String getSelectedTopicName(long moduleId,String topics){
		Map<Long, Set<FilterItem>> tree = new HashMap<Long, Set<FilterItem>>();
		makeTree(tree, topics, moduleId);
		String name=readVLR(tree, moduleId);
		return name;
	}
	
	private static String readVLR(Map<Long, Set<FilterItem>> tree, long moduleId) {
		String name = "";
		Set<FilterItem> topicItems = tree.get(moduleId);
		if (topicItems != null && topicItems.size() > 0) {
			List<FilterItem> sortedTopicItems=sort(topicItems);
			for (FilterItem topicItem : sortedTopicItems) {
				name += " * " + topicItem.getName();
				Set<FilterItem> subTopicItems = tree.get(topicItem.getId());
				if (subTopicItems != null && subTopicItems.size() > 0) {
					List<FilterItem> sortedSubTopicItems=sort(subTopicItems);
					name += ":";
					for (FilterItem subtopic : sortedSubTopicItems) {
						name += subtopic.getName()+"،";
						Set<FilterItem> subTopic1Items = tree.get(subtopic.getId());
						if (subTopic1Items != null && subTopic1Items.size() > 0) {
							List<FilterItem> sortedSubTopic1Items=sort(subTopic1Items);
							name=name.substring(0, name.length()-1);
							name += " (";
							for (FilterItem filterItem : sortedSubTopic1Items) {
								name += filterItem.getName() + "،";
							}
							name=name.substring(0, name.length()-1);
							name += ") ";
						} 
					}
					name=name.substring(0, name.length()-1);

				}
			}
			
		}
		
		return name;
	}
	
	private static void makeTree(Map<Long, Set<FilterItem>> tree, String topics, long moduleId) {
		if(topics==null){
			return;
		}
		List<FilterItem> topicItems = CacheHelper.getCahceListItems().get(new CacheKey(CacheKeyTypes.Topic, moduleId));
		if (topicItems == null) {
			return;
		}
		String[] topicIs = topics.trim().split(",");
		if (topicIs.length > 0)
			try {
				for (String string : topicIs) {
					long topicId = Long.parseLong(string);
					boolean isTopicFound = false;
					for (FilterItem topicItem : topicItems) {
						if (topicId == topicItem.getId().longValue()) {
							if (tree.get(topicId) == null) {
								Set<FilterItem> items = new HashSet<FilterItem>();
								tree.put(topicId, items);
							}
							if (tree.get(moduleId) == null) {
								Set<FilterItem> items = new HashSet<FilterItem>();
								items.add(topicItem);
								tree.put(moduleId, items);
							} else {
								Set<FilterItem> items = tree.get(moduleId);
								items.add(topicItem);
								tree.put(moduleId, items);
							}
							isTopicFound = true;
							break;
						}
					}
					if (isTopicFound == false) {
						boolean isSubTopicFound = false;
						boolean isSubTopic1Found = false;
						for (FilterItem topicItem : topicItems) {
							List<FilterItem> subTopicItems = CacheHelper.getCahceListItems().get(new CacheKey(CacheKeyTypes.SubTopic, topicItem.getId()));
							if (subTopicItems == null) {
								continue;
							}
							if (isSubTopicFound == true) {
								break;
							}
							if (isSubTopic1Found == true) {
								break;
							}
							for (FilterItem subTopicItem : subTopicItems) {
								if (subTopicItem.getId().longValue() == topicId) {
									if (tree.get(topicId) == null) {
										Set<FilterItem> items = new HashSet<FilterItem>();
										tree.put(topicId, items);
									}

									if (tree.get(topicItem.getId()) == null) {
										Set<FilterItem> items = new HashSet<FilterItem>();
										items.add(subTopicItem);
										tree.put(topicItem.getId(), items);
									} else {
										Set<FilterItem> items = tree.get(topicItem.getId());
										items.add(subTopicItem);
										tree.put(topicItem.getId(), items);
									}

									if (tree.get(moduleId) == null) {
										Set<FilterItem> items = new HashSet<FilterItem>();
										items.add(topicItem);
										tree.put(moduleId, items);
									} else {
										Set<FilterItem> items = tree.get(moduleId);
										items.add(topicItem);
										tree.put(moduleId, items);
									}
									isSubTopicFound = true;
									break;
								}
							}

							if (isSubTopicFound == false) {
								for (FilterItem subTopicItem : subTopicItems) {
									List<FilterItem> subTopic1Items = CacheHelper.getCahceListItems().get(
											new CacheKey(CacheKeyTypes.SubTopic1, subTopicItem.getId()));
									if (subTopic1Items == null) {
										continue;
									}
									for (FilterItem subTopic1Item : subTopic1Items) {
										if (subTopic1Item.getId().longValue() == topicId) {

											if (tree.get(topicId) == null) {
												Set<FilterItem> items = new HashSet<FilterItem>();
												tree.put(topicId, items);
											}

											if (tree.get(subTopicItem.getId()) == null) {
												Set<FilterItem> items = new HashSet<FilterItem>();
												items.add(subTopic1Item);
												tree.put(subTopicItem.getId(), items);
											} else {
												Set<FilterItem> items = tree.get(subTopicItem.getId());
												items.add(subTopic1Item);
												tree.put(subTopicItem.getId(), items);
											}

											if (tree.get(topicItem.getId()) == null) {
												Set<FilterItem> items = new HashSet<FilterItem>();
												items.add(subTopicItem);
												tree.put(topicItem.getId(), items);
											} else {
												Set<FilterItem> items = tree.get(topicItem.getId());
												items.add(subTopicItem);
												tree.put(topicItem.getId(), items);
											}

											if (tree.get(moduleId) == null) {
												Set<FilterItem> items = new HashSet<FilterItem>();
												items.add(topicItem);
												tree.put(moduleId, items);
											} else {
												Set<FilterItem> items = tree.get(moduleId);
												items.add(topicItem);
												tree.put(moduleId, items);
											}
											isSubTopic1Found = true;
											break;
										}
									}
									if (isSubTopic1Found == true) {
										break;
									}

								}
							}
						}
					}

				}
			} catch (Exception e) {
				// TODO: handle exception
			}

	}
	
	private static List<FilterItem> sort(Set<FilterItem> items){
		List<FilterItem> sortedItems=new ArrayList<FilterItem>();
		
		for (FilterItem filterItem : items) {
			boolean isInserted=false;
			for (int i = 0; i < sortedItems.size(); i++) {
				if(filterItem.getId().longValue()<sortedItems.get(i).getId().longValue()){
					sortedItems.add(i, filterItem);
					isInserted=true;
					break;
				}
			}
			if(isInserted==false){
				sortedItems.add(filterItem);
			}
		}
		return sortedItems;
	}

}
