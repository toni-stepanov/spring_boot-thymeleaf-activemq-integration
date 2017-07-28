package com.pp.service;

import com.pp.domain.user.CurrentUser;

/**
 * Created by astepanov
 */
public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);

}
