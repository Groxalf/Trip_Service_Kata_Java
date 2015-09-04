package org.craftedsw.tripservicekata.user;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;

public class UserSessionService {

    public User getLoggedUser() throws UserNotLoggedInException {
        User user = UserSession.getInstance().getLoggedUser();
        if (user == null) throw new UserNotLoggedInException();
        return user;
    }

}
