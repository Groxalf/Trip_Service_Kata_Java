package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.LoggedUserService;
import org.craftedsw.tripservicekata.user.User;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    private final LoggedUserService loggedUserService;
    private final TripsUserRepository tripsUserRepository;

    public TripService(LoggedUserService loggedUserService, TripsUserRepository tripsUserRepository) {
        this.loggedUserService = loggedUserService;
        this.tripsUserRepository = tripsUserRepository;
    }

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        if (user.isFriendOf(loggedUserService.getLoggedUser())) return tripsUserRepository.getTripsOf(user);
        return new ArrayList<>();
    }

}
