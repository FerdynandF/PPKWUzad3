## PPKWU - Programowanie pod kątem wielokrotnego użycia
## Zadanie 3 - Mobilny Kalendarz WEEIA
* Proszę przygotować dokumentację i zaimplementować (wg tej dokumentacji) API, które będzie generowało (dla obecnego lub wybranego miesiąca) kalendarz w formacie ICS/iCal dla kalendarza ze strony http://www.weeia.p.lodz.pl. Następnie proszę "podpiąć" ten kalendarz pod swój telefon i sprawdzić, czy działa.

 **Dokumentacja API**
 ===================
 
 ## **WEEIA EVENTS**

API generuje dla dowolnego miesiąca kalendarz w formacie ICS, korzystając z kalendarza który jest na stronie http://www.weeia.p.lodz.pl.
 API umożliwia również wyświetlenie informacji o wydarzeniach w danym miesiącu.
   
<br /> 

### ENDPOINTS
* [GET    /api/weeia/calendar/events](#list-events)
* [GET    /api/weeia/calendar/events/plik.ics](#generate-ics-file)

<br />

### *OBIEKT EVENTREST*

###### ATTRIBUTES
_________
**day**  
*Type: int*
_________
**description**  
*Type: String*
<br />  
<br />
<br />


#### **LIST EVENTS**  
Wyświetla wszystkie aktywne wydarzenia w danym roku i miesiącu



| METODA | ŚCIEŻKA DOSTĘPU | PARAMETR | OPIS |
|--------|:---------------:|:--------:|:----:|
| GET| `api/weeia/calendar/events`| `year`  `month` | Zwraca tablice obiektów [EventRest](#obiekt-eventrest) w formacie JSON, zawierające aktywne wydarzenia z danego miesiąca (Tablica moe być pusta)|
-----
###### **UWAGA!** Jeżeli chcemy podać jako parametr `month` miesiąć, który jest przed Październikiem (Styczeń, Luty, Marzec... Wrzesień) to trzeba podać dwucyfrową reprezentację miesiąca np.: Styczń to 01, Maj to 05 itd. 
#### **Przykłady wywołania**

Request: 
```java
      GET | localhost:8080/api/weeia/calendar/events?year=2019&month=11 
```
Response:
```json
    [
    {
        "day": 4,
        "description": "First Step to Fields Medal"
    },
    {
        "day": 6,
        "description": "First Step to Success"
    },
    {
        "day": 8,
        "description": "First Step to Nobel Prize"
    },
    {
        "day": 15,
        "description": "Fascynująca Fizyka - poziom podstawowy"
    }
] 
```   

* ##### Drugi przykład.<br />W tej sytuacji zwrócona zostanie pusta tablica, poniewaz dla miesiąca Maj w roku 2020 w dniu pisania dokumentacji (11.11.2019) nie ma aktywnych wydarzeń

Request: 
```java
      GET | localhost:8080/api/weeia/calendar/events?year=2020&month=05
```
Response:
```json
    [] 
```
  
  
  <br />
  
#### **Generate ICS file**
