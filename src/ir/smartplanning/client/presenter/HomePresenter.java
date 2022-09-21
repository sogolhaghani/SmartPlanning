package ir.smartplanning.client.presenter;

import ir.smartplanning.client.CacheHelper;
import ir.smartplanning.client.CacheKey;
import ir.smartplanning.client.CacheKeyTypes;
import ir.smartplanning.client.FilterItem;
import ir.smartplanning.client.MyGateKeeper;
import ir.smartplanning.client.place.NameTokens;
import ir.smartplanning.shared.MyRequestFactory.UserRequestContext;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

public class HomePresenter extends
		Presenter<HomePresenter.MyView, HomePresenter.MyProxy> {

	public interface MyView extends View {
		// public Label getHome();

		// public Label getExamResult();

		public Label getConsultation();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> content_slot = new Type<RevealContentHandler<?>>();
	public static final Type<RevealContentHandler<?>> menuBar_slot = new Type<RevealContentHandler<?>>();
	// private final PlaceManager placeManager;
	private final Provider<UserRequestContext> userRequestContextProvider;

	@ProxyCodeSplit
	@NameToken(NameTokens.home)
	@UseGatekeeper(MyGateKeeper.class)
	public interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	@Inject
	public HomePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, /* PlaceManager placeManager, */
			Provider<UserRequestContext> userRequestContextProvider) {
		super(eventBus, view, proxy);
		getView().getConsultation().addStyleName("selected");
		// this.placeManager = placeManager;
		this.userRequestContextProvider = userRequestContextProvider;
	}

	@Override
	protected void revealInParent() {
		RevealRootLayoutContentEvent.fire(this, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		onMenuHandler();
	}

	@Override
	protected void onReset() {
		super.onReset();
		Element element = Document.get().getElementById("root");
		if (element.hasParentElement()) {
			Element parent = element.getParentElement();
			if (parent.hasParentElement()) {
				Element parentOfParent = parent.getParentElement();
				parentOfParent.setAttribute("style", "overflow:inherit");
			}
		}
	}

	private void onMenuHandler() {
		// getView().getHome().addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// getView().getHome().addStyleName("selected");
		// getView().getConsultation().removeStyleName("selected");
		// // getView().getExamResult().removeStyleName("selected");
		// PlaceRequest request = new PlaceRequest.Builder().nameToken(
		// NameTokens.main).build();
		// placeManager.revealPlace(request, true);
		// }
		// });

		// getView().getConsultation().addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// // getView().getHome().removeStyleName("selected");
		// getView().getConsultation().addStyleName("selected");
		// // getView().getExamResult().removeStyleName("selected");
		// PlaceRequest request = new PlaceRequest.Builder().nameToken(
		// NameTokens.consultation).build();
		// placeManager.revealPlace(request, true);
		//
		// }
		// });

		// getView().getExamResult().addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// getView().getHome().removeStyleName("selected");
		// getView().getConsultation().removeStyleName("selected");
		// getView().getExamResult().addStyleName("selected");
		//
		// PlaceRequest request = new PlaceRequest.Builder().nameToken(
		// NameTokens.examresult).build();
		// placeManager.revealPlace(request, true);
		//
		// }
		// });

	}

	@Override
	protected void onReveal() {
		super.onReveal();
		loadingBookInfos();
	}

	private void loadingBookInfos() {
		final Storage stockStore = Storage.getLocalStorageIfSupported();
		if (stockStore.getItem(MyGateKeeper.getUser().getId().toString()) != null) {
			String storge = stockStore.getItem(
					MyGateKeeper.getUser().getId().toString()).trim();
			List<String> records = getFilterItems(storge);
			for (String string : records) {
				addToCacheHelper(string);
			}
		} else
			getBookInfos(stockStore);
	}

	private List<String> getFilterItems(String rawData) {
		if (rawData == null || rawData.length() == 0)
			return null;
		String[] records = rawData.split("@");
		List<String> result = new LinkedList<String>();
		for (String string : records) {
			result.add(string);
		}
		return result;
	}

	private void getBookInfos(final Storage stockStore) {
		userRequestContextProvider.get().getBookInfo()
				.fire(new Receiver<List<String>>() {

					@Override
					public void onSuccess(List<String> response) {
						if (response == null) {
							return;
						}
						StringBuilder sb = new StringBuilder();
						for (String string : response) {
							if (string == null) {
								continue;
							}
							if (sb.length() > 0) {
								sb.append("@");
							}
							sb.append(string);
							addToCacheHelper(string);
						}

						stockStore.setItem(MyGateKeeper.getUser().getId()
								.toString(), sb.toString());
					}

					@Override
					public void onFailure(ServerFailure error) {
						super.onFailure(error);
					}

				});
	}

	protected void addToCacheHelper(String string) {
		String[] text = string.split("\\$");
		byte type = -1;
		Long parentId = -1L;
		if (text[0].length() == 0) {
			return;
		}
		type = Byte.parseByte(text[0]);
		CacheKey cacheKey = null;
		if (text[1].length() > 0) {
			parentId = Long.parseLong(text[1]);
		}
		String[] filterItems = null;
		 if (type == CacheKeyTypes.Module.getId()) {
			filterItems = text[2].split("~");
			cacheKey = new CacheKey(CacheKeyTypes.getEnum(type), parentId,
					MyGateKeeper.getUser().getGrade(),
					MyGateKeeper.getUser().getMajorId());
		} else {
			filterItems = text[2].split("~");
			cacheKey = new CacheKey(CacheKeyTypes.getEnum(type), parentId);
		}
		LinkedList<FilterItem> items = new LinkedList<FilterItem>();
		if (filterItems != null)
			for (int i = 0; i < filterItems.length; i += 2) {
				try {
					Long id = Long.parseLong(filterItems[i]);
					FilterItem item = new FilterItem(id, filterItems[i + 1]);
					items.add(item);
				} catch (Exception e) {
				}
			}
		CacheHelper.getCahceListItems().put(cacheKey, items);
		CacheHelper.setReady(true);
	}

}
