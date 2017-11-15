
#  Assignment 3: Architektur-Archäologie
## Beschreibung
Das Ziel war es zu erkennen wie die Architektur von Apache Tomcat Version 6.053 aufgebaut ist. Speziell sollten hierbei gezeigt werden wie mit Request-Anfragen an den Tomcat-Server umgegangen wird. Mithilfe der Konsolenbefehle grep und sed sollten dabei die Import Statements der Code Dokumente analysiert und extrahiert werden. Als Tool zur anschließenden Visualisierung der Architektur dienten uns graphviz.
## Vorgehen 
**1.** Im ersten Schritt haben wir uns einen groben überblick über die Paketstruktur bzw. Ordenerstruktur von Tomcat mithilfe von Eclipse verschafft. 
Aufgefallen sind uns hierbei die Pakete catalina, jasper und coyote

**2.** Im zweiten Schritt haben wir uns überlegt, welche Ordner bzw. Dokumente wir nach Import Statements zum erstellen des .dot Files durchsuchen. Rausgeflogen sind hier die pakete javax und tomcat.util... . Da diese als nicht relevant für den Ablauf erachtet wurden. 

**3.**  Anschließend haben wir im Ordner .../apache-tomcat-6.0.53-src/java/org/apache
mithilfe der Befehle grep und sed die Dokumente durchsucht und daraus mithilfe von graphviz einen Graph der Paketstruktur erzeugt.

```bash
#!/bin/bash
echo "digraph {" > test.dot ; grep -R "^import" * | sed -E "s/\//./g" | \
sed -E "s/\.java//g" | sed -E "s/\.\*//g" | sed -E "s/import //g" | \
grep -v ":java" | grep -v ":javax" | sed -E "s/;//g" | sed -E "s/:/ -> /" | \
grep -v "\.properties" | sed -E "s/\./_/g" | sed -E "s/_[A-Z].* ->/ ->/g" | \
sed -E "s/-> ([a-z_]*)_[A-Z][a-zA-Z]*/-> \1/g" | sed -E "s/org_apache_//g" | \
grep -v "juli_logging" | grep -v "tomcat_util" | grep -v "catalina_util" |
grep -v "test"| grep -v "javax"\
sort | uniq >> test.dot ; echo "}" >> test.dot ; 
dot -Tpdf graph.dot > graph.pdf
```
Ergebnis: 
<img src="Bilder/test.png "  height="300">

Aufgefallen sind uns die folgenden Komponenten:

* Catalina 
* Coyote
* Jasper
* Catalina-Core
* Catalina Connector
* Catalina Util




**4.** Um den Aufbau der Komponeten besser zu verstehen, und da der Graph sehr unübersichtlich ist, haben wir diesen noch in seine einzelnen zuvor gefundenen Komponenten geteilt. Hierfür wurde der skriptbefehl wie folgt angepasst: 


```bash
#!/bin/bash
# name muss für jede Komponente angepasst werden!
name=catalina
echo "digraph {" > $name.dot ; grep -R "^import" * | sed -E "s/\//./g" | \
sed -E "s/\.java//g" | sed -E "s/\.\*//g" | sed -E "s/import //g" | \
grep -v ":java" | grep -v ":javax" | sed -E "s/;//g" | sed -E "s/:/ -> /" | \
grep -v "\.properties" | sed -E "s/\./_/g" | sed -E "s/_[A-Z].* ->/ ->/g" | \
sed -E "s/-> ([a-z_]*)_[A-Z][a-zA-Z]*/-> \1/g" | sed -E "s/org_apache_//g" | \
grep -v "juli_logging" | grep -v "tomcat_util" | grep " -> cat.*" | \
grep -v " -> catalina_" | sort | uniq >> $name.dot ; echo "}" >> $name.dot ; \
dot -Tpdf $name.dot > $name.pdf 
```
Catalina
<img src="pdf/catalina.png ">

