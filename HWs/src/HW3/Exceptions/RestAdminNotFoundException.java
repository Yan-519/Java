package HW3.Exceptions;

import HW3.DataObjects.RestAdmin;

public class RestAdminNotFoundException extends CodedNotFoundException {

	public RestAdminNotFoundException(int code) {
		super(code, RestAdmin.class.getSimpleName());
	}

}
