/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.service.remote;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.function.Supplier;
import pos.entity.AuthToken;
import pos.entity.User;
import pos.model.ApiFailure;
import pos.model.ApiResponse;
import pos.model.LoginResponse;
import pos.params.LoginParams;
import pos.utils.JsonParser;

/**
 *
 * @author husainazkas
 */
public class RemoteAuthService extends BaseRemoteService {

    public RemoteAuthService(Supplier<Option<AuthToken>> authTokenGetter) {
        super(authTokenGetter);
    }

    public Either<Exception, Tuple2<AuthToken, User>> login(LoginParams params) {
        try {
            var response = post("/v1/auth/login", params);
            if (response.statusCode() >= 400) {
                return Either.left(JsonParser.fromJson(response.body(), ApiFailure.class));
            }

            Type loginResponseType = new TypeToken<ApiResponse<LoginResponse>>() {
            }.getType();

            ApiResponse<LoginResponse> token = JsonParser.fromJson(response.body(), loginResponseType);
            return Either.right(new Tuple2(token.getData().getToken(), token.getData().getUser()));
        } catch (IOException | InterruptedException | JsonSyntaxException ex) {
            return Either.left(ex);
        }
    }
}
