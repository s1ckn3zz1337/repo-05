
#  Assignment 3: Architektur-Archäologie
## Beschreibung
Ziel war zu erkennen wie die  Architektur von Apache Tomcat Version 6.053 aufgebaut ist. Speziell solten hierbei gezeigt werden wie mit Request anfragen an den Tomcat-Server umgegangen wird. Mithilfe der Konsolenbefehle grep und sed sollten dabei die Import statements der Code Dokumente analysiert und extrahiert werden. Als Tool zur anschließenden Visualisierung der Architektur dienten uns graphviz.
## Vorgehen 
1. Im ersten Schritt haben wir uns einen groben überblick über die Paketstruktur bzw. Ordenerstruktur von Tomcat mithilfe von Eclipse verschaft. 
Aufgefallen sind uns hierbei die Pakete catalina, jasper und coyote

2. Im zweiten Schritt haben wir uns überlegt, welche Ordner bzw. Dokumente wir nach Import Statements zum erstellen des .dot Files durchsuchen. Rausgeflogen sind hier die pakete javax und tomcat.util... . Da diese nicht als nicht relevant für den Ablauf erachtet wurden. 

3.  Anschließend haben wir im Ordner .../apache-tomcat-6.0.53-src/java/org/apache
mithilfe der Befehle grep und sed die Dokumente durchsucht und daraus mithilfe von graphviz einen Graph erzeugt.

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
4. Mithilfe des Graphs  -> Catalina Connector   
    todo 

5. Suche nach "requests" in Eclipse -> fund der Klasse Requests
    todo

### Ergebnis 

<img src="Bilder/test.png " height="300">

todo: Auschnitt der ür die Requests wichtigen Komponenten(lesbarer als gesamtbild)


 ## Vorgehen zur Analyse des Request ablaufs 

<img alt="" src="Bilder/image2.png" style="width: 850.50px; height: 384.28px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);" title="">

<img alt="" src="Bilder/image10.png" style="width: 602.00px; height: 669.33px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);" title="">

<img alt="" src="Bilder/image4.png" style="width: 1142.28px; height: 715.88px; margin-left: -297.90px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);" title="">

<img alt="" src="Bilder/image1.png" style="width: 602.00px; height: 740.00px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);" title="">

<img alt="" src="Bilder/image8.png" style="width: 910.51px; height: 569.08px; margin-left: -247.51px; margin-top: -147.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);" title="">

<img src="Bilder/image4.png ">

<img src="Bilder/image2.png ">

<img src="Bilder/image10.png ">

<img src="Bilder/image2.png ">

<img src="Bilder/image10.png ">

<img src="Bilder/image2.png ">

<img src="Bilder/image10.png ">
