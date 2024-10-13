package com.app.pharmacy.service;

import com.app.pharmacy.domain.dto.employee.CreateEmployeeRequest;
import com.app.pharmacy.exception.CustomResponseException;
import com.app.pharmacy.exception.ErrorCode;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
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
        String userId = users.get(0).getId();
        assignRoleToUser(userId, request.role().toString());
        return userId;
    }

    public void deleteUser(String userId) {
        RealmResource realmResource = keycloak.realm(realm);

        try {
            realmResource.users().delete(userId);
        } catch (NotFoundException ex) {
            throw new CustomResponseException(ErrorCode.USER_NOT_EXIST);
        } catch (Exception e) {
            throw new RuntimeException("User deletion failed", e);
        }
    }

    private static UserRepresentation getUserRepresentation(CreateEmployeeRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEmail(request.mail());
        user.setEnabled(true);
        user.setEmailVerified(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());
        credential.setTemporary(false);

        user.setCredentials(Collections.singletonList(credential));
        return user;
    }

    private void assignRoleToUser(String userId, String roleName) {
        RealmResource realmResource = keycloak.realm(realm);
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId("pharmacy-management-system").get(0);

        ClientResource clientResource = realmResource.clients().get(clientRepresentation.getId());

        RoleRepresentation clientRole = clientResource.roles().get(roleName).toRepresentation();
        realmResource.users().get(userId).roles().clientLevel(clientResource.toRepresentation().getId())
                .add(Collections.singletonList(clientRole));
    }
}
