package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.UserSessionService;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripServiceTest {

    private TripService service;
    private User user;
    private User loggedUser;
    private TripsUserRepository tripsUserRepository;
    private UserSessionService userSessionService;
    private Trip trip;

    @Before
    public void setUp() throws Exception {
        loggedUser = new User();
        user = new User();
        trip = new Trip();
        userSessionService = mock(UserSessionService.class);
        tripsUserRepository = mock(TripsUserRepository.class);
        service = new TripService(userSessionService, tripsUserRepository);
    }

    @Test(expected = UserNotLoggedInException.class)
    public void should_raise_an_exception_when_user_is_not_logged() throws Exception {
        GivenANotLoggedUser();
        service.getTripsByUser(user);
    }

    @Test
    public void should_return_empty_trip_list_when_loggedUser_is_not_a_friend_of_user() {
        GivenALoggedUser(loggedUser);
        GivenUserWhoIsNotFriendOfLoggedUser();
        assertThat(service.getTripsByUser(user), is(Arrays.asList()));
    }

    @Test
    public void should_return_list_with_the_trips_of_user_when_is_friend_of_loggedUser() {
        GivenALoggedUser(loggedUser);
        GivenAUserFriendOfLoggedUserWithTrips(Arrays.asList(trip));
        when(tripsUserRepository.getTripsOf(user)).thenReturn(Arrays.asList(trip));
        assertThat(service.getTripsByUser(user), is(Arrays.asList(trip)));
    }

    private void GivenALoggedUser(User user) {
        when(userSessionService.getLoggedUser()).thenReturn(user);
    }


    private void GivenANotLoggedUser() {
        when(userSessionService.getLoggedUser()).thenThrow(new UserNotLoggedInException());
    }

    private void GivenUserWhoIsNotFriendOfLoggedUser() {
        when(tripsUserRepository.getTripsOf(user)).thenReturn(new ArrayList<>());
    }

    private void GivenAUserFriendOfLoggedUserWithTrips(List<Trip> trips) {
        user.addFriend(loggedUser);
        user.addTrip(trip);
        when(tripsUserRepository.getTripsOf(user)).thenReturn(trips);
    }

}
