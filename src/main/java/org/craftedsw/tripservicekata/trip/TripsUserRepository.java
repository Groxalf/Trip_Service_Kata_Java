package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

import java.util.List;

public class TripsUserRepository {

    public List<Trip> getTripsOf(User user) {
        return TripDAO.findTripsByUser(user);
    }

}
