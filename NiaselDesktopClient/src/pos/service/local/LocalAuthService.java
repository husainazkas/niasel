/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.service.local;

import com.google.gson.JsonSyntaxException;
import io.vavr.control.Option;
import java.util.prefs.Preferences;
import pos.entity.AuthToken;
import pos.entity.User;
import pos.exception.CryptoException;
import pos.utils.CryptoUtil;
import pos.utils.JsonParser;

/**
 *
 * @author husainazkas
 */
public class LocalAuthService {

    private static final String AUTH_KEY = "auth_token";
    private static final String USER_KEY = "auth_user";

    private static final Preferences authPrefs = Preferences.userNodeForPackage(AuthToken.class);

    public Option<AuthToken> loadToken() {
        String raw = authPrefs.get(AUTH_KEY, null);
        if (raw == null || raw.isBlank()) {
            return Option.none();
        }

        Option<String> decryptedToken = CryptoUtil.decrypt(raw);

        try {
            return decryptedToken.map((t) -> JsonParser.fromJson(t, AuthToken.class));
        } catch (JsonSyntaxException e) {
            return Option.none();
        }
    }

    public Option<User> loadUser() {
        String raw = authPrefs.get(USER_KEY, null);
        if (raw == null || raw.isBlank()) {
            return Option.none();
        }

        Option<String> decryptedUser = CryptoUtil.decrypt(raw);

        try {
            return decryptedUser.map((t) -> JsonParser.fromJson(t, User.class));
        } catch (JsonSyntaxException e) {
            return Option.none();
        }
    }

    public Option<Exception> saveToken(AuthToken value) {
        Option<String> encryptedToken = CryptoUtil.encrypt(JsonParser.toJson(value));
        return encryptedToken.fold(() -> Option.some(CryptoException.encryption()), (t) -> {
            try {
                authPrefs.put(AUTH_KEY, t);
            } catch (Exception e) {
                return Option.some(e);
            }
            return Option.none();
        });
    }

    public Option<Exception> saveUser(User value) {
        Option<String> encryptedUser = CryptoUtil.encrypt(JsonParser.toJson(value));
        return encryptedUser.fold(() -> Option.some(CryptoException.encryption()), (t) -> {
            try {
                authPrefs.put(USER_KEY, t);
            } catch (Exception e) {
                return Option.some(e);
            }
            return Option.none();
        });
    }

    public void clearSession() {
        authPrefs.remove(AUTH_KEY);
        authPrefs.remove(USER_KEY);
    }
}
