package ir.smartplanning.server;

import java.beans.Transient;
import java.io.Serializable;

import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * Generic @Locator for objects that extend DatastoreObject
 */
public class EntityLocator extends Locator<Serializable, Serializable> {

	@Override
	public Serializable create(Class<? extends Serializable> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Serializable find(Class<? extends Serializable> clazz,
			Serializable id) {
		return null;
	}

	@Override
	public Class<Serializable> getDomainType() {
		return null;
	}

	@Override
	public Long getId(Serializable domainObject) {
		return null;
	}

	@Override
	public Class<Serializable> getIdType() {
		return Serializable.class;
	}

	@Transient
	@Override
	public Object getVersion(Serializable domainObject) {
		return 0;
	}

}
