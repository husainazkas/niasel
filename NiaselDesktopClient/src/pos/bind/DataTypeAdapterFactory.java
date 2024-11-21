/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import pos.entity.AuthToken;
import pos.entity.Role;
import pos.entity.User;
import pos.model.ApiFailure;
import pos.model.ApiResponse;
import pos.model.LoginResponse;

/**
 *
 * @author husainazkas
 */
public class DataTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();

        if (ApiFailure.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new ApiFailureTypeAdapter().nullSafe();
        }

        if (ApiResponse.class.isAssignableFrom(rawType)) {
            // Extract the generic type parameter (e.g., AuthTokenModel, UserModel)
            TypeToken<?> dataTypeToken = TypeToken.get(((ParameterizedType) type.getType()).getActualTypeArguments()[0]);

            // Create a TypeAdapter for the inner type (data field)
            TypeAdapter<?> dataAdapter = gson.getAdapter(dataTypeToken);

            return (TypeAdapter<T>) new ApiResponseTypeAdapter<>(dataAdapter);
        }

        if (LoginResponse.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new LoginResponseTypeAdapter(gson).nullSafe();
        }

        if (AuthToken.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new AuthTokenTypeAdapter().nullSafe();
        }

        if (User.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new UserTypeAdapter(gson).nullSafe();
        }

        if (Role.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new RoleTypeAdapter(gson).nullSafe();
        }

        return null;
    }

    private static class ApiFailureTypeAdapter extends TypeAdapter<ApiFailure> {

        @Override
        public void write(JsonWriter writer, ApiFailure t) throws IOException {
            writer.beginObject();
            writer.name("status").value(t.getStatus());
            writer.name("message").value(t.getMessage());
            writer.endObject();
        }

        @Override
        public ApiFailure read(JsonReader reader) throws IOException {
            ApiFailure failure = new ApiFailure();
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case "status":
                        failure.setStatus(reader.nextString());
                        break;
                    case "message":
                        failure.setMessage(reader.nextString());
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }

            reader.endObject();
            return failure;
        }

    }

    private static class ApiResponseTypeAdapter<T extends Object> extends TypeAdapter<ApiResponse<T>> {

        private final TypeAdapter<T> dataTypeAdapter;

        public ApiResponseTypeAdapter(TypeAdapter<T> dataTypeAdapter) {
            this.dataTypeAdapter = dataTypeAdapter;
        }

        @Override
        public void write(JsonWriter writer, ApiResponse<T> t) throws IOException {
            writer.beginObject();
            writer.name("status").value(t.getStatus());
            writer.name("message").value(t.getMessage());
            writer.name("data");

            if (t.getData() == null) {
                writer.nullValue();
            } else {
                dataTypeAdapter.write(writer, t.getData());
            }
            writer.endObject();
        }

        @Override
        public ApiResponse<T> read(JsonReader reader) throws IOException {
            ApiResponse<T> response = new ApiResponse<>();
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case "status":
                        response.setStatus(reader.nextString());
                        break;
                    case "message":
                        response.setMessage(reader.nextString());
                        break;
                    case "data":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.nextNull();
                            response.setData(null);
                        } else {
                            response.setData(dataTypeAdapter.read(reader));
                        }
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }

            reader.endObject();
            return response;
        }

    }

    private static class LoginResponseTypeAdapter extends TypeAdapter<LoginResponse> {

        private final Gson gson;

        private LoginResponseTypeAdapter(Gson gson) {
            this.gson = gson;
        }

        @Override
        public void write(JsonWriter writer, LoginResponse t) throws IOException {
            writer.beginObject();

            writer.name("token");
            gson.getAdapter(AuthToken.class).write(writer, t.getToken());

            writer.name("user");
            gson.getAdapter(User.class).write(writer, t.getUser());

            writer.endObject();
        }

        @Override
        public LoginResponse read(JsonReader reader) throws IOException {
            LoginResponse response = new LoginResponse();
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case "token":
                        response.setToken(gson.getAdapter(AuthToken.class).read(reader));
                        break;
                    case "user":
                        response.setUser(gson.getAdapter(User.class).read(reader));
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }

            reader.endObject();
            return response;
        }

    }

    private static class AuthTokenTypeAdapter extends TypeAdapter<AuthToken> {

        @Override
        public void write(JsonWriter writer, AuthToken t) throws IOException {
            writer.beginObject();
            writer.name("access_token").value(t.getAccessToken());
            writer.name("refresh_token").value(t.getRefreshToken());
            writer.endObject();
        }

        @Override
        public AuthToken read(JsonReader reader) throws IOException {
            AuthToken authToken = new AuthToken();
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case "access_token":
                        authToken.setAccessToken(reader.nextString());
                        break;
                    case "refresh_token":
                        authToken.setRefreshToken(reader.nextString());
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }

            reader.endObject();
            return authToken;
        }

    }

    private static class UserTypeAdapter extends TypeAdapter<User> {

        private final Gson gson;

        private UserTypeAdapter(Gson gson) {
            this.gson = gson;
        }

        @Override
        public void write(JsonWriter writer, User t) throws IOException {
            writer.beginObject();
            writer.name("id").value(t.getId());
            writer.name("first_name").value(t.getFullName());
            writer.name("last_name").value(t.getLastName());
            writer.name("is_active").value(t.getIsActive());
            writer.name("is_deleted").value(t.getIsDeleted());

            writer.name("role");
            gson.getAdapter(Role.class).write(writer, t.getRole());

            writer.name("updated_at");
            gson.getAdapter(Date.class).write(writer, t.getUpdatedAt());

            writer.name("updated_by").value(t.getUpdatedBy());

            writer.name("created_at");
            gson.getAdapter(Date.class).write(writer, t.getCreatedAt());

            writer.name("created_by").value(t.getCreatedBy());

            writer.endObject();
        }

        @Override
        public User read(JsonReader reader) throws IOException {
            User user = new User();
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case "id":
                        user.setId(reader.nextLong());
                        break;
                    case "first_name":
                        user.setFirstName(reader.nextString());
                        break;
                    case "last_name":
                        user.setLastName(reader.nextString());
                        break;
                    case "is_active":
                        user.setIsActive(reader.nextBoolean());
                        break;
                    case "is_deleted":
                        user.setIsDeleted(reader.nextBoolean());
                        break;
                    case "role":
                        user.setRole(gson.getAdapter(Role.class).read(reader));
                        break;
                    case "updated_at":
                        user.setUpdatedAt(gson.getAdapter(Date.class).read(reader));
                        break;
                    case "updated_by":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.nextNull();
                            user.setUpdatedBy(null);
                        } else {
                            user.setUpdatedBy(reader.nextLong());
                        }
                        break;
                    case "created_at":
                        user.setCreatedAt(gson.getAdapter(Date.class).read(reader));
                        break;
                    case "created_by":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.nextNull();
                            user.setCreatedBy(null);
                        } else {
                            user.setCreatedBy(reader.nextLong());
                        }
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }

            reader.endObject();
            return user;
        }

    }

    private static class RoleTypeAdapter extends TypeAdapter<Role> {

        private final Gson gson;

        private RoleTypeAdapter(Gson gson) {
            this.gson = gson;
        }

        @Override
        public void write(JsonWriter writer, Role t) throws IOException {
            writer.beginObject();

            writer.name("id").value(t.getId());
            writer.name("name").value(t.getName());
            writer.name("c_u_d_master").value(t.getIsCanCreateUpdateDeleteMaster());
            writer.name("c_u_product").value(t.getIsCanUpdateProduct());
            writer.name("d_product").value(t.getIsCanDeleteProduct());
            writer.name("r_users").value(t.getIsCanReadUsers());
            writer.name("c_u_user").value(t.getIsCanCreateUpdateUser());
            writer.name("d_user").value(t.getIsCanDeleteUser());
            writer.name("c_purchase").value(t.getIsCanCreatePurchase());
            writer.name("is_active").value(t.getIsActive());
            writer.name("is_deleted").value(t.getIsDeleted());

            writer.name("updated_at");
            gson.getAdapter(Date.class).write(writer, t.getUpdatedAt());

            writer.name("updated_by").value(t.getUpdatedBy());

            writer.name("created_at");
            gson.getAdapter(Date.class).write(writer, t.getCreatedAt());

            writer.name("created_by").value(t.getCreatedBy());

            writer.endObject();
        }

        @Override
        public Role read(JsonReader reader) throws IOException {
            Role role = new Role();
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case "id":
                        role.setId(reader.nextLong());
                        break;
                    case "name":
                        role.setName(reader.nextString());
                        break;
                    case "c_u_d_master":
                        role.setIsCanCreateUpdateDeleteMaster(reader.nextBoolean());
                        break;
                    case "c_u_product":
                        role.setIsCanUpdateProduct(reader.nextBoolean());
                        break;
                    case "d_product":
                        role.setIsCanDeleteProduct(reader.nextBoolean());
                        break;
                    case "r_users":
                        role.setIsCanReadUsers(reader.nextBoolean());
                        break;
                    case "c_u_user":
                        role.setIsCanCreateUpdateUser(reader.nextBoolean());
                        break;
                    case "d_user":
                        role.setIsCanDeleteUser(reader.nextBoolean());
                        break;
                    case "c_purchase":
                        role.setIsCanCreatePurchase(reader.nextBoolean());
                        break;
                    case "is_active":
                        role.setIsActive(reader.nextBoolean());
                        break;
                    case "is_deleted":
                        role.setIsDeleted(reader.nextBoolean());
                        break;
                    case "updated_at":
                        role.setUpdatedAt(gson.getAdapter(Date.class).read(reader));
                        break;
                    case "updated_by":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.nextNull();
                            role.setUpdatedBy(null);
                        } else {
                            role.setUpdatedBy(reader.nextLong());
                        }
                        break;
                    case "created_at":
                        role.setCreatedAt(gson.getAdapter(Date.class).read(reader));
                        break;
                    case "created_by":
                        if (reader.peek() == JsonToken.NULL) {
                            reader.nextNull();
                            role.setCreatedBy(null);
                        } else {
                            role.setCreatedBy(reader.nextLong());
                        }
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }

            reader.endObject();
            return role;
        }
    }
}
