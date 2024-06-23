Proiectul se va deschide în Jetbrains IntelliJ IDEA: https://www.jetbrains.com/idea/download

Pentru baza de date, se va folosi MySQL.
Se descarcă MySQL Server: https://dev.mysql.com/downloads/mysql
Se descarcă MySQL Workbench: https://dev.mysql.com/downloads/workbench
Se descarcă fișierul db.sql din folderul proiectului de github și se încarcă în MySQL Workbench accesând Server>Data Import> import from self-contained file.

Pentru deployment se folosește Apache Tomcat.
Se descarcă Apache Tomcat 9: https://tomcat.apache.org/download-90.cgi
(! proiectul nu este compatibil cu Tomcat 10)

Pentru rulare, se accesează Add Configuration în IntelliJ, se selectează Tomcat server>Local și se urmează instrucțiunile.
În tab-ul Server se setează URL-ul următor: http://localhost:8080/api, iar în tab-ul Deployment se alege artefactul „licenta:war exploded“ și Application context se setează ca fiind /api

Se pornește serverul din IntelliJ, iar API-ul este gata de utilizare.

Pentru utilizarea întregului proiect, împreună cu site-ul web, se urmează pașii de utilizare a proiectului realizat de Dragotă Tiberiu-Gabriel, găsit la următorul link de github: https://github.com/tgdragota/Licenta_Dragota-UPT

Pentru autentificare există numeroase conturi disponibile. Acestea sunt vizibile în tabela accounts din baza de date. Toate conturile se pot accesa folosind parola "parola". Câteva dintre aceste conturi sunt următoarele:
student: laura.giurea@student.upt.ro
profesor: ion.sandu@upt.ro
secretariat: ioana.popescu@secretariat.upt.ro
