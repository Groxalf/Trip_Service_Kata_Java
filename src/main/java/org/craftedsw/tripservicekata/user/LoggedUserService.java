package org.craftedsw.tripservicekata.user;

public class LoggedUserService {

    public User getLoggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }

}
