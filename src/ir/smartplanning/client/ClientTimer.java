package ir.smartplanning.client;

import ir.smartplanning.shared.calender.PersianCalendar;

import java.util.Date;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class ClientTimer extends Timer {

	private static final int RUN_PERIOD = 1000;
	private static final int REFRESH_PERIOD = 120000;
	private static int remainingToRefresh = 0;
	private static Date serverTime = null;
	private static PersianCalendar persianServerTime = new PersianCalendar();
	private long prevTime;
	private int newMessageCount;
	private int newNotificationCount;

	PlaceManager placeManager;
	private EventBus eventBus;
	private boolean timeExpired = false;
	private byte timeExpiredCounter = 0;

	public int getNewMessageCount() {
		return newMessageCount;
	}

	public int getNewNotificationCount() {
		return newNotificationCount;
	}

	@Inject
	public ClientTimer(EventBus eventBus) {
		super();
		this.eventBus = eventBus;
	}

	public void start() {
		prevTime = new Date().getTime();
		scheduleRepeating(RUN_PERIOD);
	}

	public <H extends EventHandler> HandlerRegistration addHandler(
			Type<H> type, H handler) {
		return eventBus.addHandler(type, handler);
	}

	@Override
	public void run() {
		Long currentTimeMills = new Date().getTime();
		if (timeExpired) {
			if (timeExpiredCounter == 0) {
				timeExpired = false;
				refreshServerTime();
			} else {
				timeExpiredCounter--;
			}

		}
		if (currentTimeMills - prevTime > 3000) {
			timeExpired = true;
			timeExpiredCounter = 2;
		}
		prevTime = currentTimeMills;
		if (serverTime != null) {
			resetServerTime();
//			this.eventBus.fireEventFromSource(new CurrentTimeEvent(serverTime),
//					this);
		}
		remainingToRefresh -= RUN_PERIOD;
		if (remainingToRefresh <= 0) {
			remainingToRefresh = REFRESH_PERIOD;
		}
	}

	private void resetServerTime() {
		serverTime = new Date(serverTime.getTime() + RUN_PERIOD);

	}

	public void setTimeExpired(boolean timeExpired) {
		this.timeExpired = timeExpired;
	}

	private void refreshServerTime() {
		setServerTime(new Date());
//		requestFactoryProvider.get().notificationRequestContext()
//				.getServerTime().fire(new Receiver<Date>() {
//
//					@Override
//					public void onSuccess(Date response) {
//						setServerTime(response);
//					}
//
//					@Override
//					public void onFailure(ServerFailure error) {}
//
//				});

	}

	public void gotoHell() {
		MyGateKeeper.logout();
	}

	
	public static Date getServerTime() {
		return serverTime;
	}

	public static PersianCalendar getPersianServerTime() {
		return persianServerTime;
	}

	public static void setServerTime(Date time) {
		serverTime = time;
		persianServerTime.setTime(time);

	}
}