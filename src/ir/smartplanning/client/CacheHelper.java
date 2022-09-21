package ir.smartplanning.client;



	import java.util.HashMap;
	import java.util.HashSet;
	import java.util.List;

	public class CacheHelper {
		public static void clear() {
			cahceListItems.clear();
		}

		private static HashMap<CacheKey, List<FilterItem>> cahceListItems = new HashMap<CacheKey, List<FilterItem>>();
		private static boolean ready;
		
		
		public static boolean isReady() {
			return ready;
		}
		
		public static void setReady(boolean ready) {
			CacheHelper.ready = ready;
		}
		
		public static HashMap<CacheKey, List<FilterItem>> getCahceListItems() {
			return cahceListItems;
		}


		public static void setCache( HashMap<CacheKey, List<FilterItem>> fromStorage){
			cahceListItems=fromStorage;
		}
		public static HashSet<Long> getChildTopic(HashMap<CacheKey, List<FilterItem>> cache,List<Long> topicIds) {
			HashSet<Long> initateSet = new HashSet<Long>();
			HashSet<Long> resultSet = new HashSet<Long>();
			for (Long long1 : topicIds) {
				initateSet.add(long1);
			}
			while (initateSet.size() > 0) {
				Long topicId = initateSet.iterator().next();
				initateSet.remove(topicId);
				resultSet.add(topicId);
				List<FilterItem> cacheItems =cache.get(new CacheKey(CacheKeyTypes.SubTopic, topicId));
				if (cacheItems != null) {
					for (FilterItem filterItem : cacheItems) {
						initateSet.add(filterItem.getId());
					}
				} else {
					List<FilterItem> cacheItems1 = cache.get(new CacheKey(CacheKeyTypes.SubTopic1, topicId));
					if (cacheItems1 != null) {
						for (FilterItem filterItem : cacheItems1) {
							initateSet.add(filterItem.getId());
						}

					}
				}

			}
			return resultSet;
		}


//		public static Long getIdFromCache(CacheKeyTypes requestedType, Long refrenceId, byte referenceType) {
//			if (referenceType < requestedType.getId()) {
//				return null;
//			}
//			long id = refrenceId;
//			byte type = referenceType;
//			CacheKey key = null;// new
//								// CacheKey(CacheKeyTypes.getEnum(referenceType),
//								// id);
//			List<FilterItem> items = null;// CacheHelper.getCahceListItems().get(key);
//			while (type > requestedType.getId() && type > CacheKeyTypes.Module.getId()) {
//				key = new CacheKey(CacheKeyTypes.getEnum(type), id);
//				items = CacheHelper.getAdminCahceListItems().get(key);
//
//				type--;
//
//				id = Long.parseLong(items.get(1).getName());
//
//			}
//			if (requestedType.getId() == type) {
//				return id;
//			}
//			key = new CacheKey(CacheKeyTypes.getEnum(type), id);
//			items = CacheHelper.getAdminCahceListItems().get(key);
//			if (CacheKeyTypes.ModuleGroup.getId() == requestedType.getId()) {
//				return items.get(0).getId();
//			} else if (CacheKeyTypes.Major.getId() == requestedType.getId()) {
//				String[] majors = items.get(1).getName().split("\\$");
//				for (int i = 0; i < majors.length; i++) {
//					if (majors[i].trim().length() > 0) {
//						return Long.parseLong(majors[i]);
//					}
//				}
//			}
//			CommonLogger.log(logger, Level.SEVERE, "reference not found!");
//			return null;
//		}

}
