#  Assignment 5: Ein Butler für Sie
## Installation von Jenkins
Zur Installation von Jenkins per SSH-Shell mit dem Server verbinden und anschließend die in der Installationsanleitung beschriebene Schritte durchführen:

[Installationsanleitung](https://www.digitalocean.com/community/tutorials/how-to-install-jenkins-on-ubuntu-16-04#step-4-%E2%80%94-setting-up-jenkins)

Jenkins sollte anschließend über die URL http://**Adresse**:8080 erreichbar sein. Bei **Adresse** handelt es sich um die IP-Adresse des Servers.

## Konfiguration von Jenkins
Jenkins über die Browser URL http://**Adresse**:8080 aufrufen und über den zuvor angelegegten User Einloggen.
<img src="Bilder/jenkins/1_login.png">

Anschließend Element anlegen auswählen
<img src="Bilder/jenkins/2_anlegen.png">

Name eingeben und und Elementyp "Pipeline" auswählen.
<img src="Bilder/jenkins/4_typ.png">

Haken bei GitHub-Projekt setzen und Github URL des Repositories eingeben.
<img src="Bilder/jenkins/github.png">

Pipeline nach dem Makierten Schema hinzufügen und Github Nutzer hinzufügen.
<img src="Bilder/jenkins/5_pipelineconfig.png">

Github Benutzername und Passwort in die Makierten Felder eingeben und hinzufügen 
<img src="Bilder/jenkins/3_credentials.png">

Zum Abschluss Speichern 

## Pipeline

### Initialisierug


### Build

### Test


### Ergebniss

<img src="Bilder/jenkins/6_pipeline.png">

### Deployment 
Wo sind die Jars ?

## Probleme 
Da der bereitgestellte Server nur über das Hochschulnetz erreichbar ist konnte das Github-Jenkins-Plugin nicht über die "Jenkins-Hook-URL" einen Build Triggern.    (siehe [Anleitung](https://medium.com/@marc_best/trigger-a-jenkins-build-from-a-github-push-b922468ef1a)) Jenkins wurde deshalb auf einen 5 Minuten Trigger-Intervall eingestell.