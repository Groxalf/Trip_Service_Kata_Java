package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.LoggedUserService;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripServiceTest {

    private TripService service;
    private User user;
    private User loggedUser;
    private TripsUserRepository tripsUserRepository;
    private LoggedUserService loggedUserService;

    @Before
    public void setUp() throws Exception {
        loggedUser = new User();
        user = new User();
        loggedUserService = mock(LoggedUserService.class);
        when(loggedUserService.getLoggedUser()).thenReturn(loggedUser);
        tripsUserRepository = mock(TripsUserRepository.class);
        service = new TripService(loggedUserService, tripsUserRepository);
    }

    @Test(expected = UserNotLoggedInException.class)
    public void should_raise_an_exception_when_user_is_not_logged() throws Exception {
        LoggedUserService notLoggedUserService = mock(LoggedUserService.class);
        when(notLoggedUserService.getLoggedUser()).thenReturn(null);
        TripService tripService = new TripService(notLoggedUserService, tripsUserRepository);
        tripService.getTripsByUser(user);
    }

    @Test
    public void should_return_empty_trip_list_when_loggedUser_is_not_a_friend_of_user() {
        when(tripsUserRepository.getTripsOf(user)).thenReturn(new ArrayList<>());
        assertThat(service.getTripsByUser(user), is(Arrays.asList()));
    }


    @Test
    public void should_return_list_with_the_trips_of_user_when_is_friend_of_loggedUser() {
        Trip trip = new Trip();
        user.addFriend(loggedUser);
        user.addTrip(trip);
        when(tripsUserRepository.getTripsOf(user)).thenReturn(Arrays.asList(trip));
        assertThat(service.getTripsByUser(user), is(Arrays.asList(trip)));
    }

    @Test
    public void collaborations() {
        Trip trip = new Trip();
        LoggedUserService loggedUserService = mock(LoggedUserService.class);
        when(loggedUserService.getLoggedUser()).thenReturn(loggedUser);
        TripsUserRepository tripsUserRepository = mock(TripsUserRepository.class);
        when(tripsUserRepository.getTripsOf(user)).thenReturn(Arrays.asList(trip));
        user.addFriend(loggedUser);
        user.addTrip(trip);
        TripService collaborationsService = new TripService(loggedUserService, tripsUserRepository);
        assertThat(collaborationsService.getTripsByUser(user), is(Arrays.asList(trip)));
    }

}
