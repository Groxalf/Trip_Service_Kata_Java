package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

public class TripServiceTest {

    @Test(expected = UserNotLoggedInException.class)
    public void should_raise_an_exception_when_user_is_not_logged() throws Exception {
        TripService service = new TripServiceFake();
        service.getTripsByUser(new User());
    }

    public class TripServiceFake extends TripService {

        @Override
        protected User getLoggedUser() {
            return null;
        }
    }
	
}
