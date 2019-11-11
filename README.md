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
###### Domyślne wartości parametrów: year = 2019, month = 12
###### **UWAGA!** Jeżeli chcemy podać jako parametr `month` miesiąć, który jest przed Październikiem (Styczeń, Luty, Marzec... Wrzesień) to trzeba podać dwucyfrową reprezentację miesiąca np.: Styczń to 01, Maj to 05 itd. 
Zwraca status:
* 200 - OK <br />Jeżeli się wszytko powiodło.
* 400 - BAD_REQUEST <br />Jeżeli parametr `month` został niepoprawnie wpisany
  

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
  
#### **GENERATE ICS FILE**
Generuje plik z rozszerzeniem .ics w którym znajdują się wydarzenia w danym roku i miesiącu.  
 
| METODA | ŚCIEŻKA DOSTĘPU | PARAMETR | OPIS |
|--------|:---------------:|:--------:|:----:|
| GET| `api/weeia/calendar/events/file.ics`| `year`  `month`  `filename` | Zwraca informacje czy generowanie pliku się powiodło. Jeżeli operacja się powiodła to zostaje utworzony plik `filename`.ics z aktywnymi wydarzeniami w danym miesiąci i roku.|
-----
###### Domyślne wartości parametrów:<br />year = 2019, month = 12, filename = CalendarEvent
###### **UWAGA!** Jeżeli chcemy podać jako parametr `month` miesiąć, który jest przed Październikiem (Styczeń, Luty, Marzec... Wrzesień) to trzeba podać dwucyfrową reprezentację miesiąca np.: Styczń to 01, Maj to 05 itd. 
Zwraca status:
* 201 - CREATED <br />Jeżeli się wszytko powiodło.
* 204 - NO_CONTENT <br />Jeżeli nie ma aktywnych wydarzeń.
* 400 - BAD_REQUEST <br />Jeżeli parametr `month` został niepoprawnie wpisany
  

#### **Przykłady wywołania**
* ##### Pierwszy przykład dla zapytania o aktywne wydarzenia w Grudniu 2019 roku.
Request: 
```java
      GET | localhost:8080/api/weeia/calendar/events/file.ics?year=2019&month=12&filename=DecemberEvents 
```
Response:
```json
    Events from year:	2019,
    month:	12
    created in file DecemberEvents.ics
```   
<details>
    <summary>Zawartość pliku DecemberEvents.ics</summary>
    <p>
     
    ``` 
    BEGIN:VCALENDAR
    PRODID:-//Apple Inc.//Mac OS X 10.15.1//EN
    VERSION:2.0
    CALSCALE:GREGORIAN
    BEGIN:VEVENT
    DTSTAMP:20191111T185610Z
    DTSTART;VALUE=DATE:20191206
    SUMMARY:First Step to Nobel Prize
    UID:a61b4e58-02ab-4cdb-9bab-9f6d1d9ba94a
    END:VEVENT
    BEGIN:VEVENT
    DTSTAMP:20191111T185610Z
    DTSTART;VALUE=DATE:20191209
    SUMMARY:First Step to Fields Medal
    UID:34f4b131-8be2-454f-9e70-050a676e1d0d
    END:VEVENT
    BEGIN:VEVENT
    DTSTAMP:20191111T185610Z
    DTSTART;VALUE=DATE:20191211
    SUMMARY:Fascynująca Fizyka - poziom ponadpodstawowy
    UID:3e67852c-8469-4920-ad2c-5036d75577fd
    END:VEVENT
    BEGIN:VEVENT
    DTSTAMP:20191111T185610Z
    DTSTART;VALUE=DATE:20191216
    SUMMARY:Matura próbna Matematyka podstawowa
    UID:b04d25ac-2b26-4612-9809-eeb1b00bf70a
    END:VEVENT
    BEGIN:VEVENT
    DTSTAMP:20191111T185610Z
    DTSTART;VALUE=DATE:20191217
    SUMMARY:Matura próbna Matematyka rozszerzona
    UID:99740914-773f-464a-a1ac-fc77a1e8a89e
    END:VEVENT
    BEGIN:VEVENT
    DTSTAMP:20191111T185610Z
    DTSTART;VALUE=DATE:20191218
    SUMMARY:Matura próbna Fizyka rozszerzona
    UID:12b850e4-12fd-45a7-b40b-25a0d72ae141
    END:VEVENT
    BEGIN:VEVENT
    DTSTAMP:20191111T185610Z
    DTSTART;VALUE=DATE:20191219
    SUMMARY:Matura próbna Chemia rozszerzona
    UID:52035910-b9cc-4ee5-a63c-f72b0b8680a1
    END:VEVENT
    END:VCALENDAR
    ```  
</p></details>

* ##### Drugi przyklad zapytania o aktywne wydarzenia w Kwietniu 2020 roku. W pliku zostało zawarte tylko jedno wydarzenie, ponieważ tyle było aktywnych wydarzeń w tym miesiącu.
Request: 
```java
      GET | localhost:8080/api/weeia/calendar/events/file.ics?year=2020&month=04&filename=AprilEvents 
```
Response:
```json
    Events from year:	2020,
    month:	04
    created in file AprilEvents.ics
```   
<details>
    <summary>Zawartość pliku AprilEvents.ics</summary>
    <p>
     
    ``` 
    BEGIN:VCALENDAR
    PRODID:-//Apple Inc.//Mac OS X 10.15.1//EN
    VERSION:2.0
    CALSCALE:GREGORIAN
    BEGIN:VEVENT
    DTSTAMP:20191111T231638Z
    DTSTART;VALUE=DATE:20200423
    SUMMARY:gala rozdania nagród w konkursach
    UID:47359b6c-e412-486e-82d8-3fd0625dcf84
    END:VEVENT
    END:VCALENDAR
    ```  
</p></details>
