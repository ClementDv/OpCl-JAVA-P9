<p align="center">
  <img src="https://github.com/ClementDv/OpCl-JAVA-P9/blob/master/.readme/Logo_Black.png?raw=true" alt="Logo"/>
</p>

# OpCl-Java-P9

An application to assess the diabetic potential of patients and identify the factors noted.


## Features

Be able to add patients, modify and delete them.<br />
Add, edit and delete patient history notes.<br />
Assess the level of risk of diabetes in patients using the content of the notes.<br />

### Storage

* Patients are stored in SQL using MySQL.
* Notes are stored in NoSQL using MongoDB.

## Preriquistes

1. [Java version 11](https://adoptopenjdk.net/?variant=openjdk15&jvmVariant=hotspot)
2. [Maven](https://maven.apache.org/download.cgi)
3. [Docker](https://docs.docker.com/get-docker/)
4. [Docker-compose](https://docs.docker.com/compose/install/)
5. [NodeJS - Npm](https://nodejs.org/en/download/)

## Run Services

Compile the application
```
mvn package build
```

Run all services
```
java -jar users/build/libs/users.jar
java -jar gps/build/libs/gps.jar
java -jar rewards/build/libs/rewards.jar
```

## Docs

Click on the following links while the services are running

**Assessment**: http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

**Patient**: http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

**Note**: http://localhost:8082/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

## Deployement

Deploy containers
```
docker-compose up --build -d
```

Stop deployement
```
docker-compose down
```

## Webapp

<p align="center">
  <img src="https://github.com/ClementDv/OpCl-JAVA-P9/blob/master/.readme/webapp_patient.PNG?raw=true" alt="webapp"/>
  <img src="https://github.com/ClementDv/OpCl-JAVA-P9/blob/master/.readme/webapp_notes.PNG?raw=true" alt="webapp"/>
  <img src="https://github.com/ClementDv/OpCl-JAVA-P9/blob/master/.readme/webapp_assess.PNG?raw=true" alt="webapp"/>
</p>

Run the webapp after services deployement

```
cd webapp
mpm run start
```

---------------------------------------
*Thank You.  
CLemDv*
