package com.app.pharmacy.service;

import com.app.pharmacy.domain.dto.employee.CreateEmployeeRequest;
import com.app.pharmacy.exception.CustomResponseException;
import com.app.pharmacy.exception.ErrorCode;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakAdminService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public String createUser(CreateEmployeeRequest request) {

        UserRepresentation user = getUserRepresentation(request);

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(user);

        if (response.getStatus() == 409) {
            throw new CustomResponseException(ErrorCode.USER_EXISTED);
        }

        if (response.getStatus() != 201) {
            throw new RuntimeException();
        }

        List<UserRepresentation> users = keycloak.realm(realm)
                .users()
                .search(request.username());

        if (users.isEmpty()) {
            throw new RuntimeException();
        }
        return users.get(0).getId();
    }

    private static UserRepresentation getUserRepresentation(CreateEmployeeRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEmail(request.mail());
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());
        credential.setTemporary(false);

        user.setCredentials(Collections.singletonList(credential));
        return user;
    }
}