Coyote
<img src="pdf/coyote.png ">

Jasper
<img src="pdf/jasper.png " height = "100">

Catalina-Core
<img src="pdf/catalinaCore.png ">

Catalina-Connector
<img src="pdf/catalinaConnector.png ">

Catalina Util
<img src="pdf/catalinaUtil.png ">


 ## Vorgehen zur Analyse des Request ablaufs 
 
1.Aufgabenstellung

Das Ziel dieses Dokuments ist die Dokumentation der Verarbeitung ein Requests in der Standardversion des Apache Tomcat Servers in der Version 6.0.53. Die Dokumentation erfolgt unter Zuhilfenahme der IDE IntelliJ 2016.2.

2.Vorgehensweise

Der Einstiegspunkt der Dokumentation bildet die Klasse Request und RequestFacade in dem Package: org.apache.catalina.connector. Diese Klassen dient der Darstellung und verarbeitung einer einzelnen Anfrage eines HTTP Clients. Es wurde in diesen Klasse nach Methoden gesucht, welche einen Namen passend zur Aufgabenstellung, der verarbeitung einer Anfrage/Requests haben.
Danach wurde nach allen Dateien gesucht, welche die Request Klasse verwenden.

<img src="Bilder/image2.png ">

Nach dem Setzen eine Breakpoints in dem Request constructor konnte der Klasse org.apache.catalina.Connector die Methode service gefunden werden, welche einen Request und einen Response Objekt als Parameter übernimmt und diese verarbeitet.

<img src="Bilder/image10.png ">

Bei dem weiteren durchsuchen des Codes ist die Invoke Methode aufgefallen.

<img src="Bilder/image1.png ">

Dabei wurde festgestellt, dass die Verarbeitung des Request durch ein Context Object erfolgt.
Also wurde nach dem Context Object gesucht und dabei in jedem Konstruktor ein breakpoint gesetzt und danach Tomcat 6 neu gestartet.

<img src="Bilder/image4.png ">

Dabei wurde eine Methode gefunden welche die Ordner nach den Dateien durchsucht welche dann als Applikationen geladen werden können.

<img src="Bilder/image8.png ">

Dabei wurde auch eine Methode mit dem Namen deployApps() gefunden, welche anscheinend für das Platzieren von Java Applikationen in dem ServerContext verantwortlich ist.

<img src="Bilder/image6.png ">

Bei der Durchsuchung der Verzeichnisse, welche vorher im Debugger zu sehen waren,  nach der Benutzung des Requestobjektes wurden Java Class Files gefunden, in welchen Requestsin einem Java Programm verarbeitet und an den Client mit einem Response beantwortet werden. Beispielhaft dafür ist die Classe RequestInfoExample.

<img src="Bilder/image5.png ">

Bei der Suche nach weiter Aufrufen dieser Klasse, wurde festgestellt, dass jede Java Klasse als Servlet  in der datei web.xml in dem übergeordneten Ordner definiert werden muss.

<img src="Bilder/image3.png ">

Bei weiterem durchschauen der web.xml wurde der Abschnitt für den Pfadfilter gefunden.
Dieser schreibt das URL - Pattern für die einzelnen Servlets vor und wann diese Servlets geladen werden sollen. Dies erfolgt durch Filter Classen.
Aus diesen Information und der nachfolgenden Ordnerstruktur lässt sich der Pfad zum Abrufen des RequestInfoExample Servlets so ableiten:

localhost:8080/examples/servlets/servlet/RequestInfoExample.

<img src="Bilder/image7.png ">

Die Ordnerstruktur lässt das selbe Ergebniss vermuten.

<img src="Bilder/image9.png ">

Die Ausgabe wurde leicht modifiziert um die Richtigkeit der Ergebnisse zu garantieren.

<img src="Bilder/image11.png ">

Ein Aufruf der Methode doGet der Classe RequestInfoExample durch einen GET Request aus dem Chrome - Browser.
