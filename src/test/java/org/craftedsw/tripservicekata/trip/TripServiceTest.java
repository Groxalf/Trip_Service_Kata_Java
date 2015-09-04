package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TripServiceTest {

    @Test(expected = UserNotLoggedInException.class)
    public void should_raise_an_exception_when_user_is_not_logged() throws Exception {
        TripService service = new TripServiceFake();
        service.getTripsByUser(new User());
    }

    @Test
    public void should_return_empty_trip_list_when_loggedUser_is_not_a_friend_of_user() {
        User user = new User();
        User loggedUser = new User();
        TripService service = new TripServiceFake(loggedUser);
        assertThat(service.getTripsByUser(user), is(Arrays.asList()));
    }

    public class TripServiceFake extends TripService {

        private User user;

        public TripServiceFake () {
            user = null;
        }

        public TripServiceFake(User user) {
            this.user = user;
        }

        @Override
        protected User getLoggedUser() {
            return user;
        }
    }
	
}
