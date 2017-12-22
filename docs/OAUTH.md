# About OAuth2

The OAuth 2.0 authorization framework enables a third-party application to
obtain limited access to an HTTP service, either on behalf of a resource owner
by orchestrating an approval interaction between the resource owner and the HTTP
service, or by allowing the third-party application to obtain access on its own
behalf.

# Configuration

The Oauth2 security is disabled by default in development enviroment, this
configuration is in the `application-dev.properties` file and you can enable
oauth by removing the property:

```shell
 security.ignored=/**
```

# Usage

### Get access token with client and user credentials

Client id : barClientIdPassword

Client secret : secret

Username : admin

```
curl -X POST -vu barClientIdPassword:secret 'http://localhost:8080/oauth/token?username=admin&password=[your_password]&grant_type=password'
```

### Result

```
{
	"access_token": "f27b2bff-ddd8-47fb-9f57-93a245030bc4",
	"token_type": "bearer",
	"refresh_token": "0457e7ab-261b-4e9e-9773-981b180bf3ad",
	"expires_in": 35999,
	"scope": "read write"
}
```

### Access secure resource with token

```
curl -i -H "Authorization: Bearer $ACESS_TOKEN" http://localhost:8080/api/v1/users
```

### Result

```
[{
	"username":"admin",
	"active":true
}]
```

### Logout

To logout of the application you simply need to revoke your access token by invoking:

```
curl -X GET -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:8080/oauth/revoke-token
```

# Default roles and users

These are default users with their roles provided by the application.

| Username  | Role           | Privileges |                                                          
| --------- | -------------- | ---------- |
| admin | ROLE_ROOT | Can do everything |
| hub_admin| ROLE_HUB_ADMIN | Can manage everything related to a HUB application |
| app_admin | ROLE_APP_ADMIN | Can manage everything related to an organization or partner |
| user | ROLE_USER | Can see information about his/her organization: famliies, indicators |
| survey_user | ROLE_SURVEY_USER | Can fill surveys and visualize its state |
