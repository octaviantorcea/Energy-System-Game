Torcea Octavian 324CA

# Proiect Energy System Etapa 2

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>


## Biblioteci necesare pentru implementare:
* Jackson Core
* Jackson Databind
* Jackson Annotations


## Implementare

### Entitati

### Flow

### Design Patterns
* Singleton:
    * pentru a avea o singura instanta de StrategyFactory
    * pentru a avea o singura instanta a simularii "jocului"
* Factory:
    * strategiile folosite de distribuitori sunt create cu ajutorul unui factory (StrategyFactory)
* Strategy:
    * folosit pentru a alege modul in care distribuitorii isi aleg lunar producatorii
* Observer:
    * fiecare producator este un obiect observable
    * un distributor devine un observer al unui producator cand se aboneaza la el
    * un distribuitor este "atentionat" atunci cand un producator isi schimba datele

