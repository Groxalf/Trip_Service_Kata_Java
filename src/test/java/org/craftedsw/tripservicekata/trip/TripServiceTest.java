package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TripServiceTest {

    @Test(expected = UserNotLoggedInException.class)
    public void should_raise_an_exception_when_user_is_not_logged() throws Exception {
        TripService service = new TripServiceFake();
        service.getTripsByUser((User) new User());
    }

    @Test
    public void should_return_empty_trip_list_when_loggedUser_is_not_a_friend_of_user() {
        User user = new User();
        User loggedUser = new User();
        TripService service = new TripServiceFake(loggedUser);
        assertThat(service.getTripsByUser((User) user), is(Arrays.asList()));
    }


    @Test
    public void should_return_list_with_the_trips_of_user_when_is_friend_of_loggedUser() {
        User user = new User();
        User loggedUser = new User();
        Trip trip = new Trip();
        user.addFriend(loggedUser);
        user.addTrip(trip);
        TripServiceFake serviceFake = new TripServiceFake(loggedUser);
        assertThat(serviceFake.getTripsByUser(user), is(Arrays.asList(trip)));
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

        @Override
        protected List<Trip> getTripsOf(User user) {
            return user.trips();
        }
    }
	
}
