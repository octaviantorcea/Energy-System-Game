Torcea Octavian 324CA

# Proiect Energy System Etapa 2

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>


## Biblioteci necesare pentru implementare:
* Jackson Annotations
* Jackson Core
* Jackson Databind


## Implementare

### Entitati
Descrierea pachetelor:
* <b>database</b>: contine clasele ce reprezinta baza de date in care sunt stocate toate 
                  informatiile despre "jucatori" obtinute in decursul unei simulari
* <b>entities:</b> contine clasele ce reprezinta "jucatorii" ce participa la simulare
* <b>fileio</b>: contine clase ce se ocupa atat de parsarea datelor din fisierele de input cat si de 
                scrierea datelor obtinute in urma simularii in fisierul de output
* <b>simulation</b>: contine clasa Simulation ce se ocupa de simularea intregului "joc"
* <b>strategies</b>: contine strategiile pe care distribuitorii le folosesc pentru a-si alege
  producatorii
* <b>utils</b>: contine clasa Constants in care sunt declarate constantele folosite in implementarea
simularii

### Flow
Programul incepe prin maparea informatiilor din fisierul de input intr-un  obiect InputDataLoader,
se initializeaza apoi bazele de date pentru consumatori, distribuitori si producatori, apoi incepe
simularea.

Simularea poate fi vazuta ca un joc ce contine mai multe etape:
* <b>faza de initializare</b>: se creeaza consumatorii, distribuitorii si producatorii
  initiali, ce vor contine in acest moment doar datele parsate din fisierul de input
  (cautarea/semnarea/calcularea contractelor se realizeaza in faza urmatoare). Acestia vor fi
  distribuiti (intr-o lista) in bazele de date corespunzatoare fiecarei entitati. Tot in aceasta
  faza, fiecarui distribuitor ii este atribuita o strategie personalizata (aceasta nu se schimba pe
  parcursul simularii).
  
* <b>runda initiala</b>: este alcatuita din mai multe etape:
  1. fiecare distributor isi alege producatorii necesari si se aboneaza la ei
  2. fiecare distributor isi calculeaza pretul de productie
  3. se executa <u>operatiile de baza</u> (vezi mai jos)
  
* <b>se simuleaza rundele normale</b>:
  1. se updateaza costurile de infrastructura pentru fiecare distribuito
  2. se adauga in baza de date noii consumatori
  3. se executa <u>operatiile de baza</u> (vezi mai jos)
  4. se modifica producatorii conform schimbarilor lunare; in acest moment sunt avertizati
     distribuitorii daca unul dintre producatorii sai a suferit modificari; distribuitorii ce
     trebuie sa isi gaseasca alti producatori sunt introdusi intr-o lista
  5. distribuitorii isi aleg noii producatori (daca este necesar)
  6. se goleste lista cu distribuitorii ce trebuie sa isi gaseasca alti producatori
  7. fiecare distribuitor isi calculeaza noul pret de productie
  8. producatorii salveaza datele lunare (ce distribuitori are ca abonati pentru runda respectiva)
  
<b>OPERATIILE DE BAZA</b>: aceste operatii sunt comune atat rundei initiale, cat si unei runde
normale:
  1. fiecare distribuitor isi calculeaza pretul contractului
  2. se adauga salariile la bugetele consumatorilor
  3. consumatorii ce nu au contract cauta cea mai buna oferta; se creeaza un contract pe un anumit
     numar de luni (runde), cu un pret fixat. Acest contract este atribuit unui consumator si este
     adaugat in lista de contracte al unui distribuitor.
  4. fiecare consumator isi plateste (sau macar incearca) sa isi plateasca contracul
  5. fiecare distribuitor isi plateste cheltuielile; in cazul in care bugetul sau devine negativ,
     acesta va da faliment
  6. se cauta fiecare consumator care este in faliment si care inca are contract (practic se cauta
     consumatorii care au dat faliment in aceasta runda) si se sterge din lista distribuitorului
     contractul cu acesta
  7. se cauta fiecare distribuitor care este in faliment si care inca mai are contracte (practic se
     cauta distribuitorii care au dat faliment in luna aceasta); consumatorii ce aveau contract la
     acest distribuitor vor fii marcati ca fara contract, iar lista cu contracte va fii golita
     
<b>Dupa incheierea simularii</b>, se initializeaza o clasa 'OutputHelperClass' ce va
  contine informatiile ce trebuie parsate in fisierul de output.

### Design Patterns
* <b>Singleton</b>:
    * pentru a avea o singura instanta de StrategyFactory
    * pentru a avea o singura instanta a simularii "jocului"
* <b>Factory</b>:
    * strategiile folosite de distribuitori sunt create cu ajutorul unui factory (StrategyFactory)
* <b>Strategy</b>:
    * folosit pentru a alege modul in care distribuitorii isi aleg lunar producatorii
* <b>Observer</b>:
    * fiecare producator este un obiect observable
    * un distributor devine un observer al unui producator atunci cand se aboneaza la el
    * un distribuitor este "atentionat" atunci cand un producator isi schimba datele


<b>Repository-ul de github</b>: <https://github.com/octaviantorcea/OOP-Project-2>
