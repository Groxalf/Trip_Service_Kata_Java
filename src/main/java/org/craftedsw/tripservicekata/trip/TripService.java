package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSessionService;

import java.util.List;

import static java.util.Collections.emptyList;

public class TripService {

    private final UserSessionService userSessionService;
    private final TripsUserRepository tripsUserRepository;

    public TripService(UserSessionService userSessionService, TripsUserRepository tripsUserRepository) {
        this.userSessionService = userSessionService;
        this.tripsUserRepository = tripsUserRepository;
    }

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        if (user.isFriendOf(userSessionService.getLoggedUser())) return tripsUserRepository.getTripsOf(user);
        return emptyList();
    }

}
