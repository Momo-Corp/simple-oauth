
# Welcome to my Farm !

![Vache heureuse](src/main/resources/static/image.png)

# compile and launch the server

```
export MY_GITHUB_PAT=tagada
export BASE_URL="https://${CODESPACE_NAME}-8080.app.github.dev"
```


```
mvn clean install
mvn spring-boot-run
```

# run the tests
```
pytest tests/test_farm.py
=================================== 4 passed in 0.13s
```

# launch front-end

`http://localhost:8080`


# using test-token on command-line
```
TOKEN=$(curl -s http://localhost:8080/auth/test-token)
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/farm/cow
{"id":1,"name":"Bessie","hungry":true}% 
```

# simple oauth authentification with github

Remember how OAuth [Works](https://docs.google.com/presentation/d/1ovkzK-z_02cwApNDEsSs1lO30TgjZ1t5-jZUUl8UrL0/edit?usp=sharing) and what is the configuration of your server to
handle Oauth Authentification (see [Application.yml](/src/main/resources/application.yml)) is src/resources)
```
        registration:
          github:
            client-id: ${SPRING_SECURITY_OAUTH2_CLIENT_ID}
            client-secret: ${SPRING_SECURITY_OAUTH2_CLIENT_SECRET}
            scope: read:user, user:email
        provider:
          github:
            authorization-uri: https://github.com/login/oauth/authorize
            token-uri: https://github.com/login/oauth/access_token
            user-info-uri: https://api.github.com/user
```

So wee need a BASE_URL, a client ID and a client secret. 

## BASE_URL: Your server URL

Remember on codespace, localhost is not reachable from outside the VM. 
Your base URL for your web server is (carefull to not use the 'gitpreview.dev' suffix  ):
```
echo "https://${CODESPACE_NAME}-8080.app.github.dev"
```

So BASE_URL is:

```
export BASE_URL="https://${CODESPACE_NAME}-8080.app.github.dev"
```

Put that in a file:
```
echo 'export BASE_URL="https://${CODESPACE_NAME}-8080.app.github.dev"' > .env
```

and read it:
```
source .env
```

Check everything ok:
```
echo $BASE_URL
```

Suppose BASE_URL is : https://momo54-8080.app.github.dev

## Client_id and Client_secreat

- Need to get Client_id and Client_secret : Go to https://github.com/settings/apps and create a new github app.

Important Parameters are:
- Homepage URL : BASE_URL ie. https://momo54-8080.app.github.dev for example 
- GitHub App name : choose a name you like
- callback URL : https://momo54-8080.app.github.dev/login/oauth2/code/github
- Disable Webhook
- ok -> create

You should see CLIENT ID and able to generate a Client Secret. Do That.
Grab your client_id and client_secret keep and update your .env, that !! YOU DON'T COMMIT !!
```
export SPRING_SECURITY_OAUTH2_CLIENT_ID=xxxlickHtX0lR8xxxx
export SPRING_SECURITY_OAUTH2_CLIENT_SECRET=xxxx4be11e11899c035bdxxxxx
```

## Personal Access Token (PAT)

Github PAT allows you to acess directly the GitHub "Profile" server.

- from https://github.com/settings/tokens, create also a Personal Access Token (PAT) Classic
Parameters:
- check read:user, read:email and 
- expiration date
- create
- Put it in the same local file that !! YOU DON'T COMMIT !!
```
export BASE_URL = ....
export SPRING_SECURITY_OAUTH2_CLIENT_ID=xxxlickHtX0lR8xxxx
export SPRING_SECURITY_OAUTH2_CLIENT_SECRET=xxxx4be11e11899c035bdxxxxx
export MY_GITHUB_PAT=ghp_xxxYPGEomlkscSwOMuExxxxxx
```

- instanciate these environment variable
```
source .env
```

- test your PAT
```
curl -H "Authorization: Bearer $MY_GITHUB_PAT" https://api.github.com/user
```

You should see you github profile... Check if you have an !! avatar_url and a name !!
Add MY_GITHUB_PAT as a github secret for your repository. This allow to have PAT available during  Github actions.

- Go to your REPO settings
- Create a new repository secret (for secret)
 - Name is MY_GITHUB_PAT
 - value is the one you have in your .env

# run the APP

You should be able to run the app now

- to compile/run for development:
```
mvn clean install  ; mvn spring-boot:run
```

- Check the you can really login with github from the web interface: Open you browser with your BASE_URL


- and run the tests (carefull with your environment variables...)
```
pytest tests
```

How these tests can run ?? Users should login no ??

# Where are things
- The index.html is at [src/main/resources/static](/src/main/resources/static/index.html)
- the backend in [/src/main/java/com/example/authgithub](/src/main/java/com/example/authgithub)
- careful with [application.yml](/src/main/resources/application.yml)


# what to do ??
- Able to login with github from the browser
- Change things to pass the tests
- Push your changes
 - (Don't commit/push .env !!)
 - Don't forget to add MY_GITHUB_PAT as a github secret (otherwise test fails)
