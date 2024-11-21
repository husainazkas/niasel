/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.controller;

import io.vavr.control.Option;
import java.util.Arrays;
import java.util.function.Consumer;
import org.apache.commons.codec.digest.DigestUtils;
import pos.entity.User;
import pos.params.LoginParams;
import pos.service.local.LocalAuthService;
import pos.service.remote.RemoteAuthService;

/**
 *
 * @author husainazkas
 */
public class AuthController {

    private final RemoteAuthService remoteService;
    private final LocalAuthService localService;

    private boolean isSubmitting = false;
    private Option<User> currentUser = Option.none();

    public AuthController() {
        localService = new LocalAuthService();
        remoteService = new RemoteAuthService(() -> localService.loadToken());

        currentUser = localService.loadUser();
    }

    public boolean getIsSubmitting() {
        return isSubmitting;
    }

    public Option<User> getCurrentUser() {
        return currentUser;
    }

    public void login(String username, char[] password, Consumer<Exception> onFailure, Consumer<User> onSuccess) {
        isSubmitting = true;

        final String encodedUsername = DigestUtils.sha1Hex(username);
        final String encodedPass = DigestUtils.sha256Hex(String.valueOf(password));

        var result = remoteService.login(new LoginParams(encodedUsername, encodedPass, "test"));
        isSubmitting = false;

        result.fold((e) -> {
            onFailure.accept(e);
            return null;
        }, (r) -> r.apply((t, u) -> Option.sequence(Arrays.asList(
                localService.saveToken(t),
                localService.saveUser(u)
        )).fold(() -> {
            currentUser = Option.of(u);
            onSuccess.accept(u);
            return null;
        }, (e) -> {
            onFailure.accept(e.head());
            return null;
        })
        ));
    }

    public void logout() {
        localService.clearSession();
        currentUser = Option.none();
        isSubmitting = false;
    }

}
