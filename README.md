# simple oauth authentification with github

- Need to get Client_id and Client_secret : Go to https://github.com/settings/apps and create a new github app
- Grab your client_id and client_secret keep it in a local_file, that !! YOU DON'T COMMIT !!
```
simple-auth % cat .env
export SPRING_SECURITY_OAUTH2_CLIENT_ID=xxxlickHtX0lR8xxxx
export SPRING_SECURITY_OAUTH2_CLIENT_SECRET=xxxx4be11e11899c035bdxxxxx
```
- from https://github.com/settings/tokens, create also a Personal Access Token (PAT)
- check read:user, read:email and carefull about expiration date
- Put it in the same local file that !! YOU DON'T COMMIT !!
```
simple-auth % cat .env
export SPRING_SECURITY_OAUTH2_CLIENT_ID=xxxlickHtX0lR8xxxx
export SPRING_SECURITY_OAUTH2_CLIENT_SECRET=xxxx4be11e11899c035bdxxxxx
export GITHUB_PAT=ghp_xxxYPGEomlkscSwOMuExxxxxx
```

- instanciate these environment variable
```
source .env
```

- test your PAT
```
simple-auth % curl -H "Authorization: Bearer $GITHUB_PAT" https://api.github.com/user
```

You should see you github profile...

- You should be able to run the app now
- to compile/run for development:
```
mvn clean install  ; mvn spring-boot:run
```

- and run the tests
```
pytest tests
```

# Where are things
- The index.html is at [src/main/resources/static](/src/main/resources/static/index.html)
- the backend in [/src/main/java/com/example/authgithub](/src/main/java/com/example/authgithub)
- careful with application.yml


# what to do ??
- Change things to pass the tests
- Push your changes 
