package ir.smartplanning.client;

import ir.smartplanning.shared.proxies.nonpersists.UserItemProxy;

import com.gwtplatform.mvp.client.proxy.Gatekeeper;

public class MyGateKeeper implements Gatekeeper{

	private static UserItemProxy currentUser;
	
	public MyGateKeeper() {
	}
	
	public static void setUser(UserItemProxy userItemProxy){
		currentUser=userItemProxy;
	}
	
	public static UserItemProxy getUser(){
		return currentUser;
	}
	
	public static void logout() {
		currentUser = null;
	}

	@Override
	public boolean canReveal() {
		if(currentUser==null){
			return false;
		}
		return true;
	}

	public static boolean isInExam() {
		// TODO Auto-generated method stub
		return false;
	}

	public static void setInExam(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public static void setCurrentExamRequestId(Long refId) {
		// TODO Auto-generated method stub
		
	}

}
